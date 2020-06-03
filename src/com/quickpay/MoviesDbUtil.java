package com.quickpay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import java.util.*;

public class MoviesDbUtil {
	
	private DataSource dataSource;

	public MoviesDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	synchronized public int bookTicket(int Id, int tickets) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		PreparedStatement myStmt1 = null;
		ResultSet myRes = null;
		int numTickets = 0;
		
		try {
			
			myConn = dataSource.getConnection();
			
			String sql1 = "Select movieTickets from Movies where movieId = ?";
			
			myStmt1 = myConn.prepareStatement(sql1);
			myStmt1.setInt(1, Id);
			
			myRes = myStmt1.executeQuery();
			
			if(myRes.next()) {
				numTickets = myRes.getInt("movieTickets");
			}
			
			if(numTickets < tickets) {
				return -1;
			}
			
			String sql = "UPDATE Movies set movieTickets = movieTickets - ? where movieId = ?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, tickets);
			myStmt.setInt(2, Id);
			
			myStmt.execute();
			
			return 1;
			
		} finally {
			close(myConn, myStmt, myRes);
		}
		
	}
	
	public Movies getMovie(String movieId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRes = null;
		Movies m = null;
		
		try {
			
			int id = Integer.parseInt(movieId);
			
			myConn = dataSource.getConnection();
			
			String sql = "Select * from Movies where movieId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, id);
			
			myRes = myStmt.executeQuery();
			
			if(myRes.next()) {
				
				int Id = myRes.getInt("movieId");
				String Name = myRes.getString("movieName");
				String Description = myRes.getString("movieDescription");
				String Date = myRes.getString("movieDate");
				String Image = myRes.getString("movieImage");
				int Tickets = myRes.getInt("movieTickets");
				int Cost = myRes.getInt("movieCost");
				
				m = new Movies(Id, Name, Description, Date, Image, Tickets, Cost);
			}
			
			return m;
			
		} finally {
			close(myConn, myStmt, myRes);
		}
		
		
		
	}
	
	public ArrayList<Movies> getMovies() throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRes = null;
		ArrayList<Movies> arr = new ArrayList<Movies>();
		
		try {
			
			myConn = dataSource.getConnection();
			
			String sql = "Select * from Movies";
			
			myStmt = myConn.prepareStatement(sql);
			
			myRes = myStmt.executeQuery();
			
			while(myRes.next()) {
				
				int Id = myRes.getInt("movieId");
				String Name = myRes.getString("movieName");
				String Description = myRes.getString("movieDescription");
				String Date = myRes.getString("movieDate");
				String Image = myRes.getString("movieImage");
				int Tickets = myRes.getInt("movieTickets");
				int Cost = myRes.getInt("movieCost");
				
				Movies mov = new Movies(Id, Name, Description, Date, Image, Tickets, Cost);
				arr.add(mov);
			}
			
			return arr;
			
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
