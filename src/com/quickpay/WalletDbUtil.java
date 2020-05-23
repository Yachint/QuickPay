package com.quickpay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

public class WalletDbUtil {
	
	private DataSource dataSource;

	public WalletDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void createWallet(String userId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = dataSource.getConnection();
			

			String sql = "insert into Wallet "
					   + "(userId, balance) "
					   + "values (?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			

			myStmt.setInt(1, Integer.parseInt(userId));
			myStmt.setInt(2, 0);
			

			myStmt.execute();
		}
		finally {
		
			close(myConn, myStmt, null);
		}
	}
	
	public void updateWallet(String userId, int newBalance) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			int custId = Integer.parseInt(userId);
			
			myConn = dataSource.getConnection();
			
			String sql = "UPDATE Wallet SET balance=? where userId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, newBalance);
			myStmt.setInt(2, custId);
			
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}
	
	
public Wallet getWallet(String userId) throws Exception{
		
		Wallet theWallet = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRes = null;
		
		try {
			int custId = Integer.parseInt(userId);
			
			myConn = dataSource.getConnection();
			
			String sql = "Select * from Wallet where userId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, custId);
			
			myRes = myStmt.executeQuery();
			
			if(myRes.next()) {
				String uid = String.valueOf(myRes.getInt("userId"));
				String wallet = String.valueOf(myRes.getInt("balance"));
				theWallet = new Wallet(uid, wallet);
			}
			
			return theWallet;
		} finally {
			close(myConn, myStmt, myRes);
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
	
}
