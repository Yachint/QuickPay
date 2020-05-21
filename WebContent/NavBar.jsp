<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	if(session.getAttribute("userid") == null){
		pageContext.setAttribute("button1", "Login");
		pageContext.setAttribute("route1", "Login.jsp");
		pageContext.setAttribute("button2", "Signup");
		pageContext.setAttribute("route2", "Signup.jsp");
	} else {
		pageContext.setAttribute("button1", "My Orders");
		pageContext.setAttribute("route1", "Orders.jsp");
		pageContext.setAttribute("button2", "Logout");
		pageContext.setAttribute("route2", "Logout.jsp");
	}

%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="#"><b>QuickPay</b></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="Home.jsp">Home</a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="${route1}"><c:out value="${button1}" /></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="${route2}"><c:out value="${button2}" /></a>
      </li>
    </ul>
  </div>
</nav>

</body>
</html>