package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

import java.time.Duration;

import static ru.yusdm.reactprogramming.training.reactor.operators.CommonUtils.sleepSecs;

public class FlatMapConcatMap {

    private static final Flux<String> RUS_CITIES = Flux.just("Spb", "Msc");
    private static final Flux<String> BLR_CITIES = Flux.just("Minsk");
    private static final Flux<String> UKR_CITIES = Flux.just("Kiev", "Odessa");


    public static void main(String args[]) {
        // testFlatMap_1();
        //  testFlatMap_2();

        //testFlatMapSequential();
        //testFlatMapWithError();
    }

    /**
     * main Spb
     * main Msc
     * main Minsk
     * main Kiev
     * main Odessa
     */
    private static void testFlatMap_1() {
        Flux<String> countries = Flux.just("Russia", "Belarus", "Ukraine");
        countries.flatMap(FlatMapConcatMap::getCitiesByCountry_1)
                .subscribe(city -> {
                    System.out.println(Thread.currentThread().getName() + " " + city);
                });
    }

    private static Flux<String> getCitiesByCountry_1(String country) {
        switch (country) {
            case "Russia": {
                return RUS_CITIES;
            }
            case "Belarus": {
                return BLR_CITIES;
            }
            default: {
                return UKR_CITIES;
            }
        }
    }

    /**
     * Order is not defined, so flatMap play like = map + merge
     * <p>
     * parallel-2 Minsk
     * parallel-3 Kiev
     * parallel-4 Odessa
     * parallel-1 Spb
     * parallel-5 Msc
     */
    private static void testFlatMap_2() {
        Flux<String> countries = Flux.just("Russia", "Belarus", "Ukraine");
        countries.flatMap(FlatMapConcatMap::getCitiesByCountry_2)
                .subscribe(city -> {
                    System.out.println(Thread.currentThread().getName() + " " + city);
                });
        sleepSecs(30);
    }

    /**
     * Order is defined, so flatMap play like = map + concat
     * <p>
     * parallel-1 Spb
     * parallel-4 Msc
     * parallel-4 Minsk
     * parallel-3 Kiev
     * parallel-5 Odessa
     */
    private static void testFlatMapSequential() {
        Flux<String> countries = Flux.just("Russia", "Belarus", "Ukraine");
        countries.flatMapSequential(FlatMapConcatMap::getCitiesByCountry_2)
                .subscribe(city -> {
                    System.out.println(Thread.currentThread().getName() + " " + city);
                });

        sleepSecs(30);
    }


    private static Flux<String> getCitiesByCountry_2(String country) {
        switch (country) {
            case "Russia": {
                return RUS_CITIES.delayElements(Duration.ofMillis(1000));
            }
            case "Belarus": {
                return BLR_CITIES.delayElements(Duration.ofMillis(150));
            }
            default: {
                return UKR_CITIES.delayElements(Duration.ofMillis(2500));
            }
        }
    }

    private static void testFlatMapWithError() {
        Flux<String> countries = Flux.just("Russia", "Belarus", "Ukraine");
        countries.flatMapSequential(FlatMapConcatMap::getCitiesByCountry_2)
                .subscribe(city -> {
                    System.out.println(Thread.currentThread().getName() + " " + city);
                    if (city.equals("Msc") || city.equals("Kiev")) {
                        throw new RuntimeException("Bad city '" + city + "'");
                    }
                });

        sleepSecs(30);
    }

}
