package org.example.simple.event.bus.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import lombok.extern.slf4j.Slf4j;
import org.example.event.EventBusMessage;
import org.example.simple.event.bus.example.actor.EventActor;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/18 14:15
 */
@Slf4j
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create();
        final ActorRef actor = system.actorOf(Props.create(EventActor.class), "event-actor");
        final ActorRef actor2 = system.actorOf(Props.create(EventActor.class), "event-actor2");
        /*
        使用system.eventStream()订阅/发布
        是最简单的方式,推荐
         */
        system.eventStream().subscribe(actor, EventBusMessage.class);
        system.eventStream().subscribe(actor2, String.class);
        Thread.sleep(3000);
        system.eventStream().publish(new EventBusMessage(System.currentTimeMillis()));
        system.eventStream().publish("sdfsdfsdf");
        system.eventStream().publish(new EventBusMessage(System.currentTimeMillis()), actor2);

    }
}
