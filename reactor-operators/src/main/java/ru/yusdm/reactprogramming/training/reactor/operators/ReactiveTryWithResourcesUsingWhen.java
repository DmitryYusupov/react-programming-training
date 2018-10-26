package ru.yusdm.reactprogramming.training.reactor.operators;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class ReactiveTryWithResourcesUsingWhen {

    public static void main(String args[]) {
        testUsingWhen();
    }

    public static void testUsingWhen() {
        convertStringToInt("11").

                subscribe(
                data -> System.out.println("Result " + data),
                e -> System.out.println("Error " + e.getMessage()),
                () -> System.out.println("Stream finished")

                );
    }

    private static Mono<Integer> convertStringToInt(String intStrValue) {
/*
        Mono<Transaction> beginTransaction = Mono.defer(() -> {
            return Mono.fromSupplier(() -> {
                Transaction transaction = new Transaction();
                transaction.begin();
                return transaction;
            });
        });
*/

        Mono<Transaction> beginTransaction = Mono.defer(() -> Mono.just(new Transaction()));

        return Mono.usingWhen(
                beginTransaction,

                transaction -> {
                    int result = transaction.body(intStrValue);
                    return Mono.just(result);
                },

                transaction -> {
                    return Mono.defer(()->{
                        transaction.commit();
                        return Mono.empty();
                    });
                },

                transaction -> {
                    return Mono.defer(()->{
                        transaction.rollback();
                        return Mono.empty();
                    });
                }
        );
    }

    private interface Transactional<Input, Output> {
        void begin();

        Output body(Input input);

        void commit();

        void rollback();
    }

    private static class Transaction implements Transactional<String, Integer> {
        private Integer result = null;
        private String transactionId = UUID.randomUUID().toString().substring(0, 4);

        public Transaction() {
            begin();
        }

        @Override
        public void begin() {
            System.out.println("[Transaction begin '" + transactionId + "']");
        }

        @Override
        public Integer body(String input) {
            System.out.println("[Transaction body in action '" + transactionId + "']");
            result = Integer.valueOf(input);
            return result;
        }

        @Override
        public void commit() {
            System.out.println("[Transaction commit '" + transactionId + "']");
        }

        @Override
        public void rollback() {
            System.out.println("[Transaction rollback '" + transactionId + "']");
            result = null;
        }
    }



}
