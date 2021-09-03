package org.fenixsoft.jvm.chapter12;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest {
    private static final int THREAD_COUNT = 20;

    //默认初始化为0值
    private static volatile LongAdder race = new LongAdder();

    public static void increase() {
        race.increment();
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    increase();
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("race: " + race);
    }
}
