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

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String type = "mobile";
	String formName = "Recharge Mobile Services :";
	String[] operators = {"Airtel", "Vodafone", "JIO"};
	if(session.getAttribute("userid") == null){
		response.sendRedirect("Home.jsp");
	}
	if(request.getParameter("type") != null){
		if(request.getParameter("type").equals("tv")){
			type = "tv";
			String[] newop = {"Tata Sky","Dish TV","Airtel HD Plus"};
			operators = newop;
			formName = "Recharge TV Services :";
		}
		if(request.getParameter("type").equals("metro")){
			type = "metro";
			String[] newop = {"Delhi Metro","Mumbai Metro","Other"};
			operators = newop;
			formName = "Recharge Metro Card :";
		}	
	}
	
	pageContext.setAttribute("type", type);
	request.getRequestDispatcher("NavBar.jsp").include(request, response);
	request.setAttribute("operators", operators);
    request.setAttribute("formName", formName);
    request.setAttribute("type", type);
    request.getRequestDispatcher("Form.jsp").include(request, response);
%>
<br>

</body>
</html>