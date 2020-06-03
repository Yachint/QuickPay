package com.quickpay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.util.List;

public class CustomerDbUtil {
	
	private DataSource dataSource;
	
	public CustomerDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Customer> getCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "select * from Customers";
			
			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
			
				int id = myRs.getInt("UserId");
				String name = myRs.getString("Name");
				String email = myRs.getString("Email");
				String uname = myRs.getString("Uname");
				
				Customer tempCust = new Customer(id, name, email, uname);
				
				customers.add(tempCust);				
			}
			
			return customers;		
		}
		finally {

			close(myConn, myStmt, myRs);
		}	
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void addCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = dataSource.getConnection();
			

			String sql = "insert into Customers "
					   + "(Name, Email, Uname, Password) "
					   + "values (?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			

			myStmt.setString(1, theCustomer.getName());
			myStmt.setString(2, theCustomer.getEmail());
			myStmt.setString(3, theCustomer.getUname());
			myStmt.setString(4, theCustomer.getPassword());
			

			myStmt.execute();
		}
		finally {
		
			close(myConn, myStmt, null);
		}
	}
	
	public Customer getCustomer(String theCustomerId) throws Exception {

		Customer theCustomer = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int CustomerId;
		
		try {
			CustomerId = Integer.parseInt(theCustomerId);
			
			myConn = dataSource.getConnection();
			
			String sql = "select * from Customers where UserId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, CustomerId);
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				String name = myRs.getString("Name");
				String email = myRs.getString("Email");
				String uname = myRs.getString("Uname");
				
				
				theCustomer = new Customer(CustomerId, name, email, uname);
			}
			else {
				throw new Exception("Could not find Customer id: " + CustomerId);
			}				
			
			return theCustomer;
		}
		finally {
			
			close(myConn, myStmt, myRs);
		}
	}
	
	
	public Customer getEditCustomer(String theCustomerId) throws Exception {

		Customer theCustomer = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int CustomerId;
		
		try {
			CustomerId = Integer.parseInt(theCustomerId);
			
			myConn = dataSource.getConnection();
			
			String sql = "select * from Customers where UserId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, CustomerId);
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				String name = myRs.getString("Name");
				String email = myRs.getString("Email");
				String uname = myRs.getString("Uname");
				String pass = myRs.getString("Password");
				
				
				theCustomer = new Customer(name, email, uname, pass);
			}
			else {
				throw new Exception("Could not find Customer id: " + CustomerId);
			}				
			
			return theCustomer;
		}
		finally {
			
			close(myConn, myStmt, myRs);
		}
	}
	
	
	public String getCustomerId(String uname) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String userId = null;
		
		try {			
			myConn = dataSource.getConnection();
			
			String sql = "select UserId from Customers where Uname=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, uname);
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				userId = String.valueOf(myRs.getInt("UserId"));
			}
			else {
				throw new Exception("Could not find Customer with uname: " + uname);
			}				
			
			return userId;
		}
		finally {
			
			close(myConn, myStmt, myRs);
		}
	}
	
public String getCustomerName(String userId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String name = null;
		
		try {			
			myConn = dataSource.getConnection();
			
			String sql = "select Name from Customers where UserId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, Integer.parseInt(userId));
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				name = String.valueOf(myRs.getString("Name"));
			}
			else {
				throw new Exception("Could not find Customer with uid: " + userId);
			}				
			
			return name;
		}
		finally {
			
			close(myConn, myStmt, myRs);
		}
	}
	
	public void updateCustomer(Customer theCustomer, String userId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			
			myConn = dataSource.getConnection();
			
			
			String sql = "update Customers "
						+ "set Name=?, Email=?, Password=? "
						+ "where UserId=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			

			myStmt.setString(1, theCustomer.getName());
			myStmt.setString(2, theCustomer.getEmail());
			myStmt.setString(3, theCustomer.getPassword());
			myStmt.setInt(4, Integer.parseInt(userId));
			

			myStmt.execute();
		}
		finally {

			close(myConn, myStmt, null);
		}
	}
	
	public void deleteCustomer(String theCustomerId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {

			int CustomerId = Integer.parseInt(theCustomerId);

			myConn = dataSource.getConnection();
			
			String sql = "delete from Customers where UserId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, CustomerId);
			
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}	
	}
	
	public int checkPassword(String uname, String password) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int userid = -1;
		
		try {
			myConn = dataSource.getConnection();
			
			String sql = "select * from Customers where Uname=? and Password=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, uname);
			myStmt.setString(2, password);
			
			myRs =  myStmt.executeQuery();
			
			if(myRs.next()) {
				userid = myRs.getInt("UserId");

			}
			
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		
		return userid;
	}

}
