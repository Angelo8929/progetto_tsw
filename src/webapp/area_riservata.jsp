<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Area riservata</title>
</head>
<body>

<%@ include file="header.jsp" %>
 <% String username=session.getAttribute("messaggio_benvenuto") %>
 
 <h1>Benvenuto, <%=username %></h1>



<%@ include file="footer.jsp" %>

</body>
</html>