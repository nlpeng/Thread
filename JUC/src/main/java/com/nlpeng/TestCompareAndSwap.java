package com.nlpeng;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class TestCompareAndSwap {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue,(int)(Math.random()*101));
                    System.out.println(b);
                }
            }).start();
        }
    }
}

class  CompareAndSwap{
    private int value;
    public synchronized int get(){
        return value;
    }
    public synchronized int compareAndSwap(int expectedValue,int newValue){
        int oldValue = value;
        if(oldValue == expectedValue){
            this.value = newValue;
        }
        return oldValue;
    }
    public synchronized boolean compareAndSet(int expectedValue,int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}