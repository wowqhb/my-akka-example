package org.example.cluster.send.message.server.actor;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.message.ChildMessage;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 09:39
 */
@Slf4j
public class WorkerActor extends AbstractActor {

    {
        receive(
                ReceiveBuilder
                        .match(ChildMessage.Request.class, this::onMessage)
                        .matchAny(this::onAnyMessage)
                        .build()
        );
    }

    private void onMessage(ChildMessage.Request m) {
        log.info("{} receive: {}", Cluster.get(context().system()).selfAddress(), m);
        sender().tell(new ChildMessage.Response().setRequestMessage(m.getMessage())
                .setReceivedTime(System.currentTimeMillis()), self());
    }

    private void onAnyMessage(Object m) {
        log.info("{} Other: {}", Cluster.get(context().system()).selfAddress(), m);
        unhandled(m);
    }
}
