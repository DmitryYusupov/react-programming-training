package ru.yusdm.reactprogramming.training.reactor.operators;

public class CommonUtils {

    public static void sleepSecs(int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
