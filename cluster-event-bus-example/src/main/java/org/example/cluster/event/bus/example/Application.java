package org.example.cluster.event.bus.example;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.contrib.pattern.ClusterClient;
import akka.contrib.pattern.ClusterReceptionistExtension;
import akka.routing.BalancingPool;
import com.typesafe.config.ConfigFactory;
import org.example.cluster.event.bus.example.actor.ClusterMonitorActor;
import org.example.cluster.event.bus.example.actor.EventBusActor;
import org.example.event.EventBusMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/18 15:29
 */
public class Application {
    public static void main(String[] args) throws InterruptedException {
        createNode(25251);
        createNode(25252);
        createNode(25253);

        //15秒后，向集群发布消息, topic:"event-bus-actor"
        Thread.sleep(15000);
        ActorSystem system = ActorSystem.create("clientSystem");
        Set<ActorSelection> initialContacts = new HashSet<>();
        initialContacts.add(system.actorSelection(
                "akka.tcp://Akkademy@127.0.0.1:25252/user/receptionist"));
        initialContacts.add(system.actorSelection(
                "akka.tcp://Akkademy@127.0.0.1:25251/user/receptionist"));
        ActorRef receptionist = system.actorOf(ClusterClient.defaultProps(initialContacts));
        for (int i = 0; i < 1; i++) {
            ClusterClient.Publish publish = new ClusterClient.Publish(
                    "event-bus-actor", new EventBusMessage("///" + System.currentTimeMillis() + "///" + i)
            );
            receptionist.tell(publish, ActorRef.noSender());
        }
    }

    private static void createNode(int port) {
        // Override the configuration of the port
        Map<String, Object> overrides = new HashMap<String, Object>(1) {
            {
                put("akka.remote.netty.tcp.port", port);
            }
        };
        ActorSystem system = ActorSystem.create("Akkademy", ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load()));
        system.actorOf(Props.create(ClusterMonitorActor.class), "cluster-monitor-actor");
        ActorRef eventBusActor = system.actorOf(new BalancingPool(5).props(Props.create(EventBusActor.class)),
                "event-bus-actor");
        ClusterReceptionistExtension x = (ClusterReceptionistExtension) ClusterReceptionistExtension.apply(system);
        //订阅"event-bus-actor"
        x.registerSubscriber("event-bus-actor", eventBusActor);
    }
}
