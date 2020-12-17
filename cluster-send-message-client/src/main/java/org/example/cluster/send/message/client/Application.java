package org.example.cluster.send.message.client;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.contrib.pattern.ClusterClient;
import akka.pattern.Patterns;
import akka.util.Timeout;
import lombok.extern.slf4j.Slf4j;
import org.example.message.ChildMessage;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 10:09
 */
@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {
        Timeout timeout = Timeout.apply(5, TimeUnit.SECONDS);
        ActorSystem system = ActorSystem.create("clientSystem");
        Set<ActorSelection> initialContacts = new HashSet<>();
        initialContacts.add(system.actorSelection(
                "akka.tcp://Akkademy@127.0.0.1:25252/user/receptionist"));
        initialContacts.add(system.actorSelection(
                "akka.tcp://Akkademy@127.0.0.1:25251/user/receptionist"));
        ActorRef receptionist = system.actorOf(ClusterClient.defaultProps(initialContacts));
        ClusterClient.Send send = new ClusterClient.Send(
                "/user/worker-actor", new ChildMessage.Request().setMessage("test 123"),
                false);
        Future<Object> f = Patterns.ask(receptionist, send, timeout);
        Object result = Await.result(f, timeout.duration());
        log.info("result: {}", result);

        Await.result(Patterns.ask(receptionist, new ClusterClient.Send(
                "/user/worker-actor", new ChildMessage.Request().setMessage("test 123"),
                false), timeout), timeout.duration());
        Await.result(Patterns.ask(receptionist, new ClusterClient.Send(
                "/user/worker-actor", new ChildMessage.Request().setMessage("test 123"),
                false), timeout), timeout.duration());
        Await.result(Patterns.ask(receptionist, new ClusterClient.Send(
                "/user/worker-actor", new ChildMessage.Request().setMessage("test 123"),
                false), timeout), timeout.duration());
        Await.result(Patterns.ask(receptionist, new ClusterClient.Send(
                "/user/worker-actor", new ChildMessage.Request().setMessage("test 123"),
                false), timeout), timeout.duration());
        //tell
        for (int i = 100; i > 0; i--) {
            receptionist.tell(new ClusterClient.Send("/user/worker-actor", String.format("time: %d-%03d",
                    System.currentTimeMillis(), i), false), ActorRef.noSender());
        }

    }
}
