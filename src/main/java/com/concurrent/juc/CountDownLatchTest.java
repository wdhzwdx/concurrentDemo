package com.concurrent.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static CountDownLatch count = new CountDownLatch(5);

    static class BossThread extends Thread{
        @Override
        public void run(){
            try {
                System.out.println("Boss到会议室了，共有"+count.getCount()+"员工要到");
                count.await();
            }catch (Exception e){

            }
            System.out.println("还剩"+count.getCount()+"人,，可以开会了，");
        }
    }

    static class EmpleThread extends Thread{
        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName()+"到了");
            count.countDown();
        }
    }

    public static void main(String[] args) {
        new BossThread().start();
        for(int i=0 ;i<5;i++){
            new EmpleThread().start();
        }
    }

}
