package org.fenixsoft.jvm.chapter12;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {
    private static final int THREAD_COUNT = 20;

    private static volatile AtomicLong race = new AtomicLong(0);

    public static void increase() {
        race.getAndIncrement();
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

        //等所有累加线程都结束
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("race: " + race);
    }
}
