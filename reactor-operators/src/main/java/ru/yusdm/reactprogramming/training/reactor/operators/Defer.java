package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static ru.yusdm.reactprogramming.training.reactor.operators.CommonUtils.sleepSecs;

public class Defer {

    public static void main(String args[]) {
        //TEST MONO
        //testMonoJustWithoutSubscribe();
        //testMonoJustWithSubscribe();

        //testMonoFromSupplierWithoutSubscribe();
        //testMonoFromSupplierWithSubscribe();

        //testMonoDeferWithoutSubscribe();
        //testMonoDeferWithSubscribe();


        //TEST FLUX
        //testFluxJustWithoutSubscribe();
        //testFluxJustWithSubscribe();

        //testFluxDeferWithoutSubscribe();
        testFluxDeferWithSubscribe();



    }

    /**
     * Get names is calling
     * <p>
     * [Ivan, Petr]
     */
    private static void testMonoJustWithSubscribe() {
        Mono.just(getNames()).subscribe(System.out::println);
    }

    /**
     * Get names is calling
     */
    private static void testMonoJustWithoutSubscribe() {
        Mono.just(getNames());
    }

    /**
     * Get names is calling
     * <p>
     * [Ivan, Petr]
     */
    private static void testFluxJustWithSubscribe() {
        Flux.just(getNames()).subscribe(System.out::println);
    }

    /**
     * Get names is calling
     */
    private static void testFluxJustWithoutSubscribe() {
        Flux.just(getNames());
    }

    /**
     * getNames() Did not called!!!
     */
    private static void testMonoFromSupplierWithoutSubscribe() {
        Mono.fromSupplier(() -> getNames());
    }

    /**
     * Get names is calling
     * <p>
     * [Ivan, Petr]
     */
    private static void testMonoFromSupplierWithSubscribe() {
        Mono.fromSupplier(() -> getNames()).subscribe(System.out::println);
    }

    /**
     * getNames() Did not called!!!
     */
    private static void testMonoDeferWithoutSubscribe() {
        Mono.defer(() -> Mono.just(getNames()));
    }

    /**
     * getNames() Did not called!!!
     */
    private static void testMonoDeferWithSubscribe() {
        Mono.defer(() -> Mono.just(getNames())).subscribe(System.out::println);
    }

    /**
     * getNames() Did not called!!!
     */
    private static void testFluxDeferWithoutSubscribe() {
        Flux.defer(() -> Flux.just(getNames()));
    }

    /**
     * getNames() Did not called!!!
     */
    private static void testFluxDeferWithSubscribe() {
        Flux.defer(() -> Flux.just(getNames())).subscribe(System.out::println);
    }

    private static List<String> getNames() {
        List<String> names = new ArrayList<>();
        System.out.println("Get names is calling");
        sleepSecs(2);
        names.add("Ivan");
        names.add("Petr");
        return names;
    }


}
