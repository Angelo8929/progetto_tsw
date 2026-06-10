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


<p>Nome prodotto: <%=prodotto.getNome_prodotto() %></p>

<%@ include file="footer.jsp" %>




</body>
</html>