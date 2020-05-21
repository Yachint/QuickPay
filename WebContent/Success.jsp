<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	String operation = "Operation";
	String message = "Go to Home Page";
	if(request.getAttribute("operation") != null){
		operation = (String)request.getAttribute("operation");
	}
	if(request.getAttribute("message") != null){
		message = (String)request.getAttribute("message");
	}
	pageContext.setAttribute("operation", operation);
	pageContext.setAttribute("message", message);
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
%>
<br><br>
<div class="container">
	<div class="jumbotron">
  <img src="icons/success.png" width="150" align="right"/>
  <h2 class="display-4">Successful <c:out value="${operation}"></c:out></h2>
  <hr class="my-4">
  <p><c:out value="${message}" /></p>
  <a class="btn btn-primary btn-lg" href="Home.jsp" role="button" >Continue</a>
</div>
</div>

</body>
</html>