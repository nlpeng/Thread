package com.nlpeng.think;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class Test1 {
    public static void main(String[] args) {
        PrintNumber p = new PrintNumber();
        for (int i = 0; i < 5 ; i++) {
            new Thread(p,"线程"+i).start();
        }
    }
}

class PrintNumber implements Runnable{

    private int num = 1;

    private int flag = 1;

    @Override
    public void run() {
        while (num<=75){
            synchronized (this){
                String name = Thread.currentThread().getName();
                if(num<=75&&name.endsWith(flag+"")){
                    for (int i = 0; i < 5 ; i++) {
                        System.out.println(name+":"+num);
                        num++;
                    }
                    if (flag<4){
                        flag++;
                    }else {
                        flag=1;
                    }

                    this.notifyAll();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    this.notifyAll();
                    if(num == 75){
                        break;
                    }
                }
            }
        }
    }
}