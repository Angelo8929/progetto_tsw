<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="header.jsp"%>

	<h1>Registrazione</h1>

	<div class="form">

		<form action="RegistrazioneServlet" method="post">

			<div class="campo">
				<label for="email">Email</label> <input type="text" name="email"
					id="email" />

			</div>


			<div class="campo">
				<label for="username">Username</label> <input type="text"
					id="username" name="username" />

			</div>
			<div class="campo">
				<label for="password">Password</label> <input type="password"
					id="password" name="password" />
			</div>
			<div class="campo">
				<label for="conferma_password">Conferma password</label> <input
					type="password" id="conferma_password" name="conferma_password" />
			</div>



			<div class="campo">
				<input type="submit" value="Registrati" />

			</div>

		</form>

	</div>

	<%@ include file="footer.jsp"%>


	<script src="<%=request.getContextPath()%>/js/register.js"></script>

</body>
</html>