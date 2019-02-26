package com.example.bank.solution;

public class BankActions {

	public static void transferMoney(BankAccount from, BankAccount to, int amount) throws Exception {
		String threadName = Thread.currentThread().getName();
		System.out.println("On " + threadName);

		lockAccountsByOrder(from, to);
		int fromBalance = from.getBalance();
		if (amount > fromBalance) {
			unlockAccountsByOrder(from, to);
			throw new Exception("Balance can not be negative!!!");
		}
		from.setBalance(fromBalance - amount);
		int toBalance = to.getBalance();
		to.setBalance(toBalance + amount);
		unlockAccountsByOrder(from, to);
	}

	private static void lockAccountsByOrder(BankAccount a, BankAccount b) {
		if(a.getId() > b.getId()){
			b.lock();
			a.lock();
		} else {
			a.lock();
			b.lock();
		}
	}

	private static void unlockAccountsByOrder(BankAccount a, BankAccount b) {
		if(a.getId() > b.getId()){
			a.unlock();
			b.unlock();
		} else {
			b.unlock();
			a.unlock();
		}
	}

}
