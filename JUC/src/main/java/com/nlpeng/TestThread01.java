package com.nlpeng;

/**
 * @author Ferry NLP
 * @create 2019-08-14
 * @see
 * @since 1.0v
 **/
public class TestThread01 {


    public static void main(String[] args) {
        MyThread01 m1 = new MyThread01("线程1");

        MyThread01 m2 = new MyThread01("线程2");

        m1.setPriority(Thread.MIN_PRIORITY);
        m2.setPriority(Thread.MAX_PRIORITY);

        System.out.println(Thread.currentThread().getName()+"///"+Thread.currentThread().getPriority());
        System.out.println(m1.getName()+"///"+m1.getPriority());
        System.out.println(m2.getName()+"///"+m2.getPriority());


        m1.start();
        m2.start();
        for (int i = 0; i < 100 ; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }



    }


}

class MyThread01 extends Thread{
    public MyThread01(){}

    public MyThread01(String name){
        super(name);
    }

    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(this.getName()+":"+i);
        }
    }
}