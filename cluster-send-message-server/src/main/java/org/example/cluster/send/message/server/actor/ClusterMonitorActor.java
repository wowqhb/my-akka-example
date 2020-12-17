package org.example.cluster.send.message.server.actor;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.japi.pf.ReceiveBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/16 10:18
 */
@Slf4j
public class ClusterMonitorActor extends AbstractActor {
    /**
     * 初始化cluster
     */
    Cluster cluster = Cluster.get(context().system());

    {
        receive(
                ReceiveBuilder
                        .match(ClusterEvent.MemberEvent.class, this::apply)
                        .match(ClusterEvent.UnreachableMember.class, this::apply)
                        .matchEquals("leave", this::leave)
                        .matchEquals("shutdown", this::shutdown)
                        .matchAny(m -> log.info("Other: {}", m)).build()
        );
    }

    @Override
    public void preStart() {
        // 订阅集群事件
        cluster.subscribe(self(), ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(self());
    }


    private void shutdown(String m) {
        log.info("shutdown: {}", m);
        context().system().shutdown();
    }

    private void apply(ClusterEvent.UnreachableMember m) {
        log.info("UnreachableMember: {}", m);
    }

    private void leave(String m) {
        log.info("leave: {}", m);
        cluster.leave(cluster.selfAddress());
    }

    private void apply(ClusterEvent.MemberEvent m) {
        if (m instanceof ClusterEvent.MemberUp) {
            System.out.println("member up: " + m.member().address());
        }
        log.info("MemberEvent: {}", m);
    }

}
