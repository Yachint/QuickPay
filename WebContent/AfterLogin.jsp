<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.column {
  float: left;
  width: 33.33%;
  padding: 15px;
}

.row::after {
  content: "";
  clear: both;
  display: table;
}

.row{
	margin-right: -140px;
}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	if(session.getAttribute("userid") == null){
		response.sendRedirect("Home.jsp");
	}
	pageContext.setAttribute("uname", session.getAttribute("uname"));
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
%>
<br><br>
<div class="container">
	<div class="jumbotron">
  <h1 class="display-4">Welcome <c:out value="${uname}"></c:out></h1>
  <p class="lead">You can access the following services on QuickPay :</p>
  <hr class="my-4">
  <div class="row">
	  <div class="column">
			  	<div class="card column" style="width: 15rem;">
			  <img src="icons/bill.png" class="card-img-top" alt="bill">
			  <div class="card-body">
			    <h5 class="card-title"><b>Pay Bills</b></h5>
			    <hr class="my-4">
			    <p class="card-text">Pay bills like Prepaid Mobile, TV Recharge and lot more.</p>
			    <form action="CustomerController" method="GET">
			    	<input type="hidden" name="command" value="RECHARGE">
			    	<button class="btn btn-primary">Explore</button>
			    </form>
			  </div>
	 		 </div>
	 	</div>
	  
	  <div class="column">
			  <div class="card column" style="width: 15rem;">
			  <img src="icons/money.png" class="card-img-top" alt="bill">
			  <div class="card-body">
			    <h5 class="card-title"><b>Transfer Money</b></h5>
			    <hr class="my-4">
			    <p class="card-text">Send Money to other users using QuickPay Instantly!</p>
			    <form action="CustomerController" method="GET">
			    	<input type="hidden" name="command" value="TRANSFER">
			    	<button  class="btn btn-primary">Explore</button>
			    </form>
			  </div>
	  		</div>
	  	</div>
	  
	  <div class="column">
			  <div class="card column" style="width: 15rem;">
			  <img src="icons/video.png" class="card-img-top" alt="bill">
			  <div class="card-body">
			    <h5 class="card-title"><b>Movie Tickets</b></h5>
			    <hr class="my-4">
			    <p class="card-text">Book tickets for famous blockbuster films near you.</p>
			    <form action="CustomerController" method="GET">
			    	<input type="hidden" name="command" value="GET_MOVIES">
			    	<button  class="btn btn-primary">Explore</button>
			    </form>
			 </div> 		
	  		</div>
	  	</div>
  	 
  </div>
 
</div>
	
</div>
</body>
</html>