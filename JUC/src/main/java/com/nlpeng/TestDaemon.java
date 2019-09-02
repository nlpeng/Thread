package com.nlpeng;

/**
 * @author Ferry NLP
 * @create 2019-08-14
 * @see
 * @since 1.0v
 **/
public class TestDaemon {


    public static void main(String[] args) {
        DaemonDemo d1 = new DaemonDemo();
        DaemonDemo d2 = new DaemonDemo();
        d1.setDaemon(true);
        d2.setDaemon(true);
        d1.start();
        d2.start();
        for (int i = 0; i < 100 ; i++) {
            System.out.println("main"+i);
        }
        System.out.println("over");
    }
}

class DaemonDemo extends Thread{
    public void run(){
        while (true){
            System.out.println(Thread.currentThread().getName());
        }
    }
}