package com.quickpay;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/UserOrdersController")
public class UserOrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserOrdersDbUtil userOrdersDbUtil;
	
	@Resource(name="jdbc/QuickPay")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			userOrdersDbUtil = new UserOrdersDbUtil(dataSource);
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
			
			if(theCheck != null) {
				theCommand = theCheck;
			}
			
			if(theCommand == null && theCheck==null) {
				getOrders(request, response);
			} else {
				System.out.println("User Orders Handler------------------>"+theCommand);
				switch (theCommand) {
				
				case "CREATE":
					createOrder(request, response);
					break;
				
				case "TRANSFER_HANDLE":
					createOrder(request, response);
					break;
					
				case "GET":
					getOrders(request, response);
					break;
				
				case "BOOK":
					ticketReciept(request, response);
					break;
					
				default:
					getOrders(request, response);
					break;
				}
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	void createOrder(HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		
		if(request.getAttribute("userid") != null) {
			String uid1 = (String) request.getAttribute("userid");
			String type1 = (String) request.getAttribute("Atype");
			String info1 = (String) request.getAttribute("Ainfo");
			String extra1 = (String) request.getAttribute("Aextra");
			String amount1 = (String) request.getAttribute("Aamount");
			
			userOrdersDbUtil.createOrder(uid1, type1, info1, extra1, amount1);
		} 
			
			String uid = (String) session.getAttribute("userid");
			String type = (String) request.getAttribute("type");
			String info = (String) request.getAttribute("info");
			String extra = (String) request.getAttribute("extra");
			String amount = (String) request.getAttribute("amount");
			
			userOrdersDbUtil.createOrder(uid, type, info, extra, amount);
			request.setAttribute("command", "REFRESH");
			request.getRequestDispatcher("WalletController").forward(request, response);
		
	}
	
	void ticketReciept(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		Movies movie = (Movies) request.getAttribute("movie");
		
		String uid = (String) session.getAttribute("userid");
		String type = "MOVIE TICKET";
		String info = movie.getName();
		String extra = request.getAttribute("tickets")+" - $"+movie.getCost();
		String amount = String.valueOf(Integer.parseInt((String) request.getAttribute("tickets")) * movie.getCost());
		System.out.println(uid+" "+type+" "+info+" "+extra+" "+amount+" ");
		
		userOrdersDbUtil.createOrder(uid, type, info, extra, amount);
		request.setAttribute("command", "REFRESH");
		request.getRequestDispatcher("WalletController").forward(request, response);
	}
	
	void getOrders(HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		
		String uid = (String) session.getAttribute("userid");
		
		ArrayList<UserOrders> orders = (ArrayList<UserOrders>) userOrdersDbUtil.getAllOrders(uid);
//		System.out.println("Size :"+orders.length);
		
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("Orders.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
