package com.concurrent.juc;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static AtomicInteger atomicInteger=new AtomicInteger(0);
    public static volatile int volatileInt=0;


    public static void main(String[] args) throws InterruptedException {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                Integer a = atomicInteger.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + "-" + a);
                set1.add(a);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                Integer a = atomicInteger.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + "-" + a);
                set1.add(a);
            }
        });

        Thread thread6 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                Integer a = atomicInteger.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + "-" + a);
                set1.add(a);
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + volatileInt);
                set2.add(volatileInt++);
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + volatileInt);
                set2.add(volatileInt++);
            }
        });

        Thread thread5 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + volatileInt);
                set2.add(volatileInt++);
            }
        });

        thread1.start();
        thread2.start();
        thread6.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        System.out.println("atomic自增大小:" + set1.size());
        System.out.println("volatile自增大小:" + set2.size());
        System.out.println(atomicInteger.get());
    }
}
