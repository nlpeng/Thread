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
public class ThreadTest2 {

    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadExecute(business,"sub");
            }
        }).start();

        threadExecute(business, "main");

    }
    public static void threadExecute(Business business, String threadType){
        for (int i = 0; i < 100; i++) {
            try {
                if ("main".equals(threadType)){
                    business.main(i);
                }else {
                    business.sub(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Business{
    private boolean bool = true;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void main(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (bool){
                condition.await();
            }
            for (int i = 0; i < 100; i++) {
                System.out.println("main thread seq of " + i + ", loop of " + loop);
            }
            bool = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void sub(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (bool){
                condition.await();
            }
            for (int i = 0; i < 10 ; i++) {
                System.out.println("sub thread seq of " + i + ", loop of " + loop);
            }
            bool = true;
            condition.signal();
        } finally {
            lock.unlock();
        }

    }
}

class BoundedBuffer{
    final Lock lock  = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr,takeptr,count;
    public void put(Object x) throws InterruptedException{
        lock.lock();
        try {
            while (count== items.length){
                notFull.await();
                items[putptr] =x ;
                if(++putptr ==items.length){
                    putptr=0;
                }
                ++count;
                notEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }
    public Object take()throws InterruptedException{
        lock.lock();
        try {
            while (count==0){
                notEmpty.await();
                Object x = items[takeptr];
                if(++takeptr == items.length) takeptr = 0;
                --count;
                notFull.signal();
                return x;
            }
        } finally {
            lock.unlock();
        }
        return null;

    }
}