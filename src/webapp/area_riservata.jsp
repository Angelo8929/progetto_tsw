<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean"%>
<%@ page import="model.InfoConsegnaBean"%>
<%@ page import="model.OrdineBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection"%>
<%
// 1. Controllo di sicurezza: l'utente deve essere loggato
UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");
if (utenteLoggato == null) {
	response.sendRedirect(request.getContextPath() + "/login.jsp");
	return;
}

// 2. Recuperiamo il flag isAdmin passato dalla AreaRiservataServlet
Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
if (isAdmin == null) {
	isAdmin = false;
}

// 3. Recuperiamo i messaggi di feedback e le liste
String successMessage = (String) request.getAttribute("successMessage");
String errorMessage = (String) request.getAttribute("errorMessage");

Collection<OrdineBean> listaOrdini = (Collection<OrdineBean>) request.getAttribute("listaOrdini");
List<InfoConsegnaBean> listaIndirizzi = (List<InfoConsegnaBean>) request.getAttribute("listaIndirizzi");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=isAdmin ? "Pannello Admin - AlcoMarket" : "Area Riservata - AlcoMarket"%></title>
<link rel="stylesheet" href="css/home.css" media="all" />
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f4f7f6;
}

.main-container {
	max-width: 900px;
	margin: 30px auto;
	padding: 20px;
}

.box {
	border: 1px solid #ddd;
	padding: 20px;
	margin-bottom: 20px;
	border-radius: 5px;
	background-color: white;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.form-gruppo {
	margin-bottom: 15px;
}

.form-gruppo label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
}

.form-gruppo input, .form-gruppo select, .form-gruppo textarea {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.btn-salva {
	background-color: #008CBA;
	color: white;
	border: none;
	padding: 10px 15px;
	cursor: pointer;
	border-radius: 4px;
	font-weight: bold;
}

.btn-salva:hover {
	background-color: #007399;
}

.card-admin {
	display: block; 
	text-align: center; 
	text-decoration: none; 
	color: white; 
	padding: 25px; 
	border-radius: 6px; 
	font-weight: bold; 
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); 
	transition: transform 0.2s;
}
.card-admin:hover {
	transform: translateY(-2px);
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 15px;
}

th, td {
	padding: 10px;
	border-bottom: 1px solid #ddd;
	text-align: left;
}

th {
	background-color: #eee;
}
</style>
</head>
<body>

	<%@ include file="header.jsp"%>

	<div class="main-container">

		<%-- SEZIONE 1 (Comune): Dati Personali dell'Account --%>
		<div class="box">
			<h1><%=isAdmin ? "Pannello Amministratore" : "Area Riservata Cliente"%></h1>
			<hr style="border: 0; border-top: 1px solid #eee; margin: 15px 0;">
			<p><strong>Nome Utente:</strong> <%=utenteLoggato.getUsername()%></p>
			<p><strong>Email di registrazione:</strong> <%=utenteLoggato.getEmail()%></p>
		</div>

		<%
		// SEZIONE 2 (SOLO ADMIN): Mostra la plancia di comando gestionale
		if (isAdmin) {
		%>
		<div class="box">
			<h2>Pannello di Amministrazione Gestionale</h2>
			<div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; margin-top: 15px;">
				<a href="<%=request.getContextPath()%>/admin/OrdiniAdminServlet" class="card-admin" style="background-color: #008CBA;">
					<div style="font-size: 24px; margin-bottom: 10px;">📦</div>
					Gestione Ordini Totali
				</a> 
				<a href="<%=request.getContextPath()%>/admin/aggiungi_prodotto_admin.jsp" class="card-admin" style="background-color: #4CAF50;">
					<div style="font-size: 24px; margin-bottom: 10px;">➕</div> 
					Aggiungi Nuovo Prodotto
				</a> 
				<a href="<%=request.getContextPath()%>/admin/AdminModificaProdottoServlet" class="card-admin" style="background-color: #f0ad4e;">
					<div style="font-size: 24px; margin-bottom: 10px;">✏️</div>
					Modifica Prodotti Esistenti
				</a>
			</div>
		</div>
		<%
		} // Fine blocco admin
		%>

		<%-- SEZIONE 3 (Comune): Cronologia Ordini Personali (la vedono SIA i clienti SIA l'admin per i suoi acquisti) --%>
		<div class="box">
			<h2>I tuoi Ordini effettuati</h2>
			<%
			if (listaOrdini == null || listaOrdini.isEmpty()) {
			%>
			<p>Non hai ancora effettuato nessun ordine con questo account.</p>
			<%
			} else {
			%>
			<table>
				<thead>
					<tr>
						<th>ID Ordine</th>
						<th>Data Ordine</th>
						<th>Numero Prodotti</th>
						<th>Totale Pagato</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (OrdineBean ordine : listaOrdini) {
					%>
					<tr>
						<td><a href="DettaglioOrdineServlet?id=<%=ordine.getId_ordine()%>" style="color: #008CBA; font-weight: bold; text-decoration: none;">
								#<%=ordine.getId_ordine()%>
						</a></td>
						<td><%=ordine.getData_ordine()%></td>
						<td><%=ordine.getNum_prodotti()%> articoli</td>
						<td style="font-weight: bold;"><%=String.format("%.2f", ordine.getCosto_totale() / 100.0)%> €</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<%
			}
			%>
		</div>

		<%
		// SEZIONE 4 (SOLO CLIENTE NORMALE): Indirizzi di consegna (opzionale: se vuoi puoi lasciarli anche all'admin togliendo l'if)
		if (!isAdmin) {
		%>
			<%-- Elenco Indirizzi Salvati --%>
			<div class="box">
				<h2>I tuoi indirizzi di consegna salvati</h2>
				</div>

			<%-- Form per Aggiungere un nuovo Indirizzo --%>
			<div class="box">
				<h2>Aggiungi un nuovo indirizzo di consegna</h2>
				</div>
		<%
		} // Fine blocco utente normale
		%>

	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>