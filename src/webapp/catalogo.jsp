<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ProdottoBean"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/catalogo.css" media="all" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>

<%
List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti");
%>
<body>
	<%@ include file="header.jsp"%>




	<div class="catalog">
		<div class="filter_sidebar">

			<h3>Filtra prodotti</h3>

			<form action="CatalogoServlet" method="get">

				<div class="sidebar_categorie">
					<label>Categorie</label><br> <input type="checkbox"
						id="alcolici" name="categoria" value="Alcolici"> <label
						for="alcolici">Alcolici</label><br> <input type="checkbox"
						id="superalcolici" name="categoria" value="Superalcolici">
					<label for="superalcolici">Superalcolici</label><br> <input
						type="checkbox" id="analcolici" name="categoria"
						value="Analcolici"> <label for="analcolici">Analcolici</label>
				</div>

				<div class="sidebar_prezzo">
					<label>Prezzo</label> <input type="number" name="" id=""
						placeholder="min" /> <span>-</span> <input type="number" name=""
						id="" placeholder="max" />
				</div>



				<div class="sidebar_submit">
					<input type="submit" value="Applica filtri" />

				</div>


				<div class="sidebar_reset">
    <a href="CatalogoServlet" class="btn-reset">Ripristina filtri</a>
</div>


			</form>





		</div>
		<div class="main">
			<h2>Catalogo</h2>
			<div class="products">
				<%
				if (prodotti == null || prodotti.isEmpty()) {
				%>
				<p>Nessun prodotto disponibile nel catalogo.</p>
				<%
				} else {
				%>
				<%
				for (ProdottoBean prod : prodotti) {
				%>
				<div class="item">
               <div class="item_title"><%= prod.getNome_prodotto() %></div> 
               <div class="item_price"><%= String.format("%.2f", prod.getPrezzo()) %> €</div>
               <div class="item_photo"> 
                <a class="item_link" href="ProdottoServlet?id=<%=prod.getId_prodotto()%>">
                	<img src="<%= request.getContextPath() %>/images/<%= prod.getImgPath() %>" alt="foto prodotto" />
                </a>
               </div>
                
                <form action="AggiungiAlCarrelloServlet" method="post">
                	<input type="hidden" name="id_prodotto" value="<%=prod.getId_prodotto() %>" />
                	<input type="number" name="quantita" value="1" min="1" max="<%= prod.getDisponibilita() %>">
                	<button type="submit">Aggiungi al carrello</button>
                </form>
            </div>
				<%
				}
				%>
				<%
				}
				%>
			</div>
		</div>
	</div>






	<%@ include file="footer.jsp"%>

</body>
</html>