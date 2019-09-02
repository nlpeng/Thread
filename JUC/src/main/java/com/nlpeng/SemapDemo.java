package com.nlpeng;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Ferry NLP
 * @create 2019-08-16
 * @see
 * @since 1.0v
 **/
public class SemapDemo implements Runnable{
    final Semaphore semap = new Semaphore(5);
    @Override
    public void run() {
        try {
            semap.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"__-");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semap.release();
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemapDemo demo = new SemapDemo();
        for (int i = 0; i < 20 ; i++) {
            executorService.submit(demo);
        }
    }
}
