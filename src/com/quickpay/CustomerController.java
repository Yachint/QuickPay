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
			
			if(theCommand == null && theCheck == null) {
				theCommand = "LOAD";
			}
			
			System.out.println("CustController------>"+theCommand);
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
				
			case "EDIT_EXISTING":
				editDetails(request, response);
				break;
				
			case "LOGOUT":
				handleLogout(request, response);
				break;
				
			case "RECHARGE":
				getRechargeForm(request, response);
				break;
			
			case "RECHARGE_HANDLE":
				handleRecharge(request, response);
				break;
				
			case "TRANSFER":
				getTransferForm(request, response);
				break;
				
			case "GET_MOVIES":
				getMovies(request, response);
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
	
	public void getRechargeForm(HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.sendRedirect("SelectService.jsp");
	}
	
	public void getTransferForm(HttpServletRequest request, HttpServletResponse response)throws Exception {
		request.setAttribute("formName", "Transfer Money to other User :");
		List<Customer> cust = customerDbUtil.getCustomers();
		request.setAttribute("operators", cust);
		request.setAttribute("formName", "Transfer Money to other User :");
		request.getRequestDispatcher("TransferForm.jsp").forward(request, response);
	}
	
	public void getMovies(HttpServletRequest request, HttpServletResponse response)throws Exception {
	
		request.getRequestDispatcher("MoviesControllerServlet").forward(request, response);
		
	}
	
	
	public void editDetails(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		Customer cust = customerDbUtil.getEditCustomer((String) session.getAttribute("userid"));
		request.setAttribute("cust", cust);
		request.getRequestDispatcher("EditDetails.jsp").forward(request, response);
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
		String id = customerDbUtil.getCustomerId(uname);
		System.out.println("----> cust controller :"+id);
		request.setAttribute("userId", id);
		request.setAttribute("command", "CREATE");
		request.getRequestDispatcher("WalletController").forward(request, response);
		
		
	}
	public void loadCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		String theCustomerId = (String) session.getAttribute("userid");
		
		Customer theCustomer = customerDbUtil.getCustomer(theCustomerId);
		
		request.setAttribute("THE_Customer", theCustomer);
		
		request.getRequestDispatcher("Account.jsp").forward(request, response);
	}
	
	public void handleRecharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getParameter("type").equals("TV")) {
			request.setAttribute("type", "RECHARGE TV");
			request.setAttribute("info", request.getParameter("operator"));
			request.setAttribute("extra", request.getParameter("number"));
			request.setAttribute("amount", request.getParameter("amount"));
		} else if (request.getParameter("type").equals("MOBILE")){
			request.setAttribute("type", "RECHARGE MOBILE");
			request.setAttribute("info", request.getParameter("operator"));
			request.setAttribute("extra", request.getParameter("number"));
			request.setAttribute("amount", request.getParameter("amount"));
		} else {
			request.setAttribute("type", "RECHARGE METRO");
			request.setAttribute("info", request.getParameter("operator"));
			request.setAttribute("extra", request.getParameter("number"));
			request.setAttribute("amount", request.getParameter("amount"));
		}
		
		request.setAttribute("command", "UPDATE");
		request.getRequestDispatcher("WalletController").forward(request, response);
		
	}
	
//	public void updateCustomer(HttpServletRequest request, HttpServletResponse response)throws Exception {
//		String name = request.getParameter("fname");
//		String email = request.getParameter("email");
//		String uname = request.getParameter("uname");
//		String password = request.getParameter("pass");
//		HttpSession session = request.getSession();
//		
//		Customer newCustomer = new Customer((Integer)session.getAttribute("userid"),name,email,uname,password);
//		customerDbUtil.updateCustomer(newCustomer);
//		
//		request.setAttribute("operation", "Update");
//		request.setAttribute("message", "Update successful, Click the button below to continue using QuickPay");
//		request.getRequestDispatcher("Success.jsp").forward(request, response);
//		
//	}
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
			
			request.setAttribute("command", "GET");
			request.getRequestDispatcher("WalletController").forward(request, response);
		} else {
			request.setAttribute("operation", "Login");
			request.setAttribute("message", "Incorrect Credentials...");
			request.getRequestDispatcher("Incorrect.jsp").forward(request, response);
		}
	}
	
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("userid");
		session.removeAttribute("uname");
		session.removeAttribute("balance");
		session.setMaxInactiveInterval(0);
		request.setAttribute("operation", "Logout");
		request.setAttribute("message", "Logout successful, thanks for using QuickPay!");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
		
	}
	
	public void editForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String name = request.getParameter("fname");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		
		Customer cust = new Customer(name,email,(String) session.getAttribute("uname"),pass);
		customerDbUtil.updateCustomer(cust, (String) session.getAttribute("userid"));
		request.setAttribute("operation", "Edit");
		request.setAttribute("message", "Edit Details successful!");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
		
	}
	
	public void handleTransfer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		if(request.getParameter("type").equals("other")) {
			request.setAttribute("name", customerDbUtil.getCustomerName(request.getParameter("user")));
			request.setAttribute("user", request.getParameter("user"));
			request.setAttribute("amount", request.getParameter("amount"));
			
		} else {
			request.setAttribute("user", session.getAttribute("userid"));
			request.setAttribute("amount", request.getParameter("amount"));
		}
		
		request.setAttribute("command", request.getParameter("TRANSACT"));
		request.getRequestDispatcher("WalletController").forward(request, response);
		
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
				
			case "LOGIN":
				handleLogin(request, response);
				break;
			
			case "HOME":
				defaultPage(request, response);
				break;
				
			case "EDIT_FORM":
				editForm(request, response);
				break;
				
			case "RECHARGE_HANDLE":
				handleRecharge(request, response);
				break;
				
			case "TRANSFER_HANDLE":
				handleTransfer(request, response);
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
