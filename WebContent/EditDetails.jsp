<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.quickpay.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
	Customer cust = (Customer) request.getAttribute("cust");
	pageContext.setAttribute("cust", cust);
	
%>
<br><br>
<div class="container">
	<h2>Sign-up :</h2>
	<br>
	<div class="dropdown-divider"></div>
	<br>
	<form action="CustomerController" method="POST">
  <div class="form-group">
    <label for="fname">Name</label>
    <input type="text" class="form-control" id="fname" name="fname" value="${cust.getName()}" >
  </div>
  	<input type="hidden" name="command" value="EDIT_FORM">
    <div class="form-group">
    <label for="email">Email</label>
    <input type="email" class="form-control" id="email" name="email" value="${cust.getEmail()}" aria-describedby="emailHelp">
  </div>
  <div class="form-group">
    <label for="pass">Password</label>
    <input type="password" class="form-control" name="pass" id="pass" value="${cust.getPassword()}" >
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>


</body>
</html>