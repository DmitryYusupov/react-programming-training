package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

public class Batching {

    public static void main(String[] args) {
        //testBuffer();
        //testWindow();
        //testWindowUntil();
        //testWindowUntil2(false);
        testWindowUntil2(true);
        // testGroup();
        // testGroup2();
    }

    /**
     * onNext([1, 2, 3, 4, 5])
     * [1, 2, 3, 4, 5]
     * <p>
     * [ INFO] (main) onNext([6, 7, 8, 9, 10])
     * [6, 7, 8, 9, 10]
     * <p>
     * [ INFO] (main) onNext([11, 12, 13, 14, 15])
     * [11, 12, 13, 14, 15]
     * <p>
     * [ INFO] (main) onNext([16, 17, 18, 19, 20])
     * [16, 17, 18, 19, 20]
     * <p>
     * [ INFO] (main) onComplete()
     */
    private static void testBuffer() {
        Flux.range(1, 20)
                .buffer(5)
                .log()
                .subscribe(listOfInts -> System.out.println(listOfInts));

    }

    /**
     * 1,2,3,4,5,[ INFO] (main) onNext(UnicastProcessor)
     * 6,7,8,9,10,[ INFO] (main) onNext(UnicastProcessor)
     * 11,12,13,14,15,[ INFO] (main) onNext(UnicastProcessor)
     * 16,17,18,19,20,[ INFO] (main) onComplete()
     */
    private static void testWindow() {
        Flux.range(1, 20)
                .window(5)
                .log()
                .subscribe(fluxOfInts -> fluxOfInts.subscribe(i -> System.out.print(i + ",")));

    }

    /**
     * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
     * [ INFO] (main) | onNext(WindowFlux)
     * [17]
     * [ INFO] (main) | onNext(WindowFlux)
     * [18]
     * [ INFO] (main) | onNext(WindowFlux)
     * [19]
     * [ INFO] (main) | onNext(WindowFlux)
     * [20]
     * [ INFO] (main) | onComplete()
     */
    private static void testWindowUntil() {
        Flux.range(1, 20)
                .windowUntil(i -> i > 15)
                .log()
                .subscribe(fluxOfInts -> {
                    fluxOfInts.collectList().subscribe(System.out::println);
                });
    }


    /**
     * CUT BEFORE = FALSE
     * <p>
     * [ INFO] (main) | onNext(WindowFlux)
     * [1, 2, 3, 4, 5]
     * [ INFO] (main) | onNext(WindowFlux)
     * [6, 7, 8, 9, 10]
     * [ INFO] (main) | onNext(WindowFlux)
     * [11, 12, 13, 14, 15]
     * [ INFO] (main) | onNext(WindowFlux)
     * [16, 17, 18, 19, 20]
     * <p>
     * <p>
     * <p>
     * CUT BEFORE = TRUE
     * [ INFO] (main) | request(unbounded)
     * [ INFO] (main) | onNext(WindowFlux)
     * [1, 2, 3, 4]
     * [ INFO] (main) | onNext(WindowFlux)
     * [5, 6, 7, 8, 9]
     * [ INFO] (main) | onNext(WindowFlux)
     * [10, 11, 12, 13, 14]
     * [ INFO] (main) | onNext(WindowFlux)
     * [15, 16, 17, 18, 19]
     * [ INFO] (main) | onNext(WindowFlux)
     * [20]
     *
     * @param cutBefore
     */
    private static void testWindowUntil2(boolean cutBefore) {
        Flux.range(1, 20)
                .windowUntil(i -> i % 5 == 0, cutBefore)
                .log()
                .subscribe(fluxOfInts -> {
                    fluxOfInts.collectList().subscribe(System.out::println);
                });
    }

    /**
     * [ INFO] (main) | onNext(UnicastGroupedFlux)
     * [ INFO] (main) | onNext(UnicastGroupedFlux)
     * [ INFO] (main) | onNext(UnicastGroupedFlux)
     * [ INFO] (main) | onNext(UnicastGroupedFlux)
     * [Petr]
     * [Semen, Sergey]
     * [Dmitry, Denis]
     * [Ivan, Igor]
     * [ INFO] (main)
     */
    private static void testGroup() {
        Flux.just("Ivan", "Petr", "Dmitry", "Semen", "Sergey", "Denis", "Igor")
                .groupBy(name -> name.charAt(0) + "")
                .log()
                .subscribe(group -> {
                    group.collectList()
                            .subscribe(listOfNames -> {
                                System.out.println(listOfNames);
                            });
                });

    }


    /**
     * [ INFO] (main) | request(unbounded)
     * [ INFO] (main) | onNext(UnicastGroupedFlux)
     * [ INFO] (main) | onNext(UnicastGroupedFlux)
     * [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
     * [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
     */
    private static void testGroup2() {
        Flux.range(1, 20)
                .groupBy(i -> i % 2 == 0)
                .log()
                .subscribe(group -> {
                    group.collectList().subscribe(itemsInGroup -> System.out.println(itemsInGroup));
                });
    }
}
