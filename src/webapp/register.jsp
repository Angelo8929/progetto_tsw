<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<% String errorMessage=(String) request.getAttribute("errorMessage"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/auth.css" media="all" />
</head>
<body>
	<%@ include file="header.jsp"%>

	<h1>Registrazione</h1>
	
	<% if (errorMessage!= null) {%>
		<p><%=errorMessage%></p>
	<%} %>
	<div class="form">
	
	<%-- RECUPERO E STAMPA ERRORI APPROCCIO 2 --%>
		<% 
			java.util.List<String> errori = (java.util.List<String>) request.getAttribute("errori");
			String oldUsername = request.getAttribute("oldUsername") != null ? (String) request.getAttribute("oldUsername") : "";
			String oldEmail = request.getAttribute("oldEmail") != null ? (String) request.getAttribute("oldEmail") : "";
			
			if (errori != null && !errori.isEmpty()) {
		%>
			<div style="background-color: #f2dede; color: #a94442; border: 1px solid #ebccd1; padding: 15px; border-radius: 4px; margin-bottom: 20px;">
				<strong style="display: block; margin-bottom: 5px;">Risolvi i seguenti errori prima di procedere:</strong>
				<ul style="margin: 0; padding-left: 20px; font-size: 14px;">
					<% for (String errore : errori) { %>
						<li><%= errore %></li>
					<% } %>
				</ul>
			</div>
		<% 
			} 
		%>

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

			<span>Sei già registrato?</span><a href="login.jsp">Accedi</a>

			<div class="campo">
				<input type="submit" value="Registrati" />

			</div>

		</form>

	</div>

	<%@ include file="footer.jsp"%>


	<script src="<%=request.getContextPath()%>/js/register.js"></script>

</body>
</html>