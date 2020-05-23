<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.quickpay.Customer" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.check {
	margin: 0 20px 0;

}
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
	String formName = "Form :"; 
	List<Customer> cust = new ArrayList<Customer>();
	Customer c = new Customer("Name 1", "name@gmail.com", "uname", "pass");
	cust.add(c);
	int ops = 1;
	if(request.getParameter("type") != null){
		if(request.getParameter("type").equals("self")){
			ops = 2;
		}
	}	
	if(request.getParameter("formName") != null){
		formName = (String)request.getParameter("formName");
	}
	if(request.getAttribute("formName") != null){
		formName = (String)request.getAttribute("formName");
	}
	if(request.getAttribute("operators") != null){
		cust = (ArrayList<Customer>)request.getAttribute("operators");
	}
	if(request.getParameter("operators") != null){
		cust = (ArrayList<Customer>)request.getAttribute("operators");
	}
	pageContext.setAttribute("ops", ops);
	pageContext.setAttribute("Form", formName);
	pageContext.setAttribute("Operators", cust);
%>
<br><br>
<div class="card">
  <div class="card-header">
    <ul class="nav nav-pills card-header-pills">
      <li class="nav-item check">
        <form action="CustomerController" method="GET">
        <input type="hidden" name="type" value="another">
        <input type="hidden" name="formName" value="Transfer Money to other User :">
        <input type="hidden" name="command" value="TRANSFER">
        <button type="submit" class="btn btn-outline-primary">Transfer to Other User</button></form>
      </li>
      <li class="nav-item check">
        <form action="TransferForm.jsp">
        <input type="hidden" name="type" value="self">
        <input type="hidden" name="formName" value="Transfer Money to this Account :">
        <button type="submit" class="btn btn-outline-primary">Transfer to Self</button></form>
      </li>
    </ul>
  </div>
  <div class="card-body">
    	<div class="container">
	<h2><c:out value="${Form}" /></h2>
	<br>
	<div class="dropdown-divider"></div>
	<br>
	<form action="CustomerController" method="POST">
  <div class="form-group">
  	
  	<c:if test="${ops == 1}">
	  	<input type="hidden" name="command" value="TRANSFER_HANDLE">
	  	<input type="hidden" name="type" value="other">
	  	<label for="uname">Select QuickPay User</label>
		  	<select class="form-control" name="user">
		  	<c:forEach var="val" items="${Operators}">
					<option value="${val.getUserId()}" > ${val.getName()} </option>
			</c:forEach>
			</select>
		<br>
		<label for="amount">Enter Amount</label>
	    <input type="text" class="form-control" id="amount" name="amount">
	    <br>
	</c:if>
	
	<c:if test="${ops == 2}">
	  	<input type="hidden" name="command" value="TRANSFER_HANDLE">
	  	<input type="hidden" name="type" value="self">
	    <label for="amount">Enter Amount</label>
	    <input type="text" class="form-control" id="amount" name="amount">
	    <br>
	</c:if>
	
  </div>

  <button type="submit" class="btn btn-primary">Checkout</button>
	</form>
</div>
  </div>
</div>

</body>
</html>