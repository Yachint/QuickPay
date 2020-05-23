package com.quickpay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class UserOrdersDbUtil {
	
	private DataSource dataSource;
	
	public UserOrdersDbUtil(DataSource s) {
		this.dataSource = s;
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
	
	public List<UserOrders> getAllOrders(String userId) throws Exception {
		
		List<UserOrders> orders = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRes = null;
		
		try {
			
			myConn = dataSource.getConnection();
			
			String sql = "select * from UserOrders where userId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, Integer.parseInt(userId));
			
			myRes = myStmt.executeQuery();
			
			int i = 0;
			
			while(myRes.next()) {
				String uid = String.valueOf(myRes.getInt("userId"));
				String type = myRes.getString("type");
				String info = myRes.getString("info");
				String extra = myRes.getString("extra");
				String amount = myRes.getString("amount");
				
				UserOrders u = new UserOrders(uid, type, info, extra, amount);
				
				orders.add(u);
				i++;
			}
			
			if(i==0) {
				return null;
			} else {
				return orders;
			}
			
			
		} finally {
			close(myConn,myStmt,myRes);
		}
		
	}
	
	public void createOrder(String uid, String type, String info, String extra, String amount) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = dataSource.getConnection();
			
			String sql = "INSERT INTO UserOrders (userId, type, info, extra, amount) VALUES (?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, Integer.parseInt(uid));
			myStmt.setString(2, type);
			myStmt.setString(3, info);
			myStmt.setString(4, extra);
			myStmt.setInt(5, Integer.parseInt(amount));
			
			myStmt.execute();
			
			
		} finally {
			close(myConn,myStmt,null);
		}
	}
}
