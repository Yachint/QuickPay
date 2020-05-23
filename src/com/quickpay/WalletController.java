package com.quickpay;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


@WebServlet("/WalletController")
public class WalletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private WalletDbUtil walletDbUtil;
	
	@Resource(name="jdbc/QuickPay")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			walletDbUtil = new WalletDbUtil(dataSource);
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
			System.out.println("Wallet handler------------------>"+theCommand);
			switch (theCommand) {
			
			case "CREATE":
				createWallet(request, response);
				break;
			
			case "ADD":
				createWallet(request, response);
				break;
				
			case "UPDATE":
				updateWallet(request, response);
				break;
				
			case "GET":
				getWallet(request, response);
				break;
				
			case "TRANSACT":
				updateWalletTransfer(request, response);
				break;
				
			case "TRANSFER_HANDLE":
				updateWalletTransfer(request, response);
				break;
				
			case "REFRESH":
				refreshWallet(request, response);
				break;
				
			case "BOOK":
				bookMovie(request, response);
				break;
				
			default:
				getWallet(request, response);
				break;
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	public void createWallet(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		walletDbUtil.createWallet((String)request.getAttribute("userId"));
		
		request.setAttribute("operation", "Register");
		request.setAttribute("message", "Registration successful, login and start using QuickPay");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
		
	}
	
	
	public void bookMovie(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
//		System.out.println("Entered Book..");
		HttpSession session = request.getSession();
		Movies movie = (Movies) request.getAttribute("movie");
		
		String userId = (String) session.getAttribute("userid");
		String balance = (String) session.getAttribute("balance");
		
		int newBalance = Integer.parseInt(balance) - movie.getCost()* Integer.parseInt(((String) request.getAttribute("tickets")));
		
		System.out.println("Balance New :"+newBalance);
		walletDbUtil.updateWallet(userId, newBalance);
		
		request.setAttribute("command", "BOOK");
		request.setAttribute("movie", movie);
		request.setAttribute("tickets", request.getAttribute("tickets"));
		request.getRequestDispatcher("UserOrdersController").forward(request, response);
	}
	
	public void updateWalletTransfer(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		
		
		if(request.getAttribute("user") == session.getAttribute("userid")) {
			
			int currbalance = Integer.parseInt((String) session.getAttribute("balance"));
			int newBalance = currbalance + Integer.parseInt((String) request.getAttribute("amount"));
			
			walletDbUtil.updateWallet((String) session.getAttribute("userid"), newBalance);
			
			request.setAttribute("command", "CREATE");
			request.setAttribute("type", "ACCOUNT TRANSFER");
			request.setAttribute("info", "SELF TRANSFER");
			request.setAttribute("extra", "Balance Added (+)");
			request.setAttribute("amount", request.getAttribute("amount"));
			request.getRequestDispatcher("UserOrdersController").forward(request, response);
			
		} else {
			
			int currbalance = Integer.parseInt((String) session.getAttribute("balance"));
			int newBalance = currbalance - Integer.parseInt((String) request.getAttribute("amount"));
			
			walletDbUtil.updateWallet((String) session.getAttribute("userid"), newBalance);
			
			request.setAttribute("command", "CREATE");
			request.setAttribute("type", "ACCOUNT TRANSFER");
			request.setAttribute("info", (String) request.getAttribute("name"));
			request.setAttribute("extra", "Pay once (-)");
			request.setAttribute("amount", request.getAttribute("amount"));
			
			Wallet toWallet = walletDbUtil.getWallet((String) request.getAttribute("user"));
			
			int toNewBalance = Integer.parseInt(toWallet.getBalance()) + Integer.parseInt((String) request.getAttribute("amount"));
			
			walletDbUtil.updateWallet(toWallet.getUserId(), toNewBalance);
			
			request.setAttribute("userid", toWallet.getUserId());
			request.setAttribute("Atype", "ACCOUNT TRANSFER");
			request.setAttribute("Ainfo", session.getAttribute("uname"));
			request.setAttribute("Aextra", "Received (+)");
			request.setAttribute("Aamount", request.getAttribute("amount"));
			request.getRequestDispatcher("UserOrdersController").forward(request, response);
		}

	}
	
	public void updateWallet(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		int currbalance = Integer.parseInt((String) session.getAttribute("balance"));
		int newBalance = currbalance - Integer.parseInt((String) request.getAttribute("amount"));
		
		walletDbUtil.updateWallet((String) session.getAttribute("userid"), newBalance);
		
		request.setAttribute("command", "CREATE");
		request.setAttribute("type", request.getAttribute("type"));
		request.setAttribute("info", request.getAttribute("info"));
		request.setAttribute("extra", request.getAttribute("extra"));
		request.setAttribute("amount", request.getAttribute("amount"));
		request.getRequestDispatcher("UserOrdersController").forward(request, response);
	}
	public void getWallet(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();
		Wallet newWallet = walletDbUtil.getWallet((String)session.getAttribute("userid"));
		session.setAttribute("balance", newWallet.getBalance());
		
		request.getRequestDispatcher("AfterLogin.jsp").forward(request, response);
	}
	
	public void refreshWallet(HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		Wallet newWallet = walletDbUtil.getWallet((String)session.getAttribute("userid"));
		session.setAttribute("balance", newWallet.getBalance());
		
		request.setAttribute("operation", "Transaction");
		request.setAttribute("message", "Your Transaction was successful, check the history tab for more details");
		request.getRequestDispatcher("Success.jsp").forward(request, response);
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
