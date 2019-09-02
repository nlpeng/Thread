package com.nlpeng;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class Thread12A {
    public static void main(String[] args) {
        final ShareData sd = new ShareData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26 ; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sd.addABC();
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26 ; i++) {
                    sd.addnum();
                }
            }
        },"B").start();


    }
}

class ShareData{
    private int a = 1;
    private char c ='A';

    private boolean bool = true;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void addABC(){
        lock.lock();
        try {
            while (bool){
                condition.await();
            }
            bool = true;
            System.out.print(c+++"\t");
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void addnum(){
        lock.lock();
        try {
            while (!bool){
                condition.await();
            }
            bool=false;
            System.out.print(""+(a++)+"\t"+(a++)+"\t");
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}