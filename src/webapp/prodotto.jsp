<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.ProdottoBean" %>  
<% ProdottoBean prodotto=(ProdottoBean) request.getAttribute("prodotto"); %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dettaglio Prodotto</title>
</head>
<body>



<%@ include file="header.jsp" %>

<h1>Dettaglio prodotto</h1>

<p>Nome prodotto: <%=prodotto.getNome_prodotto() %></p>
<p>Prezzo prodotto: <%=prodotto.getPrezzo() %></p>

<form action="AggiungiAlCarrelloServlet">
	<input type="hidden" name="id_prodotto" value=<%=prodotto.getId_prodotto() %> />
	<button type="submit">Aggiungi al carrello</button>

</form>

<%@ include file="footer.jsp" %>




</body>
</html>