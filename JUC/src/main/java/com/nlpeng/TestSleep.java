package com.nlpeng;

/**
 * @author Ferry NLP
 * @create 2019-08-14
 * @see
 * @since 1.0v
 **/
public class TestSleep {
    public static void main(String[] args) {
        SleepDemo s = new SleepDemo();
        s.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
            if(i%3 == 0 ){
                s.interrupt();//打断休眠
            }

        }
    }
}


class SleepDemo extends Thread{
    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(this.getName()+":"+i);
            if(i%2==0){
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}