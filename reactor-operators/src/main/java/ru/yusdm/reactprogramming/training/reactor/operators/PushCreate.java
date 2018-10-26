package ru.yusdm.reactprogramming.training.reactor.operators;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class PushCreate {

    public static void main(String args[]){
        /*Flux.defer(new Supplier<Publisher<? extends Object>>() {
            @Override
            public Publisher<? extends Object> get() {
                return null;
            }
        });*/
        Mono.just(getNames());
        //Mono.fromSupplier(()->getNames());
        Mono.defer((Supplier<Mono<List<String>>>) () -> Mono.just(getNames())).subscribe();

    }

    private static List<String> getNames(){
        System.out.println("Getting names");
        return Arrays.asList("a","b", "c");
    }
}
