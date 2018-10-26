package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ReduceStreamElems {

    public static void main(String[] args) {
        //testScan();
        //testReduce();
        //   sort();
//        testHasElementsFalse();
//        testHasElementsTrue();
//        testHasElement();
//        testAnyFalse();
//        testAnyTrue();
    }

    /**
     * Result 3
     * Result 4
     * Result 6
     * Result 9
     * Result 13
     * Result 18
     */
    private static void testScan() {
        Flux.just(1, 2, 3, 4, 5)
                .scan(3, (elem1, elem2) -> elem1 + elem2)
                .subscribe(result -> System.out.println("Result " + result));
    }

    /**
     * 3 + 1 = 4
     * 4 + 2 = 6
     * 6 + 3 = 9
     * 9 + 4 = 13
     * 13 + 5 = 18
     * <p>
     * Output is 18
     */
    private static void testReduce() {
        Flux.just(1, 2, 3, 4, 5).reduce(3, (elem1, elem2) -> elem1 + elem2)
                .subscribe(result -> System.out.println("Result " + result));
    }

    private static void sort() {
        Flux.just(1, 3, 9, 2, 90, 55, 67).sort().subscribe(i -> System.out.print(i + ","));
    }

    /**
     * Has elements false
     */
    private static void testHasElementsFalse() {
        Flux.just()
                .hasElements()
                .subscribe(hasEvens -> System.out.println("Has elements " + hasEvens));
    }

    /**
     * check whether a stream has any elements
     */
    private static void testHasElementsTrue() {
        Flux.just(3, 5, 7, 9, 11, 15, 17, 17)
                .hasElements()
                .subscribe(hasEvens -> System.out.println("Has elements " + hasEvens));
    }

    private static void testHasElement() {
        Flux.just(3, 5, 7, 9, 11, 15, 17, 17)
                .hasElement(17)
                .subscribe(hasEvens -> System.out.println("Has events " + hasEvens));
    }

    /**
     * Has events false
     */
    private static void testAnyFalse() {
        Flux.just(3, 5, 7, 9, 11, 15, 17, 17)
                .any(e -> e % 2 == 0)
                .subscribe(hasEvens -> System.out.println("Has events " + hasEvens));
    }

    /**
     * Has events true
     */
    private static void testAnyTrue() {
        Flux.just(3, 5, 7, 9, 11, 15, 17, 18)
                .any(e -> e % 2 == 0)
                .subscribe(hasEvens -> System.out.println("Has events " + hasEvens));
    }
}
