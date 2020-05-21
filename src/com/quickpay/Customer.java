package com.quickpay;

public class Customer {
	
	private int UserId;
	private String Name;
	private String Email;
	private String Uname;
	private String Password;
	
	public Customer(String name, String email, String uname, String password) {
		UserId = 0;
		Name = name;
		Email = email;
		Uname = uname;
		Password = password;
	}
	
	public Customer(int userId, String name, String email, String uname, String password) {
		UserId = userId;
		Name = name;
		Email = email;
		Uname = uname;
		Password = password;
	}
	
	public Customer(int userId, String name, String email, String uname) {
		UserId = userId;
		Name = name;
		Email = email;
		Uname = uname;
		Password = "";
	}
	
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getUname() {
		return Uname;
	}
	public void setUname(String uname) {
		Uname = uname;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "Customer [UserId=" + UserId + ", Name=" + Name + ", Email=" + Email + ", Uname=" + Uname + ", Password="
				+ Password + "]";
	}
	
	
}
