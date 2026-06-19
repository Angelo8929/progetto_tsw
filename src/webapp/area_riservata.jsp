<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean"%>
<%@ page import="model.InfoConsegnaBean"%>
<%@ page import="java.util.List"%>
<%@ page import="model.OrdineBean"%>
<%@ page import="java.util.Collection"%>
<%
// Recuperiamo l'utente loggato dalla sessione
UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

if (utenteLoggato == null) {
	response.sendRedirect(request.getContextPath() + "/login.jsp");
	return;
}

// RECUPERO DATI DALLA SERVLET
List<InfoConsegnaBean> listaIndirizzi = (List<InfoConsegnaBean>) request.getAttribute("listaIndirizzi");
String successMessage = (String) request.getAttribute("successMessage");
String errorMessage = (String) request.getAttribute("errorMessage");

// Recuperiamo la lista degli ordini passata dalla servlet
Collection<OrdineBean> listaOrdini = (Collection<OrdineBean>) request.getAttribute("listaOrdini");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Area Riservata</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
}

.main-container {
	max-width: 800px;
	margin: 30px auto;
	padding: 20px;
}

.box {
	border: 1px solid #ddd;
	padding: 20px;
	margin-bottom: 20px;
	border-radius: 5px;
	background-color: #f9f9f9;
}

.form-gruppo {
	margin-bottom: 15px;
}

.form-gruppo label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
}

.form-gruppo input {
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

.msg-success {
	color: green;
	font-weight: bold;
	margin-bottom: 15px;
}

.msg-error {
	color: red;
	font-weight: bold;
	margin-bottom: 15px;
}

.indirizzo-item {
	border-bottom: 1px solid #eee;
	padding: 10px 0;
}

.indirizzo-item:last-child {
	border-bottom: none;
}
</style>
</head>
<body>

	<%@ include file="header.jsp"%>

	<div class="main-container">

		<%-- Messaggi di feedback --%>
		<%
		if (successMessage != null) {
		%>
		<div class="msg-success"><%=successMessage%></div>
		<%
		}
		%>
		<%
		if (errorMessage != null) {
		%>
		<div class="msg-error"><%=errorMessage%></div>
		<%
		}
		%>

		<%-- Sezione 1: Benvenuto e Dati Personali --%>
		<div class="box">
			<h1>
				Benvenuto,
				<%=utenteLoggato.getUsername()%></h1>
			<hr>
			<h3>I tuoi Dati:</h3>
			<p>
				<strong>Nome Utente:</strong>
				<%=utenteLoggato.getUsername()%></p>
			<p>
				<strong>Email di registrazione:</strong>
				<%=utenteLoggato.getEmail()%></p>
		</div>

		<div class="box">
			<h2>I tuoi Ordini</h2>
			<%
			if (listaOrdini == null || listaOrdini.isEmpty()) {
			%>
			<p>Non hai ancora effettuato nessun ordine.</p>
			<%
			} else {
			%>
			<table
				style="width: 100%; border-collapse: collapse; margin-top: 15px;">
				<thead>
					<tr style="background-color: #eee; text-align: left;">
						<th style="padding: 10px; border-bottom: 2px solid #ddd;">ID
							Ordine</th>
						<th style="padding: 10px; border-bottom: 2px solid #ddd;">Data</th>
						<th style="padding: 10px; border-bottom: 2px solid #ddd;">Prodotti</th>
						<th style="padding: 10px; border-bottom: 2px solid #ddd;">Totale</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (OrdineBean ordine : listaOrdini) {
					%>
					<tr style="border-bottom: 1px solid #eee;">
						<td style="padding: 10px;"><a
							href="DettaglioOrdineServlet?id=<%=ordine.getId_ordine()%>"
							style="color: #008CBA; font-weight: bold; text-decoration: none;">
								#<%=ordine.getId_ordine()%>
						</a></td>
						<td style="padding: 10px;"><%=ordine.getData_ordine()%></td>
						<td style="padding: 10px;"><%=ordine.getNum_prodotti()%>
							articoli</td>
						<td style="padding: 10px; font-weight: bold;"><%=String.format("%.2f", ordine.getCosto_totale() / 100.0)%>
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

		<%-- NUOVA SEZIONE: Visualizzazione Indirizzi Esistenti --%>
		<div class="box">
			<h2>I tuoi indirizzi salvati</h2>
			<%
			if (listaIndirizzi == null || listaIndirizzi.isEmpty()) {
			%>
			<p>Non hai ancora salvato alcun indirizzo di consegna.</p>
			<%
			} else {
			%>
			<%
			for (InfoConsegnaBean indirizzo : listaIndirizzi) {
			%>
			<div class="indirizzo-item">
				<strong><%=indirizzo.getDestinatario()%></strong> -
				<%=indirizzo.getVia()%>,
				<%=indirizzo.getCivico()%>
			</div>
			<%
			}
			%>
			<%
			}
			%>
		</div>

		<%-- Sezione 2: Form Aggiunta Nuovo Indirizzo --%>
		<div class="box">
			<h2>Aggiungi un nuovo indirizzo di consegna</h2>
			<p style="color: #666; font-size: 14px;">Inserisci un indirizzo
				valido da poter selezionare durante i tuoi prossimi acquisti.</p>

			<form action="<%=request.getContextPath()%>/SalvaIndirizzoServlet"
				method="post">

				<div class="form-gruppo">
					<label for="destinatario">Nome e Cognome Destinatario</label> <input
						type="text" id="destinatario" name="destinatario"
						placeholder="Es. Mario Rossi" required>
				</div>

				<div class="form-gruppo">
					<label for="via">Via / Piazza</label> <input type="text" id="via"
						name="via" placeholder="Es. Via Garibaldi" required>
				</div>

				<div class="form-gruppo">
					<label for="via">Citta</label> <input type="text" id="citta"
						name="citta" placeholder="Es. Roma" required>
				</div>

				<div class="form-gruppo">
					<label for="civico">Numero Civico</label> <input type="number"
						id="civico" name="civico" placeholder="Es. 7" min="1" required>
				</div>

				<button type="submit" class="btn-salva">Salva Indirizzo</button>
			</form>
		</div>

	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>