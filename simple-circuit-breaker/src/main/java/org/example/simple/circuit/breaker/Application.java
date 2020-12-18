package org.example.simple.circuit.breaker;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.CircuitBreaker;
import akka.pattern.Patterns;
import akka.util.Timeout;
import lombok.extern.slf4j.Slf4j;
import org.example.simple.circuit.breaker.actor.CircuitBreakerActor;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/18 16:43
 */
@Slf4j
public class Application {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("circuit-breaker-system");
        ActorRef actorRef = system.actorOf(Props.create(CircuitBreakerActor.class), "circuit-breaker-actor");
        CircuitBreaker circuitBreaker = new CircuitBreaker(
                system.scheduler(),
                3,
                FiniteDuration.create(1, TimeUnit.SECONDS),
                FiniteDuration.create(1, TimeUnit.SECONDS),
                system.dispatcher()
        ).onOpen(() -> {
            log.info("CircuitBreaker::onOpen...");
        }).onClose(() -> {
            log.info("CircuitBreaker::onClose...");
        }).onHalfOpen(() -> {
            log.info("CircuitBreaker::onHalfOpen...");
        });
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Future<Object> objectFuture = circuitBreaker.callWithCircuitBreaker(() -> Patterns.ask(actorRef,
                    ("hello-" + finalI), Timeout.apply(1, TimeUnit.SECONDS)));
            try {
                Object result = Await.result(objectFuture, Duration.apply(1, TimeUnit.SECONDS));
                log.info("{} # Await.result: {}", finalI, result);
            } catch (Exception e) {
                log.error((finalI + " # Await.result error:"), e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
