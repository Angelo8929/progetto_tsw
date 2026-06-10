<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean"%>
<% String username=(String) session.getAttribute("messaggio_benvenuto");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/home.css" />
</head>
<body>
	<div class="header">
		<div class="title">
			<h1>
				<a href="<%= request.getContextPath() %>/home">AlcoMarket</a>
			</h1>
		</div>






		<div class="search">
		 
		
			<input type="text" name="cerca" id="cerca" placeholder="chi cerca trova..." />
		</div>

		<div class="account">

			<% UtenteBean utente=(UtenteBean) session.getAttribute("user");
				
		
		if (username ==null){
		
		%>

			<a href="login.jsp">Accedi</a>
			<% } else{ %>

			<div class="dropdown">
				<button class="dropbtn">Benvenuto, <%=username %></button>
				<div class="dropdown-content">
					<a href="#">I miei ordini</a> 
					<a href="">Area riservata</a> 
					
					<form action="LogoutServlet" method="post">
					
						<input type="submit" value="Disconnetti" />
					</form>
					
					
					
				</div>
			</div>


			<% }%>



		</div>





	</div>

	<div class="navbar">
		<a href="catalogo.jsp">catalogo</a> 
		<a href="chi_siamo.jsp">chi siamo</a> 
		<a href="contattaci.jsp">contattaci</a>
	</div>
</body>
</html>