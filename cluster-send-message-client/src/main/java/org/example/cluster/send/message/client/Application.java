package org.example.cluster.send.message.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 10:09
 */
public class Application {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("ClientSystem");
        Set<ActorRef> initialContacts = new HashSet<>();
        initialContacts.add(system.actorSelection(
                "akka.tcp://ClusterSystem@127.0.0.1:25252/user/receptionist").anchor());
        initialContacts.add(system.actorSelection(
                "akka.tcp://ClusterSystem@127.0.0.1:25251/user/receptionist").anchor());
    }
}
