package com.quickpay;

public class UserOrders {
	private String userId;
	private String type;
	private String info;
	private String extra;
	private String amount;
	
	public UserOrders(String userId, String type, String info, String extra, String amount) {
		this.userId = userId;
		this.type = type;
		this.info = info;
		this.extra = extra;
		this.amount = amount;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
