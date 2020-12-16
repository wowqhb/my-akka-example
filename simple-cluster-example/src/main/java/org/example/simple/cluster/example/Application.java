package org.example.simple.cluster.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.example.simple.cluster.example.actor.ClusterMonitorActor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/16 10:04
 */
public class Application {
    public static void main(String[] args) throws InterruptedException {
        createNode(25251);
        createNode(25252);
        ActorRef node25253 = createNode(25253);
        ActorRef node25254 = createNode(25254);
        Thread.sleep(10000);
        node25253.tell("leave", ActorRef.noSender());
        Thread.sleep(10000);
        node25254.tell("leave", ActorRef.noSender());
    }

    private static ActorRef createNode(int port) {
        // Override the configuration of the port
        Map<String, Object> overrides = new HashMap<String, Object>(1) {
            {
                put("akka.remote.artery.canonical.port", port);
            }
        };
        ActorSystem system = ActorSystem.create("ClusterSystem", ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load()));
        ActorRef actorRef = system.actorOf(Props.create(ClusterMonitorActor.class), "cluster-monitor-actor");
        return actorRef;
    }
}
