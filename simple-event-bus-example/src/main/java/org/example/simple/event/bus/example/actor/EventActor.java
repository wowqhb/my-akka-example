package org.example.simple.event.bus.example.actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/18 15:04
 */
@Slf4j
public class EventActor extends AbstractActor {

    {
        receive(
                ReceiveBuilder
                        .matchAny(this::onAnyMessage)
                        .build()
        );
    }

    private void onAnyMessage(Object o) {
        log.info("{} receive any message: {}", self(), o);
    }
}
