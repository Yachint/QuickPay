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
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
%>
<br><br>
<div class="container">
	<h2>Login :</h2>
	<br>
	<div class="dropdown-divider"></div>
	<br>
	<form action="CustomerController" method="POST">
  <div class="form-group">
  	<input type="hidden" name="command" value="LOGIN">
    <label for="uname">User Name</label>
    <input type="text" class="form-control" id="uname" name="uname" aria-describedby="emailHelp">
  </div>
  <div class="form-group">
    <label for="pass">Password</label>
    <input type="password" class="form-control" name="pass" id="pass">
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>

</body>
</html>