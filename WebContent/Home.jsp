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
	if(session.getAttribute("userid") != null){
		response.sendRedirect("AfterLogin.jsp");
	}
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
%>
<br><br>
<div class="container">
	<div class="jumbotron">
  <h1 class="display-4">Welcome to Quick Pay</h1>
  <br>
  <div id="carouselExampleSlidesOnly" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
        <img src="icons/payment-banks.jpg" class="d-block w-100" alt="...">
    </div>
  </div>
</div>
<br>
  <p class="lead">Hello Customers, here you can quickly pay your monthly bills or book movie tickets, all with a single click!</p>
  <hr class="my-4">
  <p>This website was built with JSP, Java and JDBC to provide you with the fastest experience possible!</p>
  <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
</div>
</div>
</body>
</html>