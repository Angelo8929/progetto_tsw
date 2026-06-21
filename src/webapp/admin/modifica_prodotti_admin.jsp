<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ProdottoBean"%>
<%@ page import="java.util.List"%>
<%
// Recuperiamo la lista globale (sempre presente)
List<ProdottoBean> listaProdotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");

// Recuperiamo il prodotto da modificare (presente solo se l'admin ha cliccato su "Modifica")
ProdottoBean prodSelezionato = (ProdottoBean) request.getAttribute("prodottoSelezionato");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestione e Modifica Prodotti - AlcoMarket</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 30px;
	background-color: #f4f7f6;
}

.container {
	max-width: 1000px;
	margin: 0 auto;
}

.box {
	background: white;
	padding: 25px;
	border-radius: 8px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
	margin-bottom: 25px;
}

.grid-form {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 15px;
}

.form-gruppo {
	margin-bottom: 15px;
	transition: all 0.3s ease;
}

.form-gruppo label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
	font-size: 14px;
}

.form-gruppo input, .form-gruppo select {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.btn-salva {
	background-color: #f0ad4e;
	color: white;
	border: none;
	padding: 10px 20px;
	cursor: pointer;
	border-radius: 4px;
	font-weight: bold;
	font-size: 15px;
}

.btn-salva:hover {
	background-color: #ec971f;
}

.btn-annulla {
	background-color: #999;
	color: white;
	text-decoration: none;
	padding: 10px 15px;
	border-radius: 4px;
	font-size: 15px;
	margin-left: 10px;
	display: inline-block;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 15px;
}

th, td {
	padding: 12px;
	border-bottom: 1px solid #ddd;
	text-align: left;
}

th {
	background-color: #eee;
}

.btn-azione {
	background-color: #008CBA;
	color: white;
	padding: 5px 10px;
	text-decoration: none;
	border-radius: 4px;
	font-weight: bold;
	font-size: 13px;
}

.btn-azione:hover {
	background-color: #007399;
}

.btn-cancella {
	background-color: #d9534f;
	color: white;
	padding: 5px 10px;
	text-decoration: none;
	border-radius: 4px;
	font-weight: bold;
	font-size: 13px;
	margin-left: 5px;
}

.btn-cancella:hover {
	background-color: #c9302c;
}

.back-link {
	display: inline-block;
	margin-bottom: 20px;
	color: #008CBA;
	text-decoration: none;
	font-weight: bold;
}

.nascosto {
	display: none !important;
}
</style>
</head>
<body>

	<div class="container">
		<a href="<%=request.getContextPath()%>/AreaRiservataServlet" class="back-link">← Torna al Pannello Admin</a>

		<h1>Gestione Catalogo Prodotti</h1>

		<%-- SEZIONE FORM DI MODIFICA --%>
		<% if (prodSelezionato != null) { %>
		<div class="box" style="border-left: 5px solid #f0ad4e;">
			<h2>
				Stai modificando: <span style="color: #ec971f;"><%=prodSelezionato.getNome_prodotto()%></span> (ID #<%=prodSelezionato.getId_prodotto()%>)
			</h2>

			<form action="<%=request.getContextPath()%>/admin/AdminModificaProdottoServlet" method="post">
				<input type="hidden" name="id_prodotto" value="<%=prodSelezionato.getId_prodotto()%>">

				<div class="grid-form">
					<div class="form-gruppo">
						<label>Nome Prodotto</label> 
						<input type="text" name="nome" value="<%=prodSelezionato.getNome_prodotto()%>" required>
					</div>

					<div class="form-gruppo">
						<label>Prezzo al pubblico (€)</label> 
						<input type="number" name="prezzo" step="0.01" min="0" value="<%=prodSelezionato.getPrezzo()%>" required>
					</div>

					<div class="form-gruppo">
						<label>Categoria</label> 
						<select id="categoriaSelect" name="categoria" onchange="gestisciCampiDinamici()">
							<option value="Alcolici" <%="Alcolici".equals(prodSelezionato.getCategoria()) ? "selected" : ""%>>Alcolici</option>
							<option value="Superalcolici" <%="Superalcolici".equals(prodSelezionato.getCategoria()) ? "selected" : ""%>>Superalcolici</option>
							<option value="Analcolici" <%="Analcolici".equals(prodSelezionato.getCategoria()) ? "selected" : ""%>>Analcolici</option>
						</select>
					</div>

					<div class="form-gruppo">
						<label>Nome File Immagine (imgPath)</label> 
						<input type="text" name="imgPath" value="<%=prodSelezionato.getImgPath() != null ? prodSelezionato.getImgPath() : ""%>">
					</div>

					<div class="form-gruppo" id="boxGradazione">
						<label>Gradazione Alcolica (% Vol)</label> 
						<input type="number" name="percAlcol" step="0.1" min="0" value="<%=prodSelezionato.getPerc_alcol()%>">
					</div>

					<div class="form-gruppo" id="boxEffervescenza">
						<label>Effervescenza</label> 
						<input type="text" name="effervescenza" placeholder="Es. Fermo, Frizzante, Vivace..." value="<%=prodSelezionato.getEffervescenza() != null ? prodSelezionato.getEffervescenza() : ""%>">
					</div>

					<div class="form-gruppo" id="boxDescrizione">
						<label>Descrizione</label> 
						<input type="text" name="descrizione" placeholder="Inserisci una descrizione" value="<%=prodSelezionato.getDescrizione() != null ? prodSelezionato.getDescrizione() : ""%>">
					</div>

					<div class="form-gruppo" id="boxIva">
						<label>IVA (%)</label> 
						<input type="number" name="iva" min="0" value="<%=prodSelezionato.getIva()%>" required>
					</div>

					<div class="form-gruppo">
						<label>Quantità in Magazzino (Disponibilità)</label> 
						<input type="number" name="disponibilita" min="0" value="<%=prodSelezionato.getDisponibilita()%>" required>
					</div>
				</div>

				<div style="margin-top: 20px;">
					<button type="submit" class="btn-salva">Salva Modifiche</button>
					<a href="<%=request.getContextPath()%>/admin/AdminModificaProdottoServlet" class="btn-annulla">Annulla</a>
				</div>
			</form>
		</div>
		<% } %>

		<%-- SEZIONE TABELLA INVENTARIO --%>
		<div class="box">
			<h2>Inventario Prodotti</h2>
			<p style="color: #666; font-size: 14px;">Seleziona il prodotto che desideri modificare cliccando sul rispettivo bottone d'azione.</p>

			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome Articolo</th>
						<th>Categoria</th>
						<th>Prezzo Corrente</th>
						<th>Disponibilità</th>
						<th>Azioni</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (listaProdotti != null && !listaProdotti.isEmpty()) {
						for (ProdottoBean prod : listaProdotti) {
							boolean isCorrente = (prodSelezionato != null && prodSelezionato.getId_prodotto() == prod.getId_prodotto());
					%>
					<tr <%=isCorrente ? "style='background-color: #fff9e6; font-weight: bold;'" : ""%>>
						<td>#<%=prod.getId_prodotto()%></td>
						<td><%=prod.getNome_prodotto()%></td>
						<td>
							<span style="font-size: 13px; padding: 3px 8px; background: #eee; border-radius: 12px;"><%=prod.getCategoria()%></span>
						</td>
					
						<td><%=String.format("%.2f", prod.getPrezzo())%> €</td>
						
						<td>
							<% if (prod.getDisponibilita() == 0) { %> 
								<span style="color: red; font-weight: bold;">Esaurito</span> 
							<% } else { %>
								<%=prod.getDisponibilita()%> pz 
							<% } %>
						</td>

						<td>
							<a href="<%=request.getContextPath()%>/admin/AdminModificaProdottoServlet?id=<%=prod.getId_prodotto()%>" class="btn-azione" <%=isCorrente ? "style='background-color: #999;'" : ""%>> 
								<%=isCorrente ? "In Modifica..." : "✏️ Seleziona"%>
							</a> 
							<a href="<%=request.getContextPath()%>/admin/AdminCancellaProdottoServlet?id=<%=prod.getId_prodotto()%>" class="btn-cancella" onclick="return confirm('Sicuro di voler cancellare questo prodotto?');">
								🗑️ Cancella
							</a>
						</td>
					</tr>
					<%
						}
					} else {
					%>
					<tr>
						<td colspan="7">Nessun prodotto nel database.</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<script>
		// FIX: Uniformato il nome della funzione cercato dall'onchange
		function gestisciCampiDinamici() {
			var selectCategoria = document.getElementById("categoriaSelect");
			if (!selectCategoria)
				return; 

			var categoria = selectCategoria.value;
			var boxGradazione = document.getElementById("boxGradazione");
			var boxEffervescenza = document.getElementById("boxEffervescenza");

			if (categoria === "Analcolici") {
				boxGradazione.classList.add("nascosto");
				boxEffervescenza.classList.remove("nascosto");
			} else {
				boxGradazione.classList.remove("nascosto");
				boxEffervescenza.classList.add("nascosto");
			}
		}

		window.onload = function() {
			gestisciCampiDinamici();
		};
	</script>

</body>
</html>