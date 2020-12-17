package org.example.tell.and.ask.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.message.HelloMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/15 11:17
 */
@Slf4j
public class HelloActor extends AbstractActor {
    List<String> stringList = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
            add("4");
        }
    };

    {
        receive(
                ReceiveBuilder
                        .match(HelloMsg.Request.class, this::onMessage)
                        .match(HelloMsg.CleanListRequest.class, this::onMessage)
                        .matchAny(this::onAnyMessage)
                        .build()
        );
    }

    private void onMessage(HelloMsg.CleanListRequest m) {
        log.info("HelloMsg.CleanListRequest: {}", m);
        sender().tell(new HelloMsg.CleanListResponse().setList(stringList), self());
    }

    private void onMessage(HelloMsg.Request m) {
        log.info("HelloMsg.Request: {}", m);
        sender().tell(new HelloMsg.Response().setTime(System.currentTimeMillis()).setList(new ArrayList<String>() {
            {
                add("1");
                add("2");
                add("3");
                add("4");
            }
        }), self());
    }

    private void onAnyMessage(Object m) {
        log.info("Object: {}", m);
        unhandled(m);
    }
}
