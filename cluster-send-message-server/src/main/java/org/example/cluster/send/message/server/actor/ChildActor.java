package org.example.cluster.send.message.server.actor;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.message.ChildMessage;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 09:39
 */
@Slf4j
@AllArgsConstructor
public class ChildActor extends AbstractActor {
    Cluster cluster;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ChildMessage.Request.class, this::onMessage)
                .matchAny(this::onMessage)
                .build();
    }

    private void onMessage(ChildMessage.Request m) {
        log.info("receive: {}", m);
        sender().tell(new ChildMessage.Response().setRequestMessage(m.getMessage())
                .setReceivedTime(System.currentTimeMillis()), self());
    }

    private void onMessage(Object m) {
        log.info("Other: {}", m);
        unhandled(m);
    }
}
