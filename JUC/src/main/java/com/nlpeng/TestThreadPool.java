package com.nlpeng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class TestThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolDemo tpd = new ThreadPoolDemo();
        List<Future<Integer>> list = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int j = 0; j < 100; j++) {
                        sum += j;
                    }
                    return sum;
                }
            });
            list.add(future);
        }
        for (Future<Integer> future:list) {
            System.out.println(future.get());
        }
        for (int i = 0; i < 10; i++) {
            pool.submit(tpd);
        }
        pool.shutdown();
    }
}

class ThreadPoolDemo implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}