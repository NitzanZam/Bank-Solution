package com.example.bank.solution;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    ReentrantLock lock = new ReentrantLock();
    private int balance;
    private int id;

    public BankAccount(int balance, int id){
    	this.balance = balance;
    	this.id = id;
	}

    public int getBalance(){
        try {
            Thread.sleep(100 * new Random().nextInt(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }

    public void setBalance(int amount) {
        try {
            Thread.sleep(500 * new Random().nextInt(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        balance = amount;
    }

    public void lock() {
        try {
			lock.lock();
			Thread.sleep(500 * new Random().nextInt(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void unlock() {
        lock.unlock();
        try {
            Thread.sleep(500 * new Random().nextInt(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

	public int getId() {
		return id;
	}
}
