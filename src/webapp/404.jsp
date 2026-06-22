<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<% response.setStatus(HttpServletResponse.SC_NOT_FOUND); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pagina Non Trovata - AlcoMarket</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/errori.css" media="all" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/home.css" media="all" />
    
</head>
<body>
    <div class="error-container">
        <h1>404</h1>
        <h2>Ops! Pagina non trovata</h2>
        <p>La risorsa o la pagina che stai cercando potrebbe essere stata rimossa, aver cambiato nome o essere momentaneamente non disponibile.</p>
        <a href="<%= request.getContextPath() %>/" class="btn-home">Torna alla Home</a>
    </div>
</body>
</html>