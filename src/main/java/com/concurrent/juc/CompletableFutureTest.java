package com.concurrent.juc;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureTest {

    static LinkedBlockingQueue<A> queue = new LinkedBlockingQueue<>();
    static AtomicInteger count = new AtomicInteger(0);
    static final int size = 100000;
    static CountDownLatch countDownLatch = new CountDownLatch(size);

    static {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    int size = queue.size();
                    if (size==0){
                        return;
                    }
                    List<A> list = new ArrayList<>(size);
                    for (int i=0 ;i<size;i++){
                        list.add(queue.poll());
                    }
                    System.out.println("本次合并请求数量："+list.size());
                    count.addAndGet(list.size());

                    for (A item : list){
                        String key = item.getKey();
                        CompletableFuture<Map<String,String>> future = item.getFuture();
                        Map<String,String> map = new HashMap<>();
                        map.put(key,key);
                        future.complete(map);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },0,1,TimeUnit.MILLISECONDS);
       
    }

    @Setter
    @Getter
    static final class A{
        
        private String key;

        private CompletableFuture<Map<String,String>> future;

        public A(){};

        public A(String key,CompletableFuture<Map<String,String>> future){
            this.key = key;
            this.future = future;
        }
    }

    static class TestRequest implements Runnable{

        private int i;

        TestRequest(int i){
            this.i = i;
        }

        @Override
        public void run() {
            A a = new A();
            a.setKey("k"+i);
            CompletableFuture<Map<String,String>> future = new CompletableFuture<>();
            a.setFuture(future);
            try {
                queue.put(a);
                Map<String,String> map = future.get();
                countDownLatch.countDown();
//                System.out.print(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = new ThreadPoolExecutor(
                3000,
                10000,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(80),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i=0;i<size;i++){
            executorService.execute(new TestRequest(i));
        }
        countDownLatch.await();
        System.out.println("共请求"+count.get());
//        executorService.shutdownNow();
  
    }
    
}

