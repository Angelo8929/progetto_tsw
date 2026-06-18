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

<form action="AggiungiAlCarrelloServlet" method="post">
	<input type="hidden" name="id_prodotto" value="<%=prodotto.getId_prodotto() %>" />
	<label for="quantita">Quantità:</label>
        <input type="number" id="quantita" name="quantita" min="1" max="5" step="1" value="1">
	<button type="submit">Aggiungi al carrello</button>

</form>

<%@ include file="footer.jsp" %>




</body>
</html>