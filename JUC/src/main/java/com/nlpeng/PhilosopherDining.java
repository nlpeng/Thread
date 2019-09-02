package com.nlpeng;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class PhilosopherDining {

    public static void main(String[] args) {
        int factor = 3;
        int num = 5;
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[num];
        for(int i = 0; i < num; ++i) {
            chopsticks[i] = new Chopstick();
        }
        for(int i = 0; i < num; ++i) {
            Philosopher philosopher = null;
            if(i < num - 1) {
                philosopher = new Philosopher(chopsticks[i], chopsticks[i + 1], i, factor);
            } else {
                philosopher = new Philosopher(chopsticks[0], chopsticks[i], i, factor);
            }
            exec.execute(philosopher);
        }
        exec.shutdown();
    }

}

class Chopstick {
    private boolean taken;
    public Chopstick() {
        taken = false;
    }
    public void take() {
        synchronized (this) {
            while(taken) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            taken = true;
        }
    }
    public void drop() {
        synchronized (this) {
            taken = false;
            this.notifyAll();
        }
    }
}

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private int id;
    private int factor;
    private Random random;
    private int count;

    public Philosopher(Chopstick left, Chopstick right, int id, int factor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.factor = factor;
        count = factor;
        random = new Random();
    }

    public void eat() {
        left.take();
        right.take();
        System.out.println("Philosopher " + id + " is eating.");
        try {
            TimeUnit.MICROSECONDS.sleep(random.nextInt(factor) * 100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void think() {
        left.drop();
        right.drop();
        System.out.println("Philosopher " + id + " is thinking.");
        try {
            TimeUnit.MICROSECONDS.sleep(random.nextInt(factor) * 100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        while (count-- > 0) {
            eat();
            think();
        }
    }
}