package com.nlpeng.think;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class Ticket implements Runnable {
    private int count = 100;

    @Override
    public void run() {
        while (count>0){
            synchronized (this){
                if (count>0){
                    count--;
                    System.out.println(Thread.currentThread().getName()+"：remainder"+count);
                    this.notifyAll();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Ticket t = new Ticket();
        Thread t1 = new Thread(t);
        t1.setName("A");
        t1.start();
        Thread t2 = new Thread(t);
        t2.setName("B");
        t2.start();
        Thread t3 = new Thread(t);
        t3.setName("C");
        t3.start();
    }
}
