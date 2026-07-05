<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean"%>
<%@ page import="model.InfoConsegnaBean"%>
<%@ page import="model.OrdineBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection"%>
<%

UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");
if (utenteLoggato == null) {
	response.sendRedirect(request.getContextPath() + "/login.jsp");
	return;
}


Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
if (isAdmin == null) {
	isAdmin = false;
}


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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/area_riservata.css" media="all" />

</head>
<body>

	<%@ include file="header.jsp"%>

	<div class="main-container">

		
		<%
		if (errorMessage != null) {
		%>
		<div class="alert-error"><%=errorMessage%></div>
		<%
		}
		%>
		<%
		if (successMessage != null) {
		%>
		<div class="alert-success"><%=successMessage%></div>
		<%
		}
		%>

		
		<div class="box">
			<h1><%=isAdmin ? "Pannello Amministratore" : "Area Riservata Cliente"%></h1>
			<hr >
			<p>
				<strong>Nome Utente:</strong>
				<%=utenteLoggato.getUsername()%></p>
			<p>
				<strong>Email di registrazione:</strong>
				<%=utenteLoggato.getEmail()%></p>
		</div>

		<%
		
		if (isAdmin) {
		%>
		<div class="box">
			<h2>Pannello di Amministrazione Gestionale</h2>
			<div>
				<a href="<%=request.getContextPath()%>/admin/OrdiniAdminServlet"
					class="card-admin" >
					<div>📦</div>
					Gestione Ordini Totali
				</a> <a
					href="<%=request.getContextPath()%>/admin/aggiungi_prodotto_admin.jsp"
					class="card-admin" >
					<div>➕</div> Aggiungi
					Nuovo Prodotto
				</a> <a
					href="<%=request.getContextPath()%>/admin/AdminModificaProdottoServlet"
					class="card-admin" >
					<div >✏️</div>
					Modifica Prodotti Esistenti
				</a>
			</div>
		</div>
		<%
		} 
		%>

		
		<div class="box box-ordini">
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
						<td><a
							href="DettaglioOrdineServlet?id=<%=ordine.getId_ordine()%>"
							>
								#<%=ordine.getId_ordine()%>
						</a></td>
						<td><%=ordine.getData_ordine()%></td>
						<td><%=ordine.getNum_prodotti()%> articoli</td>
						<td ><%=ordine.getCosto_totale()%>
							€</td>
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

		
		<div class="box">
			<h2>I tuoi indirizzi di consegna salvati</h2>
			<%
			if (listaIndirizzi == null || listaIndirizzi.isEmpty()) {
			%>
			<p>Non hai ancora salvato nessun indirizzo di consegna.</p>
			<%
			} else {
			%>
			<table>
				<thead>
					<tr>
						<th>Nome Destinatario</th>
						<th>Via</th>
						<th>Civico</th>
						<th>Città</th>
						
					</tr>
				</thead>
				<tbody>
					<%
					for (InfoConsegnaBean indirizzo : listaIndirizzi) {
					%>
					<tr>
						<td><%=indirizzo.getDestinatario()%></td>
						<td><%=indirizzo.getVia()%></td>
						<td><%=indirizzo.getCivico()%></td>
						<td><%=indirizzo.getCitta()%></td>
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

		
		<div class="box">
			<h2>Aggiungi un nuovo indirizzo di consegna</h2>

			<form action="SalvaIndirizzoServlet" method="post">
				<div class="form-gruppo">
					<label for="nome_cognome">Nome e Cognome Destinatario</label> <input
						type="text" id="destinatario" name="destinatario"
						placeholder="Es. Mario Rossi" required />
				</div>

				<div class="form-gruppo">
					<label for="via">Via</label> <input type="text" id="indirizzo_via"
						name="via" placeholder="Es. Via Roma 12" required />
				</div>


				<div class="form-gruppo">
					<label for="civico">Civico</label> <input type="text" id="civico"
						name="civico" placeholder="104" required />
				</div>

				<div class="form-gruppo">
					<label for="citta">Città</label> <input type="text" id="citta"
						name="citta" placeholder="teverola" required />
				</div>



				<div style="margin-top: 20px; text-align: right;">
					<input type="submit" class="btn-salva" value="Salva Indirizzo" />
				</div>
			</form>
		</div>

	</div>

	<%@ include file="footer.jsp"%>
	
	<script src="<%=request.getContextPath() %>/js/indirizzi.js"></script>

</body>
</html>