<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.InfoConsegnaBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Spedizione e Pagamento</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/checkout.css" media="all" />
    
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

    <%-- Ripilogo del costo --%>
    <div class="box">
        <h2>Riepilogo Ordine</h2>
        <p>Totale da pagare: <strong><%= prezzoTotale %> €</strong></p>
    </div>

    <%-- SEZIONE 1: SELEZIONE INDIRIZZO ESISTENTE --%>
    <div class="box">
        <h2>1. Scegli un indirizzo di spedizione</h2>
        
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

    <%-- SEZIONE 2: FORM PER AGGIUNGERE UN NUOVO INDIRIZZO --%>
    <div class="box form-nuovo-indirizzo">
        <h2>O aggiungi un nuovo indirizzo</h2>
        <form action="<%= request.getContextPath() %>/SalvaIndirizzoServlet" method="post">
            <label>Nome e Cognome Destinatario</label>
            <input type="text" name="destinatario" placeholder="Es. Mario Rossi" required>

            <label>Via / Piazza</label>
            <input type="text" name="via" placeholder="Es. Via Roma" required>

            <label>Numero Civico</label>
            <input type="number" name="civico" placeholder="Es. 12" required>
            
            
            <label for="citta">Città</label>
            <input type="text" name="citta" id="citta" />

            <button type="submit" style="background-color: #008CBA; color: white; border: none; padding: 10px 15px; cursor: pointer; border-radius: 4px;">Salva Indirizzo</button>
        </form>
    </div>

</div>
<%@ include file="footer.jsp"%>
</body>
</html>