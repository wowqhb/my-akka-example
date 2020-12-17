package org.example.tell.and.ask;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import lombok.extern.slf4j.Slf4j;
import org.example.message.HelloMsg;
import org.example.tell.and.ask.actors.RootActor;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/15 10:14
 */
@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("system");
        ActorRef rootActor = system.actorOf(Props.create(RootActor.class), "root-actor");
        //tell
        rootActor.tell(new HelloMsg.Request(), ActorRef.noSender());
        //ask1
        Future<Object> ask = Patterns.ask(system.actorSelection("/user/root-actor/hello-actor"),
                new HelloMsg.Request(), Timeout.apply(1, TimeUnit.SECONDS));
        Object result = ask.result(Duration.apply(1, TimeUnit.SECONDS), null);
        log.info("{}", result);
        //ask2
        ask = Patterns.ask(system.actorSelection("/user/root-actor/hello-actor"),
                new HelloMsg.CleanListRequest(), Timeout.apply(1, TimeUnit.SECONDS));
        result = Await.result(ask, Duration.apply(1, TimeUnit.SECONDS));
        log.info("{}", result);
    }
}
