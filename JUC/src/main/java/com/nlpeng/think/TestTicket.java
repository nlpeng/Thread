package com.nlpeng.think;

/**
 * @author Ferry NLP
 * @create 2019-08-15
 * @see
 * @since 1.0v
 **/
public class TestTicket {
    public static void main(String[] args) {

        TicketSaleWindow t1 = new TicketSaleWindow();
        for (int i = 0; i <4; i++) {
            new Thread(t1).start();
        }
    }

}


class TicketSaleWindow implements Runnable{
    private int tick = 1000;
    private Object lock = new Object();
    public void run(){
        while (true){
            synchronized (lock){
                if(tick>0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+":sell"+tick--+"tick");
                }else {
                    break;
                }
            }
        }
    }
}