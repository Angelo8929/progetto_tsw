
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
					<img src="<%=prod.getImgPath()%>"
						alt="<%=prod.getNome_prodotto()%>"
						style="width: 100%; max-height: 150px; object-fit: contain;">
					<h4><%=prod.getNome_prodotto()%></h4>
					<p><%=String.format("%.2f", prod.getPrezzo())%>
					</p>
					<a href="ProdottoServlet?id=<%=prod.getId_prodotto()%>"
						class="btn-dettaglio">Vedi Prodotto</a>
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