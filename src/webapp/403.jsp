<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<% response.setStatus(HttpServletResponse.SC_FORBIDDEN); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato - AlcoMarket</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/errori.css" media="all" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/home.css" media="all" />
    
</head>
<body>
    <div class="error-container">
        <h1>403</h1>
        <h2>Accesso Vietato</h2>
        <p>Non disponi dei permessi necessari per visualizzare questa risorsa amministrativa.</p>
        <a href="<%= request.getContextPath() %>/" class="btn-home">Torna alla Home</a>
    </div>
</body>
</html>