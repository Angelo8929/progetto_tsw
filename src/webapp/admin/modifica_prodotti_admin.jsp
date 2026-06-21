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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/modifica_prodotti_admin.css" media="all" />

</head>
<body>
<%@ include file="../header.jsp"  %>

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
<%@ include file="../footer.jsp" %>
</body>
</html>