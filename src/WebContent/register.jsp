<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="header.jsp"  %>


<div class="form">

	<form action="RegistrazioneServlet" method="post">
	
	<div class="campo">
		<label for="email">email</label>
		<input type="text" name="email" id="email" />
	
	</div>
	
	
	 	<div class="campo">
				<label for="username">username</label>
				<input type="text" id="username" name="username"/>
			
			</div>
			<div class="campo">
				<label for="password">password</label>
				<input type="password" name="password"/>
			</div>
			
			<div class="campo">
				<input type="submit" value="Registrati" />
			
			</div>
	
	</form>

</div>

<%@ include file="footer.jsp" %>

</body>
</html>