package com.nlpeng;

/**
 * @author Ferry NLP
 * @create 2019-08-14
 * @see
 * @since 1.0v
 **/
public class TestYield {

    public static void main(String[] args) {
        YieldDemo y1 = new YieldDemo();
        YieldDemo y2 = new YieldDemo();
        y1.start();
        y2.start();


    }
}

class YieldDemo extends Thread{
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(this);
            Thread.yield();
        }
    }
}