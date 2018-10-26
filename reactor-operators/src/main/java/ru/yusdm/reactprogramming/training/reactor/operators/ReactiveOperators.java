package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReactiveOperators {

    public static void main(String args[]) {
        //indexOperator();
        //filterOperator();


        //skipTakeOperators_1();
        //skipTakeOperators_2();
        //skipTakeOperators_3();
        //skipTakeOperators_4();
        //skipTakeOperators_5();
        skipTakeOperators_6();
    }

    /**
     * 0-10
     * 1-11
     * 2-12
     * ....
     * 9-19
     */
    private static void indexOperator() {
        Flux.range(10, 10).index().subscribe(tuple2 -> {
            System.out.println("Index " + tuple2.getT1() + " Value = " + tuple2.getT2());
        });
    }

    /**
     * 3,6,9  3,6,9  3,6,9 ..... 3,6,9
     */
    private static void filterOperator() {
        Flux.range(1, 10).repeat().filter(i -> i % 3 == 0).subscribe(System.out::println);
    }

    /**
     * 4,5,6....10
     */
    private static void skipTakeOperators_1() {
        Flux.range(1, 10)
                //untilPredicate the {@link Predicate} evaluated to stop skipping
                .skipUntil(i -> i > 3)
                .subscribe(System.out::println);
    }

    /**
     * Out put is : 1
     * It took 1, 1 is less than 8, so it takes and that is all
     * <p>
     * It is like repeat-until loop - body is executed at least one time
     */
    private static void skipTakeOperators_2() {
        Flux.range(1, 10)
                //@param predicate the {@link Predicate} that stops the taking of values from this {@link Flux}
                .takeUntil(i -> i <= 8)
                .subscribe(System.out::println);
    }

    /*
     * Out put is : 1,2,3,4,5,..,8
     */
    private static void skipTakeOperators_3() {
        Flux.range(1, 10)
                //@param predicate the {@link Predicate} that stops the taking of values from this {@link Flux}
                .takeUntil(i -> i >= 8)
                .subscribe(System.out::println);
    }

    /*
     * Out put is : 4,5,6,7,8
     */
    private static void skipTakeOperators_4() {
        Flux.range(1, 10).
                skipUntil(i -> i > 3)
                //@param predicate the {@link Predicate} that stops the taking of values from this {@link Flux}
                .takeUntil(i -> i >= 8)
                .subscribe(System.out::println);
    }

    /**
     * 4, 1, 2, 3, 4, 5, 6, 7, 8
     */
    private static void skipTakeOperators_5() {
        Flux.just(1, 2, 3, 4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).
                skipUntil(i -> i > 3)
                //@param predicate the {@link Predicate} that stops the taking of values from this {@link Flux}
                .takeUntil(i -> i >= 8)
                .subscribe(i -> {
                    System.out.print(i + ", ");
                });
    }

    /**
     * parallel-1 4
     * parallel-1 5
     * parallel-1 6
     * parallel-1 7
     * parallel-1 8
     * parallel-1 Complete
     */
    private static void skipTakeOperators_6() {
        Flux.interval(Duration.ofMillis(500))
                .skipUntil(i -> i > 3)
                .takeUntil(i -> i >= 8)
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " " + i),
                        (throwable -> {
                        }),
                        () -> {
                            System.out.println(Thread.currentThread().getName() + " Complete");
                        });
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
