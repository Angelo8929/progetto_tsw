<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/home.css" media="all" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>
	<%@ include file="header.jsp"%>
	



	<div class="catalog">
		<div class="filter_sidebar">
		
			<h3>Filtra prodotti</h3> 
			
			<form action="">
			
			<div class="sidebar_categorie">
			<label for="categorie">Categorie</label>
			
			<button>Alcolici</button>
			<button>Superalcolici</button>
			<button>Aanalcolici</button>
			
			
			</div>
			
			<div class="sidebar_prezzo">
			<label>Prezzo</label>
			<input type="number" name="" id="" placeholder="min"/>
			<span>-</span>
			<input type="number" name="" id="" placeholder="max"/>
			</div>
			
			
			
			<div class="sidebar_submit">
			<input type="submit" value="Applica filtri" />
			
			</div>
			
			
			<div class="sidebar_reset">
			<input type="reset" value="Ripristina filtri" />
			
			</div>
			
			
			</form>
			
			
			
		
		
		</div>
		<div class="main">
			<h2>Catalogo</h2>
			<div class="products">
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
				<div class="item"></div>
			
			</div>
		</div>
	</div>






	<%@ include file="footer.jsp"%>

</body>
</html>