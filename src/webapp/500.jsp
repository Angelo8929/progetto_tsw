<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Errore Interno - AlcoMarket</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/errori.css" media="all" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/home.css" media="all" />
    
</head>
<body>
    <div class="error-container">
        <h1>500</h1>
        <h2>Errore del Server</h2>
        <p>Si è verificato un problema interno all'applicazione. Riprova più tardi</p>
        <a href="<%= request.getContextPath() %>/" class="btn-home">Torna alla Home</a>
    </div>

    <%-- Dettaglio tecnico nascosto nel sorgente HTML per il debug rapido --%>
    </body>
</html>