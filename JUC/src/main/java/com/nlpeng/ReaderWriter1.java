package com.nlpeng;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class ReaderWriter1 {

    private int readerNum;
    private Semaphore wmutex;//优先写
    private Semaphore rwmutex;//读者与写者之间互斥
    public ReaderWriter1(int readerNum) {
        this.readerNum = readerNum;
        wmutex = new Semaphore(1);
        rwmutex = new Semaphore(readerNum);
    }
    private class Reader implements Runnable {
        public void read() {
            try {
                wmutex.acquire();
                rwmutex.acquire();
                wmutex.release();
                System.out.println(Thread.currentThread().getName() + " is reading.");
                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(10) * 10);
                rwmutex.release();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void run() {
            read();
        }
    }
    private class Writer implements Runnable {
        public void write() {
            try {
                wmutex.acquire();
                rwmutex.acquire(readerNum);
                System.out.println(Thread.currentThread().getName() + " is writing.");
                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(100) * 1000);
                rwmutex.release(readerNum);
                wmutex.release();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void run() {
            write();
        }
    }
    public static void main(String[] args) {
        int readerNum = 20;
        int writerNum = 2;
        ReaderWriter1 rw = new ReaderWriter1(readerNum);
        Reader reader = rw.new Reader();
        Writer writer = rw.new Writer();
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < readerNum; ++i) {
            exec.execute(reader);
        }
        for(int i = 0; i < writerNum; ++i) {
            exec.execute(writer);
        }
        exec.shutdown();
    }

}
