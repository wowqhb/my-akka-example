package org.example.cluster.send.message.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.example.cluster.send.message.server.actor.ClusterMonitorActor;

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

    private static ActorRef createNode(int port) {
        // Override the configuration of the port
        Map<String, Object> overrides = new HashMap<String, Object>(1) {
            {
                put("akka.remote.artery.canonical.port", port);
            }
        };
        ActorSystem system = ActorSystem.create("ClusterSystem", ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load()));
        return system.actorOf(Props.create(ClusterMonitorActor.class), "cluster-monitor-actor");
    }
}
