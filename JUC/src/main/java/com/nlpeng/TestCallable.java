package com.nlpeng;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class TestCallable {
    public static void main(String[] args) {
        CallableDemo  cd = new CallableDemo();
        FutureTask<Integer> result = new FutureTask<>(cd);
        new Thread(result).start();
        try {
            Integer in = result.get();
            System.out.println(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class  CallableDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <=100 ; i++) {
            sum += i;
        }
        return sum;
    }
}