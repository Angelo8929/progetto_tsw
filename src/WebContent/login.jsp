<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet" href="css/home.css" media="all" />
</head>
<body>
<%@ include file="header.jsp"  %>
<h1>Login</h1>
	<div class="form">

		<form action="LoginServlet" method="post">

			<div class="campo">
				<label for="email">email</label>
				<input type="text" name="email" id="email"/>
			
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

</body>
</html>