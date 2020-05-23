<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.quickpay.UserOrders" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
	
	int i = 1;
	ArrayList<UserOrders> orders = (ArrayList<UserOrders>)request.getAttribute("orders");
	int check = 1;
	if(orders == null || orders.size() == 0){
		check = 0;
		pageContext.setAttribute("check", check);
	} else {
		pageContext.setAttribute("check", check);
		pageContext.setAttribute("orders", orders);
	}

%>
<br>
<h2>Your Order History :</h2>
<div class="dropdown-divider"></div>
<br>
<div class="container">

<c:if test="${check == 1}">
	
	<table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col">SNo.</th>
      <th scope="col">Type</th>
      <th scope="col">Info</th>
      <th scope="col">Extra</th>
      <th scope="col">Amount</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${orders}"  var="order">
  	<tr>
      <td><%= i++ %></td>
      <td><c:out value="${order.getType()}" /></td>
      <td><c:out value="${order.getInfo()}" /></td>
      <td><c:out value="${order.getExtra()}" /></td>
      <td><c:out value="${order.getAmount()}" /></td>
    </tr>
  
  </c:forEach>
  </tbody>
</table>
	

</c:if>
</div>

<c:if test="${check == 0}">
	<div class="jumbotron container">
  <div class="container">
    <h3 class="display-4">Your history log is empty</h3>
    <p class="lead">Please initiate some transactions to populate this page!</p>
  </div>
	</div>
</c:if>

</body>
</html>