<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	String formName = "Form :"; 
	String[] operators = {"Operator 1", "Operator 2", "Operator 3"};
	int ops = 1;
	if(request.getAttribute("type") == "mobile"){
		ops = 2;
	}
	if(request.getAttribute("type") == "metro"){
		ops = 3;
	}
	if(request.getAttribute("formName") != null){
		formName = (String)request.getAttribute("formName");
	}
	if(request.getAttribute("operators") != null){
		operators = (String[])request.getAttribute("operators");
	}
	pageContext.setAttribute("ops", ops);
	pageContext.setAttribute("Form", formName);
	pageContext.setAttribute("Operators", operators);
%>
<br><br>
<div class="card">
  <div class="card-header">
    <ul class="nav nav-pills card-header-pills">
      <li class="nav-item check">
        <form action="SelectService.jsp" ><input type="hidden" name="type" value="mobile"><button type="submit" class="btn btn-outline-primary">Mobile Recharge</button></form>
      </li>
      <li class="nav-item check">
        <form action="SelectService.jsp"><input type="hidden" name="type" value="tv"><button type="submit" class="btn btn-outline-primary">TV Recharge</button></form>
      </li>
      <li class="nav-item check">
        <form action="SelectService.jsp"><input type="hidden" name="type" value="metro"><button type="submit" class="btn btn-outline-primary">Metro Card Recharge</button></form>
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
  	<input type="hidden" name="command" value="RECHARGE_HANDLE">
  	<input type="hidden" name="type" value="TV">
  	<label for="uname">Select TV Provider</label>
	  	<select class="form-control" name="operator">
	  	<c:forEach var="val" items="${Operators}">
				<option value="${val}" > ${val} </option>
		</c:forEach>
		</select>
	<br>
	<label for="number">Enter Subscription Number</label>
    <input type="text" class="form-control" id="number" name="number">
	<br>
	<label for="amount">Enter Amount</label>
    <input type="text" class="form-control" id="amount" name="amount">
    <br>
    
	</c:if>
	
	<c:if test="${ops == 2}">
		<label for="Op">Select Operator</label>
		<select class="form-control" name="operator">
			<c:forEach var="val" items="${Operators}">
				<option value="${val}" > ${val} </option>
			</c:forEach>
		</select>
		<br>
  	<input type="hidden" name="command" value="RECHARGE_HANDLE">
  	<input type="hidden" name="type" value="MOBILE">
    <label for="amount">Enter Amount</label>
    <input type="text" class="form-control" id="amount" name="amount">
    <br>
    
    <label for="number">Enter Mobile Number</label>
    <input type="text" class="form-control" id="number" name="number">
  	<br>
	</c:if>
	
	<c:if test="${ops == 3}">
	<input type="hidden" name="command" value="RECHARGE_HANDLE">
	<input type="hidden" name="type" value="METRO">
		<label for="Op">Select Metro</label>
		<select class="form-control" name="operator">
			<c:forEach var="val" items="${Operators}">
				<option value="${val}" > ${val} </option>
			</c:forEach>
		</select>
		<br>
  	
    <label for="amount">Enter Amount</label>
    <input type="text" class="form-control" id="amount" name="amount">
    <br>
    
    <label for="number">Enter Metro Card Number</label>
    <input type="text" class="form-control" id="number" name="number">
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