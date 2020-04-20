package com.concurrent.juc;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 令牌桶限流
 */
public class RateLimiterTest {

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = simpleDateFormat.format(new Date());
        //平稳性
        RateLimiter rateLimiter = RateLimiter.create(1.0);
        //渐变性
        RateLimiter rateLimiter2 = RateLimiter.create(1.0,2, TimeUnit.SECONDS);
        for(int i=1;i<10;i++){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double waitTime = rateLimiter.acquire();
            System.out.println("cutTime="+System.currentTimeMillis()+" call execute:"+i+" waitTime:"+waitTime);
        }
        System.out.println("startTime:"+start);
        System.out.println("endTime:"+simpleDateFormat.format(new Date()));
    }

}
