<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="model.ProdottoBean" %>
    <%@ page import="java.util.List" %>
    
    <% List<ProdottoBean> prodotti=(List <ProdottoBean>)request.getAttribute("prodotti");%>
    
     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link rel="stylesheet" href="css/home.css" media="all" />
</head>
<body>

<%@ include file="header.jsp"%>



	

<div class="in_evidenza">
<div class="in_evidenza_title">
	<h3>In evidenza</h3>
</div>


<div class="in_evidenza_products">
	
	<% if (prodotti != null) { 
        for (ProdottoBean prodotto : prodotti) { %>
            <div class="item">
            
            
            <a href="ProdottoServlet?id=<%=prodotto.getId_prodotto()%>"><%=prodotto.getId_prodotto()%></a>
           <div class="item_title"><%= prodotto.getNome_prodotto() %></div> 
           <div class="item_price"><%= prodotto.getPrezzo() %> euro</div>
           <div class="item_photo"> <img src="<%= request.getContextPath() %>/images/<%= prodotto.getImgPath() %>" alt="foto prodotto" /></div>
            
            <form action="AggiungiAlCarrelloServlet" method="post">
            
            	<input type="hidden" name="id_prodotto" value="<%=prodotto.getId_prodotto() %>" />
            	 <input type="number" name="quantita" value="1" min="1" max="<%= prodotto.getDisponibilita() %>">
            	<button type="submit">Aggiungi al carrello</button>
            </form>
            
            </div>
    <%   } 
       } else { %>
        <div>Nessun prodotto in evidenza.</div>
    <% } %>

</div>


<div class="categorie">

<div class="categorie_title">
<h3>Esplora le categorie</h3>
</div>


<div class="categorie_products">
	<div class="category_item"> <a href="CatalogoServlet?categoria=Alcolici">Alcolici</a></div>
	<div class="category_item">  <a href="CatalogoServlet?categoria=Superalcolici">Superalcolici</a></div>
	<div class="category_item">   <a href="CatalogoServlet?categoria=Analcolici">Analcolici</a> </div>
</div>

</div>


	

</div>

<%@ include file="footer.jsp"%>

</body>
</html>