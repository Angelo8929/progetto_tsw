<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<% String errorMessage=(String) request.getAttribute("errorMessage");
		String successMessage=(String) request.getAttribute("successMessage");
	
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/auth.css" media="all" />
</head>
<body>
<%@ include file="header.jsp"  %>
<h1>Login</h1>

<% if (errorMessage != null) {%>
	<p><%=errorMessage %></p>
	
	
	<% } else if(successMessage!=null) {%>
	<p><%=successMessage %></p>
	<%} %>
	
	<div class="form">

		<form action="LoginServlet" method="post" novalidate>

			<div class="campo">
				<label for="email">email</label>
				<input type="email" name="email" id="email"/>
			
			</div>
			<div class="campo">
				<label for="password">password</label>
				<input type="password" name="password" id="password"/>
			</div>
			
			<span>Non sei ancora registrato?</span><a href="register.jsp">Fallo</a>
			
			<input type="submit" value="Accedi" />

		</form>



	</div>
	
	<%@ include file="footer.jsp" %>
	
	<script src="<%=request.getContextPath() %>/js/login.js"></script>

</body>
</html>