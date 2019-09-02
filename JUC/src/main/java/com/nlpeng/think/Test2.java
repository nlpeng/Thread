package com.nlpeng.think;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class Test2 {
    public static void main(String[] args) {
        Houseware houseware = new Houseware();
        new Worker(houseware).start();
        new Customer(houseware).start();
    }
}

class Worker extends Thread{
    private Houseware h ;

    public Worker (Houseware h){
        this.h = h;
    }
    public void run (){
        while (true){
            h.add();
        }
    }
}

class Customer extends Thread{
    private Houseware h ;

    public Customer (Houseware h){
        this.h = h;
    }
    public void run (){
        while (true){
            h.sale();
        }
    }
}

class Houseware{
    private int count = 0;
    public synchronized void add(){
        if(count==10){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName()+"生产了一台电视机，库存："+count);

        this.notify();
    }

    public synchronized void sale(){
        if(count==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName()+"消费了一台电视机，库存："+count);
        this.notify();
    }


}