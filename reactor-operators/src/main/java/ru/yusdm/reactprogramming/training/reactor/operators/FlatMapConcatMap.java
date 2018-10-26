package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class FlatMapConcatMap {

    private static final Flux<String> RUS_CITIES = Flux.just("Spb", "Msc");
    private static final Flux<String> BLR_CITIES = Flux.just("Minsk");
    private static final Flux<String> UKR_CITIES = Flux.just("Kiev", "Odessa");


    public static void main(String args[]) {
        testFlatMap_1();
        //  testFlatMap_2();
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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Flux<String> getCitiesByCountry_2(String country) {
        switch (country) {
            case "Russia": {
                return RUS_CITIES.delayElements(Duration.ofMillis(1000));
            }
            case "Belarus": {
                return BLR_CITIES.delayElements(Duration.ofMillis(15));
            }
            default: {
                return UKR_CITIES.delayElements(Duration.ofMillis(25));
            }
        }
    }

}
