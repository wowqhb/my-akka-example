package org.example.tell.and.ask.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.message.HelloMsg;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/15 10:35
 */
@Slf4j
public class RootActor extends AbstractActor {
    ActorRef child = context().actorOf(Props.create(HelloActor.class), "hello-actor");

    {
        receive(
                ReceiveBuilder
                        .match(HelloMsg.Request.class, this::onMessage)
                        .matchAny(this::onAnyMessage)
                        .build()
        );
    }

    private void onAnyMessage(Object m) {
        log.info("Object: {}", m);
        unhandled(m);
    }


    private void onMessage(HelloMsg.Request m) {
        log.info("HelloMsg.Request: {}", m);
    }
}
