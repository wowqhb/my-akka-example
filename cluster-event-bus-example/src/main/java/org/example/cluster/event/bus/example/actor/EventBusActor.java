package org.example.cluster.event.bus.example.actor;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 09:39
 */
@Slf4j
public class EventBusActor extends AbstractActor {

    {
        receive(
                ReceiveBuilder
                        .matchAny(this::onAnyMessage)
                        .build()
        );
    }

    private void onAnyMessage(Object m) {
        log.info("{} Other: {}", Cluster.get(context().system()).selfAddress(), m);
    }
}
