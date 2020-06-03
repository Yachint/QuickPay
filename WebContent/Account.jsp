<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.quickpay.Customer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.card {
	margin-left: 120px;
	margin-right: 120px;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
	Customer cust = new Customer(3,"Name 1", "Email 1", "Uname 1");
	if(request.getAttribute("THE_Customer") != null){
		cust = (Customer)request.getAttribute("THE_Customer");
	}
	pageContext.setAttribute("cust", cust);
	pageContext.setAttribute("bal",session.getAttribute("balance"));
%>
<br>
<div class="card text-center">
  <div class="card-header">
    User Details
  </div>
  <div class="card-body">
    <p class="card-text">Name: <c:out value="${cust.getName()}" /></p>
    <p class="card-text">Email: <c:out value="${cust.getEmail()}" /></p>
    <p class="card-text">Username: <c:out value="${cust.getUname()}" /></p>
    <form action="CustomerController" method="GET" >
    	<input type="hidden" name="command" value="EDIT_EXISTING">
    	<button class="btn btn-primary" type="submit" >Edit Details</button>
    </form>
  </div>
  <div class="card-footer">
    Account Balance: <c:out value="${bal}" />
  </div>
</div>
</body>
</html>