package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class CombineReactive {

    public static void main(String args[]) {
        testConcat2();
        //testConcat();
        //   Flux.interval(Duration.ofMillis(1)).take(5).subscribe(i->System.out.print(i+","));
        // testMerge();
        // sleepSec(100);
    }

    private static void sleepSec(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * A_0, A_1, A_2, A_3, A_4, A_5, A_6, A_7, A_8, A_9,
     */
    private static void testConcat() {
        Flux.concat(
                //it never ends so in subscribtion we will not see it
                Flux.interval(Duration.ofSeconds(1)).map(id -> "A_" + id),
                Flux.interval(Duration.ofSeconds(1)).map(id -> "B_" + id)
        ).subscribe(i -> System.out.print(i + ", "));
        sleepSec(10);
    }

    /**
     * A_0, A_1, A_2, A_3, A_4,      B_0, B_1, B_2, B_3, B_4,
     */
    private static void testConcat2() {
        Flux.concat(
                Flux.interval(Duration.ofSeconds(1)).takeWhile(i -> i < 5).map(id -> "A_" + id),
                Flux.interval(Duration.ofSeconds(1)).takeWhile(i -> i < 5).map(id -> "B_" + id)
        ).subscribe(i -> System.out.print(i + ", "));
        sleepSec(15);
    }

    /**
     * A_0, B_0,   A_1, B_1,    A_2, B_2,     A_3, B_3,
     */
    private static void testMerge() {
        Flux.merge(
                Flux.interval(Duration.ofSeconds(1)).map(id -> "A_" + id),
                Flux.interval(Duration.ofSeconds(1)).map(id -> "B_" + id)
        ).subscribe(i -> System.out.print(i + ", "));
        sleepSec(5);
    }
}
