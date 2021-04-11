package com.activity5;

import java.util.Queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        findDivisor findDivisor = new findDivisor();
        findDivisor.createThread();

    }
}

class findDivisor {
    public static int biggestDivisor = 0;
    public static int count = 0;
    public static int startNumber = 0;
    public static int endNumber = 100000;


    public void createThread(){
        int endStepNumber = startNumber;
        int step = (endNumber - startNumber) / 4;
        int i = 1;
        while (i <= 4) {
            thread thread = new thread(startNumber, endStepNumber += step, "thread" + i);
            thread.start();
            i++;
            startNumber = endStepNumber + 1;
        }
    }
}

class thread extends Thread{
    int startNumber;
    int endNumber;
    String nameThread;

    public thread(int startNumber, int endNumber, String nameThread) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.nameThread = nameThread;
        this.setName(nameThread);
    }

    @Override
    public void run() {
        int number = startNumber, bigCount = 0, count = 0;
        System.out.println(this.getName() + " " + System.currentTimeMillis() / 1000 + " running!");
        for(int i = startNumber; i <= endNumber; i++){
            count = 0;
            for(int j = 1; j <= i; j++){
                if(i % j == 0){
                    count ++;
                }
            }
            if(count >= bigCount){
                number = i;
                bigCount = count;
            }
        }

        synchronized (this){
            if(bigCount >= findDivisor.count){
                findDivisor.biggestDivisor = number;
                findDivisor.count = bigCount;
            }

            System.out.println(number + " big number owned " + bigCount + " divisor");
            System.out.println(this.getName() + " " + System.currentTimeMillis() / 1000 + " finish! \n");
        }
    }
}
