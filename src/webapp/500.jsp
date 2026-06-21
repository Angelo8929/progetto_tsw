<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Errore Interno - AlcoMarket</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; background-color: #f4f7f6; padding: 50px; }
        .error-container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); display: inline-block; max-width: 500px; }
        h1 { color: #d9534f; font-size: 60px; margin: 0; }
        h2 { color: #333; }
        p { color: #666; }
        .btn-home { display: inline-block; background-color: #008CBA; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; font-weight: bold; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>500</h1>
        <h2>Errore del Server</h2>
        <p>Si è verificato un problema interno all'applicazione. I nostri tecnici sono stati informati.</p>
        <a href="<%= request.getContextPath() %>/index.jsp" class="btn-home">Torna alla Home</a>
    </div>

    <%-- Dettaglio tecnico nascosto nel sorgente HTML per il debug rapido --%>
    </body>
</html>