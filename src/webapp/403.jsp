<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<% response.setStatus(HttpServletResponse.SC_FORBIDDEN); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato - AlcoMarket</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; background-color: #f4f7f6; padding: 50px; }
        .error-container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); display: inline-block; max-width: 500px; }
        h1 { color: #f0ad4e; font-size: 60px; margin: 0; }
        h2 { color: #333; }
        p { color: #666; }
        .btn-home { display: inline-block; background-color: #008CBA; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; font-weight: bold; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>403</h1>
        <h2>Accesso Vietato</h2>
        <p>Non disponi dei permessi necessari per visualizzare questa risorsa amministrativa.</p>
        <a href="<%= request.getContextPath() %>/index.jsp" class="btn-home">Torna alla Home</a>
    </div>
</body>
</html>