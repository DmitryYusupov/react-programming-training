package ru.yusdm.reactprogramming.training.reactor.operators;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

import static ru.yusdm.reactprogramming.training.reactor.operators.CommonUtils.sleepSecs;

public class ReactiveTryWithResourcesUsingWhenFromBook {

    public static void main(String args[]){
        Flux.usingWhen(
                Transaction.beginTransaction(), // (1)
                transaction -> transaction.insertRows(Flux.just("A", "B", "C")), // (2)
                Transaction::commit, // (3)
                Transaction::rollback // (4)
        ).subscribe(
                d -> {System.out.println("onNext: {}" + d);},
                e -> {System.out.println("onError: " + e.getMessage());} ,
                () -> {System.out.println("onComplete");}
        );

        sleepSecs(3);
    }


    private static class Transaction {
        private final int id;

        public Transaction(int id) {
            this.id = id;
            System.out.println("[T: {}] created" + id);
        }

        public static Mono<Transaction> beginTransaction() { // (1)
            return Mono.defer(() ->
                    Mono.just(new Transaction(new Random().nextInt(1000))));
        }
        public Flux<String> insertRows(Publisher<String> rows) { // (2)
            return Flux.from(rows)
                    .delayElements(Duration.ofMillis(100))
                    .flatMap(r -> {
                        if (new Random().nextInt(10) < 2) {
                            return Mono.error(new RuntimeException("Error: " + r));
                        } else {
                            return Mono.just(r);
                        }
                    });
        }
        public Mono<Void> commit() { // (3)

            return Mono.defer(() -> {
                System.out.println("[T: {}] commit " + id);
                if (new Random().nextBoolean()) {
                    return Mono.empty();
                } else {
                    return Mono.error(new RuntimeException("Conflict"));
                }
            });
        }
        public Mono<Void> rollback() { // (4)
            return Mono.defer(() -> {
                System.out.println("[T: {}] rollback " + id);
                if (new Random().nextBoolean()) {
                    return Mono.empty();
                } else {
                    return Mono.error(new RuntimeException("Conn error"));
                }
            });
        }
    }


}
