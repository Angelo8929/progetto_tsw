<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<%
	
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
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/conferma_ordine.css" media="all" />
    
</head>
<body>

<%@ include file="header.jsp" %>

<div class="conferma-container">
    <div class="success-icon">✓</div>
    <h1>Grazie per il tuo acquisto!</h1>
    <p>
        Il tuo ordine è stato ricevuto con successo ed è ora in fase di elaborazione.<br>
        
    </p>
    
    <div class="btn-group">
        <a href="<%= request.getContextPath() %>/IndexServlet" class="btn btn-primary">Continua lo Shopping</a>
        
        <a href="<%= request.getContextPath() %>/AreaRiservataServlet" class="btn btn-secondary">I Miei Ordini</a>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>