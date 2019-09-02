package com.nlpeng;

/**
 * @author Ferry NLP
 * @create 2019-08-14
 * @see
 * @since 1.0v
 **/
public class TestJoin {
    public static void main(String[] args) {
        JoinThread t1 = new JoinThread();
        JoinThread t2 = new JoinThread();
        t1.start();
        t2.start();


        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);

        }
    }
}

class JoinThread extends Thread{
    public void run(){
        for (int i = 0; i < 100 ; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);

        }
        System.out.println(Thread.currentThread().getName()+"over");
    }
}