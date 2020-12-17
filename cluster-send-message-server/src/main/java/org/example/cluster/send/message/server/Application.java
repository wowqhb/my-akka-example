package org.example.cluster.send.message.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.contrib.pattern.ClusterReceptionistExtension;
import akka.routing.BalancingPool;
import com.typesafe.config.ConfigFactory;
import org.example.cluster.send.message.server.actor.ClusterMonitorActor;
import org.example.cluster.send.message.server.actor.WorkerActor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/16 11:54
 */
public class Application {
    public static void main(String[] args) {
        createNode(25251);
        createNode(25252);
        createNode(25253);
        createNode(25254);
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
//        ActorRef workerActor = system.actorOf(Props.create(WorkerActor.class), "worker-actor");
        ActorRef workerActor = system.actorOf(new BalancingPool(5).props(Props.create(WorkerActor.class)),
                "worker-actor");
        ((ClusterReceptionistExtension) akka.contrib.pattern.ClusterReceptionistExtension.apply(system))
                .registerService(workerActor);
    }
}
