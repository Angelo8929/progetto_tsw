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






		<div class="search" style="position: relative;">
			<input type="text" name="cerca" id="cerca" placeholder="chi cerca trova..." autocomplete="off" />
			<div id="suggerimenti" class="dropdown-suggerimenti"></div>
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
					<a href="CarrelloServlet">Carrello</a>
					<a href="#">I miei ordini</a> 
					<a href="AreaRiservataServlet">Area riservata</a> 
					
					<form action="LogoutServlet" method="post">
					
						<input type="submit" value="Disconnetti" />
					</form>
					
					
					
				</div>
			</div>


			<% }%>



		</div>





	</div>

	<div class="navbar">
		<a href="CatalogoServlet">catalogo</a> 
		<a href="chi_siamo.jsp">chi siamo</a> 
		<a href="contattaci.jsp">contattaci</a>
	</div>
	
	<script>
    // Questa variabile globale sarà leggibile da qualsiasi file .js esterno caricato dopo
    window.contextPath = "<%= request.getContextPath() %>";
</script>

<script src="js/search.js"></script>
</body>
</html>