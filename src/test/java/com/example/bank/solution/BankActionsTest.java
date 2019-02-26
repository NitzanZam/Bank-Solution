package com.example.bank.solution;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankActionsTest {

	private SoftAssertions softAssertions = new SoftAssertions();


	@Test
	public void transferMoney_singleThread_goodPath() throws Exception {
		BankAccount from = new BankAccount(100, 1);
		BankAccount to = new BankAccount(0, 2);

		BankActions.transferMoney(from, to, 70);

		softAssertions.assertThat(from.getBalance()).isEqualTo(30);
		softAssertions.assertThat(to.getBalance()).isEqualTo(70);
		softAssertions.assertAll();
	}

	@Test()
	public void transferMoney_singleThread_badPath() throws Exception {
		BankAccount from = new BankAccount(100, 1);
		BankAccount to = new BankAccount(0, 2);

		try {
			BankActions.transferMoney(from, to, 110);
		} catch (Exception e) {
			assertEquals("Balance can not be negative!!!", e.getMessage());
		}
	}

	@Test
	public void transferMoney_MultiThread_doubleTarnsfer() throws Exception {
		BankAccount matarBankAccount = new BankAccount(200, 1);
		BankAccount nogaBankAccount = new BankAccount(0, 2);

		TransferMoneyTask transferFromMatarToNogaTask1 = new TransferMoneyTask(matarBankAccount, nogaBankAccount, 100);
		TransferMoneyTask transferFromMatarToNogaTask2 = new TransferMoneyTask(matarBankAccount, nogaBankAccount, 100);

		Thread thread1 = new Thread(transferFromMatarToNogaTask1);
		thread1.start();

		Thread thread2 = new Thread(transferFromMatarToNogaTask2);
		thread2.start();

		thread1.join();
		thread2.join();

		softAssertions.assertThat(matarBankAccount.getBalance()).isEqualTo(0);
		softAssertions.assertThat(nogaBankAccount.getBalance()).isEqualTo(200);
		softAssertions.assertAll();
	}


	@Test
	public void transferMoney_MultiThread_cross_transfer() throws InterruptedException {
		BankAccount matarBankAccount = new BankAccount(200, 1);
		BankAccount nogaBankAccount = new BankAccount(100, 2);

		TransferMoneyTask transferFromMatarToNogaTask = new TransferMoneyTask(matarBankAccount, nogaBankAccount, 100);
		TransferMoneyTask transferFromNogaToMatarTask = new TransferMoneyTask(nogaBankAccount, matarBankAccount, 50);

		Thread thread1 = new Thread(transferFromMatarToNogaTask);
		thread1.start();

		Thread thread2 = new Thread(transferFromNogaToMatarTask);
		thread2.start();

		thread1.join(60000);
		thread2.join(60000);

		softAssertions.assertThat(matarBankAccount.getBalance()).isEqualTo(150);
		softAssertions.assertThat(nogaBankAccount.getBalance()).isEqualTo(150);
		softAssertions.assertAll();
	}
}