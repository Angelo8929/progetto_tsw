<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<%
	// Controllo di sicurezza: se un utente non loggato prova ad accedere a questa pagina a caso,
	// lo rimandiamo alla home.
	UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");
	if (utenteLoggato == null) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ordine Confermato!</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f7f6; }
        .conferma-container { max-width: 600px; margin: 80px auto; padding: 40px; background: white; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; }
        .success-icon { font-size: 64px; color: #4CAF50; margin-bottom: 20px; }
        h1 { color: #333; margin-bottom: 10px; }
        p { color: #666; font-size: 16px; line-height: 1.6; margin-bottom: 30px; }
        .btn-group { display: flex; justify-content: center; gap: 15px; }
        .btn { display: inline-block; padding: 12px 24px; font-size: 15px; font-weight: bold; text-decoration: none; border-radius: 4px; transition: background 0.2s; }
        .btn-primary { background-color: #4CAF50; color: white; }
        .btn-primary:hover { background-color: #45a049; }
        .btn-secondary { background-color: #008CBA; color: white; }
        .btn-secondary:hover { background-color: #007399; }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="conferma-container">
    <div class="success-icon">✓</div>
    <h1>Grazie per il tuo acquisto!</h1>
    <p>
        Il tuo ordine è stato ricevuto con successo ed è ora in fase di elaborazione.<br>
        Abbiamo inviato un resoconto dettagliato all'indirizzo email: <strong><%= utenteLoggato.getEmail() %></strong>
    </p>
    
    <div class="btn-group">
        <a href="<%= request.getContextPath() %>/home.jsp" class="btn btn-primary">Continua lo Shopping</a>
        
        <a href="<%= request.getContextPath() %>/AreaRiservataServlet" class="btn btn-secondary">I Miei Ordini</a>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>