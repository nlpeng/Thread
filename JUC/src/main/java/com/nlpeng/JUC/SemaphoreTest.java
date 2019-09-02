package com.nlpeng.JUC;

import java.util.concurrent.Semaphore;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class SemaphoreTest {

    Semaphore mSemaphore = new Semaphore(2);
    public void method(){
        try {
            mSemaphore.acquire();//这个线程已经获得了方法的使用权，
            // 如果获得使用权的线程数等于指定的数，那么其他线程将不能再获得方法的使用权。

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            mSemaphore.release();//用于线程释放使用权
        }
    }


}
