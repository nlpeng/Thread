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
public class ThreadABC {
    public static void main(String[] args) {
        final AlternateDemo ad = new AlternateDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=20 ; i++) {
                    ad.loopA();
                }

            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=20 ; i++) {
                    ad.loopB();
                }

            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=20 ; i++) {
                    ad.loopC();
                    System.out.print("---");
                }

            }
        },"C").start();
    }
}

class AlternateDemo{
    private int number = 1;
    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(){
        lock.lock();
        try {
            if(number!=1){
                condition1.await();
            }
            for (int i = 0; i <1 ; i++) {
                System.out.print(Thread.currentThread().getName());
            }
            number=2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(){
        lock.lock();
        try {
            if(number!=2){
                condition2.await();
            }
            for (int i = 0; i <1 ; i++) {
                System.out.print(Thread.currentThread().getName());
            }
            number=3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(){
        lock.lock();
        try {
            if(number!=3){
                condition3.await();
            }
            for (int i = 0; i <1 ; i++) {
                System.out.print(Thread.currentThread().getName());
            }
            number=1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}