package com.quickpay;

public class Wallet {
	private String userId;
	private String balance;
	
	public Wallet(String userId, String balance) {
		super();
		this.userId = userId;
		this.balance = balance;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
}
