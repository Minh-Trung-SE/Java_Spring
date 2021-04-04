package com.activity1;

public class Main {
    public static void main(String[] args) {
        for(int i = 1; i <= 50; i++){
            ThreadExtends thread = new ThreadExtends();
            thread.setName("Thread " + i);
            thread.start();
        }
    }
}

class ThreadExtends extends Thread{
    public void run(){
        System.out.println(this.getName() + ": running.");
    }
}
