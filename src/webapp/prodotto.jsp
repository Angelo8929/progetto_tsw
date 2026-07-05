<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.ProdottoBean" %>  
<% ProdottoBean prodotto=(ProdottoBean) request.getAttribute("prodotto"); %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dettaglio Prodotto</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/prodotto.css" media="all" />
</head>
<body>



<%@ include file="header.jsp" %>

<div class="product-container">
	<div class="product-image">
		<img src="<%= request.getContextPath() %>/images/<%= prodotto.getImgPath() %>" alt="foto prodotto" />
	</div>
	<div class="product-details">
	 <span><a href="CatalogoServlet?categoria=<%=prodotto.getCategoria() %>"><%=prodotto.getCategoria() %></a></span>    
	<h1><%=prodotto.getNome_prodotto() %></h1>
	<p><%=prodotto.getDescrizione() %></p>
<strong><%=prodotto.getPrezzo() %> €</strong>
<p>Prezzo con iva: <%=String.format("%.2f",prodotto.getPrezzo()+(prodotto.getPrezzo()*(prodotto.getIva()/100.0))) %> €</p>
	<form action="AggiungiAlCarrelloServlet" method="post">
	<input type="hidden" name="id_prodotto" value="<%=prodotto.getId_prodotto() %>" />
	<label for="quantita">Quantità:</label>
        <input type="number" id="quantita" name="quantita" min="1" max="<%= prodotto.getDisponibilita() %>" step="1" value="1">
	<button type="submit">Aggiungi al carrello</button>

</form>
	</div>
	
</div>






<%@ include file="footer.jsp" %>




</body>
</html>