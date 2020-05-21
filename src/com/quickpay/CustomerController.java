package com.quickpay;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CustomerDbUtil customerDbUtil;
	
	@Resource(name="jdbc/QuickPay")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			customerDbUtil = new CustomerDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			String theCheck = (String)request.getAttribute("command");
			
			if (theCommand == null) {
				theCommand = theCheck;
			} 
			
			switch (theCommand) {
			
			case "LIST":
				listCustomers(request, response);
				break;
				
			case "LOAD":
				loadCustomer(request, response);
				break;
			
			case "DELETE":
				deleteCustomer(request, response);
				break;
				
			case "LOGOUT":
				handleLogout(request, response);
				break;
				
			default:
				defaultPage(request, response);
				break;
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	public void defaultPage(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		request.getRequestDispatcher("Home.jsp").forward(request, response);
	}
	
	public void listCustomers(HttpServletRequest request, HttpServletResponse response)throws Exception {
		List<Customer> customers = customerDbUtil.getCustomers();
		
		request.setAttribute("CUST_LIST", customers);
		
		request.getRequestDispatcher("TransferMoney").forward(request, response);
	}
	public void addCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = request.getParameter("fname");
		String email = request.getParameter("email");
		String uname = request.getParameter("uname");
		String password = request.getParameter("pass");
		
		Customer newCustomer = new Customer(name,email,uname,password);
		
		customerDbUtil.addCustomer(newCustomer);
		
		request.setAttribute("operation", "Register");
		request.setAttribute("message", "Registration successful, login and start using QuickPay");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
		
		
	}
	public void loadCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String theCustomerId = request.getParameter("CustomerId");
		
		Customer theCustomer = customerDbUtil.getCustomer(theCustomerId);
		
		request.setAttribute("THE_Customer", theCustomer);
		
		request.getRequestDispatcher("EditDetailsForm.jsp").forward(request, response);
	}
	public void updateCustomer(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String name = request.getParameter("fname");
		String email = request.getParameter("email");
		String uname = request.getParameter("uname");
		String password = request.getParameter("pass");
		HttpSession session = request.getSession();
		
		Customer newCustomer = new Customer((Integer)session.getAttribute("userid"),name,email,uname,password);
		customerDbUtil.updateCustomer(newCustomer);
		
		request.setAttribute("operation", "Update");
		request.setAttribute("message", "Update successful, Click the button below to continue using QuickPay");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
		
	}
	public void deleteCustomer(HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		customerDbUtil.deleteCustomer((String)session.getAttribute("userid"));
		request.getRequestDispatcher("Success.jsp").forward(request, response);
	}
	
	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uname = request.getParameter("uname");
		String password = request.getParameter("pass");
		
		int userid = customerDbUtil.checkPassword(uname, password);
		if(userid != -1) {
			HttpSession session = request.getSession();
			session.setAttribute("userid", String.valueOf(userid));
			session.setAttribute("uname", String.valueOf(uname));
			request.getRequestDispatcher("AfterLogin.jsp").forward(request, response);
		} else {
			request.setAttribute("operation", "Login");
			request.setAttribute("message", "Incorrect Credentials...");
			request.getRequestDispatcher("Incorrect.jsp").forward(request, response);
		}
	}
	
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("userid");
		session.setMaxInactiveInterval(0);
		request.setAttribute("operation", "Logout");
		request.setAttribute("message", "Logout successful, thanks for using QuickPay!");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			
			if (theCommand == null) {
				theCommand = "HOME";
			}
			
			switch (theCommand) {
			
				
			case "ADD":
				addCustomer(request, response);
				break;
				
			case "UPDATE":
				updateCustomer(request, response);
				break;
				
			case "LOGIN":
				handleLogin(request, response);
				break;
			
			case "HOME":
				defaultPage(request, response);
				break;
				
			default:
				defaultPage(request, response);
				break;
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

}
