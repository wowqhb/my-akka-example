package org.example.simple.circuit.breaker.actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/18 16:45
 */
@Slf4j
public class CircuitBreakerActor extends AbstractActor {

    {
        receive(
                ReceiveBuilder.matchAny(this::onAnyMessage).build()
        );
    }

    boolean b = false;
    AtomicInteger atomicInteger = new AtomicInteger(0);

    private void onAnyMessage(Object o) {
        log.info("receive message: {}", o);
        int i = atomicInteger.incrementAndGet() % 10;
        if (i == 0) {
            b = !b;
        }
        if (b) {
            try {
                log.info("sleep 2s...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return;
        }
        log.info("response message...");
        sender().tell("receive: \"" + o + "\"", self());
    }
}
