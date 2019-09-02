package com.nlpeng.think;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class TestBank {
    public static void main(String[] args) {
        User u = new User();
        new Thread(u).start();
        new Thread(u).start();

    }
}

class Account{
    private int sum =1000;
    synchronized public void withdraw(int money){
        if(money>sum){
            System.out.println("余额不足；当前余额为"+sum);
        }else {
            //synchronized (this){
                System.out.println(Thread.currentThread().getName()+"之前余额"+sum);
                sum=sum-money;
                System.out.println(Thread.currentThread().getName()+"本次取出"+money+":余额"+sum);
            //}
        }

    }
}

class User implements Runnable{
    private Account account = new Account();
    @Override
    public void run() {
        for (int i = 0; i < 6 ; i++) {

            account.withdraw(99);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}