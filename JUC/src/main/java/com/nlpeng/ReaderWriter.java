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
public class ReaderWriter {
    private int readerCount;
    private Semaphore wmutex;//优先写
    private Semaphore rwmtex;//读者与写者之间互斥
    private Semaphore readerCountMutex;//读者更新readerCount时互斥

    public ReaderWriter(){
        readerCount =0;
        wmutex = new Semaphore(1);
        rwmtex = new Semaphore(1);
        readerCountMutex = new Semaphore(1);
    }
/*
acquire()和 release() 两个方法，
第一个方法表示这个线程已经获得了方法的使用权，
如果获得使用权的线程数等于指定的数，
那么其他线程将不能再获得方法的使用权。
release()用于线程释放使用权。
 */
    private class Reader implements Runnable{

        public void read(){
            try {
                wmutex.acquire();
                readerCountMutex.acquire();
                if (readerCount ==0){
                    rwmtex.acquire();
                }
                ++readerCount;
                readerCountMutex.release();
                wmutex.release();
                System.out.println(Thread.currentThread().getName()+"is reading");
                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(10)*10);
                readerCountMutex.acquire();
                --readerCount;
                if (readerCount ==0){
                    rwmtex.release();
                }
                readerCountMutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            read();
        }
    }

    private class Writer implements Runnable{

        public void writer(){
            try {
                wmutex.acquire();
                rwmtex.acquire();
                System.out.println(Thread.currentThread().getName()+"is writing");
                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(100)*1000);
                rwmtex.release();
                wmutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            writer();
        }
    }

    public static void main(String[] args) {
        int readerNum = 5;
        int writerNum = 2;
        ReaderWriter rw = new ReaderWriter();
        Reader reader = rw.new Reader();
        Writer writer = rw.new Writer();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < writerNum; i++) {
            exec.execute(writer);
        }
        for (int i = 0; i < readerNum; i++) {
            exec.execute(reader);
        }
        exec.shutdown();
    }

}

