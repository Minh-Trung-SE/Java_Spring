package com.activity2;

public class Main {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("ABC", 3500);
        MyThread thread2 = new MyThread("DEF", 2500);
        thread1.start();
        thread2.start();
    }
}

class MyThread extends Thread{
    private String name;
    private int sleep;
    public MyThread (String name, int sleep){
        this.name = name;
        this.sleep = sleep;
        this.setName(name);
    }
    @Override
    public void run() {
        while (true){
            System.out.println(this.getName() + " " + System.currentTimeMillis() / 1000);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
