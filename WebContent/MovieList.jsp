<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.quickpay.Movies" %>
<!DOCTYPE html>
<html>
<head>
<style>
.options {
	
}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.getRequestDispatcher("NavBar.jsp").include(request, response);	
	ArrayList<Movies> arr = (ArrayList<Movies>)request.getAttribute("MovieList");
	pageContext.setAttribute("movies", arr);
%>

<br>
<div class="container">
	<h3>Select a Movie: </h3>
	<br>
	<ul class="list-group">
		<c:forEach items="${movies}" var="movie">
				  <li class="list-group-item">
					<div class="card mb-3" >
					  <div class="row no-gutters">
					    <div class="col-md-4">
					      <img src="${movie.getImage()}" class="card-img" alt="title">
					    </div>
					    <div class="col-md-8">
					      <div class="card-body">
					        <h5 class="card-title"><b><c:out value="${movie.getName()}" /></b></h5>
					        <p class="card-text"><c:out value="${movie.getDescription()}" /></p>
					        <p class="card-text"><small class="text-muted">Released <c:out value="${movie.getDate()}" /></small></p>
					        <h4>Cost: $<c:out value="${movie.getCost()}" /></h4>
					        <h3>Tickets Available: <c:out value="${movie.getTickets()}" /></h3>
					         <form action="MoviesControllerServlet" method="GET">
					         	<input type="hidden" name="MovieId" value="${movie.getId()}">
						    	<input type="hidden" name="command" value="BOOK">
						    	<div class="input-group mb-3">
								  <input type="text" class="form-control" placeholder="No. of tickets" name="tickets">
								  <div class="input-group-append">
								    <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Buy</button>
								  </div>
								</div>
					    	</form>
					      </div>
					    </div>
					  </div>
					</div>
				</li>
		</c:forEach>
	</ul>
</div>

</body>
</html>