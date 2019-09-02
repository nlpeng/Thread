package com.nlpeng;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class ProducerConsumer {
    private final int itemsNo;
    private Stack<Integer> stack;
    private final int STACK_SIZE;


    public ProducerConsumer(int itemsNo, int stack_size) {
        this.itemsNo = itemsNo;
        this.stack = new Stack<Integer>();
        STACK_SIZE = stack_size;
    }

    private class Producer implements Runnable{
        private int count;
        public Producer(){
            this.count = 0;
        }
        @Override
        public void run() {
            while (true){
                synchronized (stack){
                    while (stack.size()>=STACK_SIZE){
                        try {
                            stack.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (count<itemsNo){
                        produce();
                        try {
                            TimeUnit.MICROSECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        stack.notifyAll();
                    }else {
                        break;
                    }
                }
            }
        }
        private void produce(){
            stack.push(++count);

            System.out.println(Thread.currentThread().getName() + " is producing item " + count);

        }
    }

    private class Consumer implements Runnable{

        private  int count;

        public Consumer(){
            this.count =0;
        }
        @Override
        public void run() {
            while (true){
                synchronized (stack){
                    while (stack.empty()&&!isFinished()){
                        try {
                            stack.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consume();
                    try {
                        TimeUnit.MICROSECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stack.notifyAll();
                    if(isFinished()){
                        break;
                    }
                }
            }
        }
        public boolean isFinished(){
            return count == itemsNo;
        }
        private void consume(){
            if(isFinished()){
                return;
            }
            System.out.println(Thread.currentThread().getName() +" is consuming item " + stack.pop());
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ProducerConsumer pc = new ProducerConsumer(10,3);
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();
        int producerNo = 3;
        int consumerNo = 2;
        for (int i = 0; i < producerNo; i++) {
            exec.execute(producer);
        }
        for (int i = 0; i < consumerNo; i++) {
            exec.execute(consumer);
        }
        exec.shutdown();
    }
}
