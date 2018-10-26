package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

import java.time.Duration;

import static ru.yusdm.reactprogramming.training.reactor.operators.CommonUtils.sleepSecs;

public class Sample {

    public static void main(String args[]){
        testSample();
    }

    private static void testSample(){

        Flux.range(1, 10)
                .delayElements(Duration.ofMillis(500))
                .log()
                .sample(Duration.ofMillis(2000))
                .log()
                .subscribe(System.out::println);

        sleepSecs(10);
    }
}
