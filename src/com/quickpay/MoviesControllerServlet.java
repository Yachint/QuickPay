package com.quickpay;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/MoviesControllerServlet")
public class MoviesControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MoviesDbUtil moviesDbUtil;
	
	@Resource(name="jdbc/QuickPay")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			moviesDbUtil = new MoviesDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			String theCheck = (String)request.getAttribute("command");
			System.out.println("------------------>"+theCheck);
			
			if (theCommand == null || theCheck != null) {
				theCommand = theCheck;
			} 
			System.out.println("Movies handler------------------>"+theCommand);
			switch (theCommand) {
			
			case "GET_MOVIES":
				getMovies(request, response);
				break;
			
			case "BOOK":
				bookMovie(request, response);
				break;
				
			default:
				getMovies(request, response);
				break;
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	public void getMovies(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		ArrayList<Movies> mov = moviesDbUtil.getMovies();
		
		request.setAttribute("MovieList", mov);
		request.getRequestDispatcher("MovieList.jsp").forward(request, response);
		
	}
	
	public void bookMovie(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		int id = Integer.parseInt(request.getParameter("MovieId"));
		int tickets = Integer.parseInt(request.getParameter("tickets"));
		
		int res = moviesDbUtil.bookTicket(id, tickets);
		
		if(res == -1) {
			request.setAttribute("operation", "Book Tickets");
			request.setAttribute("message", "Tickets sold out or you have entered an invalid amount!");
			request.getRequestDispatcher("Incorrect.jsp").forward(request, response);
			
		} else {
			Movies movie = moviesDbUtil.getMovie(request.getParameter("MovieId"));
			
			request.setAttribute("command", "BOOK");
			request.setAttribute("tickets", request.getParameter("tickets"));
			request.setAttribute("movie", movie);
			request.getRequestDispatcher("WalletController").forward(request, response);
			
		}
		

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
