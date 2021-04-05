package com.activity4;

public class Main {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        WithdrawThread husbandThread = new WithdrawThread("Husband", 12000000);
        WithdrawThread wifeThread = new WithdrawThread("Wife", 10000000);
        wifeThread.start();
        husbandThread.start();
    }
}

class BankAccount{
    protected static long amount = 20000000;
    public synchronized void withdraw(String threadName, long withdrawAmount){
        System.out.println(threadName + " need: " + withdrawAmount);
        if(withdrawAmount <= BankAccount.amount){
            BankAccount.amount = BankAccount.amount - withdrawAmount;
            System.out.println(threadName + " withdraw " + withdrawAmount + " success!");
        }else{
            System.out.println(threadName + " withdraw " + withdrawAmount + " failed!");
        }
        System.out.println(threadName + " see balance: " + BankAccount.amount);
    }

    public synchronized void addMoney(String nameThread, long addAmount){
        BankAccount.amount = addAmount + BankAccount.amount;
        System.out.println(nameThread + " add " + addAmount + " success!");
    }
}

class WithdrawThread extends Thread{
    private String threadName;
    private long withdrawAmount;

    public WithdrawThread(String threadName, long withdrawAmount) {
        this.threadName = threadName;
        this.withdrawAmount = withdrawAmount;
    }

    @Override
    public void run() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.withdraw(threadName, withdrawAmount);
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bankAccount.addMoney(threadName, withdrawAmount);
        System.out.println("Balance: " + BankAccount.amount);
    }
}