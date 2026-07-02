<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.InfoConsegnaBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Spedizione e Pagamento</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/checkout.css" media="all" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/area_riservata.css" media="all" />
    
</head>
<body>
<%@ include file="header.jsp"%>
<div class="container">

    <h1>Checkout</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        List<InfoConsegnaBean> listaIndirizzi = (List<InfoConsegnaBean>) request.getAttribute("listaIndirizzi");
        Double prezzoTotale = (Double) request.getAttribute("prezzoTotale");
    %>

    <% if (errorMessage != null) { %>
        <p class="error-message"><%= errorMessage %></p>
    <% } %>

    
    <div class="box">
        <h2>Riepilogo Ordine</h2>
        <p>Totale da pagare: <strong><%= String.format("%.2f",prezzoTotale) %> €</strong></p>
    </div>

    
    <div class="box">
        <h2>Scegli un indirizzo di spedizione</h2>
        
        <% if (listaIndirizzi == null || listaIndirizzi.isEmpty()) { %>
            <p>Non hai ancora indirizzi salvati. Compila il modulo sottostante per aggiungerne uno.</p>
        <% } else { %>
            <form action="<%= request.getContextPath() %>/CompletaOrdineServlet" method="post" id="formCheckout">
                
                <% for (InfoConsegnaBean indirizzo : listaIndirizzi) { %>
                    <div class="indirizzo-item">
                        <input type="radio" id="ind_<%= indirizzo.getId_consegna() %>" name="id_consegna" value="<%= indirizzo.getId_consegna() %>" required>
                        <label for="ind_<%= indirizzo.getId_consegna() %>">
                            <strong><%= indirizzo.getDestinatario() %></strong><br>
                            <%= indirizzo.getVia() %>, Civico <%= indirizzo.getCivico() %>
                        </label>
                    </div>
                <% } %>
                
                <br>
                <button type="submit" class="btn-conferma">Completa l'Ordine e Paga</button>
            </form>
        <% } %>
    </div>

    
    <div class="box form-nuovo-indirizzo">
        <h2>O aggiungi un nuovo indirizzo</h2>
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
<script src="<%=request.getContextPath() %>/js/indirizzi.js"></script>
<%@ include file="footer.jsp"%>
</body>
</html>