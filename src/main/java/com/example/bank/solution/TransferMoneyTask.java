package com.example.bank.solution;

public class TransferMoneyTask implements Runnable {

	private BankAccount from;
	private BankAccount to;
	private int amount;

	public TransferMoneyTask(BankAccount from, BankAccount to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	@Override
	public void run() {
		try {
			BankActions.transferMoney(from, to, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
