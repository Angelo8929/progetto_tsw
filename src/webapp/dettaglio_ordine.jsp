<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrdineBean" %>
<%@ page import="model.ProdottoOrdineBean" %>
<%@ page import="java.util.List" %>
<%
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
    List<ProdottoOrdineBean> dettagli = (List<ProdottoOrdineBean>) request.getAttribute("dettagli");

    if (ordine == null || dettagli == null) {
        response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine #<%= ordine.getId_ordine() %></title>
    <link rel="stylesheet" href="css/dettaglio_ordine.css" media="all" />
    
</head>
<body>
<%@ include file="header.jsp"  %>

<div class="details-container">
    <a href="AreaRiservataServlet" class="back-link">← Torna all'Area Riservata</a>
    
    <h2>Riepilogo Ordine #<%= ordine.getId_ordine() %></h2>
    <p><strong>Data Ordine:</strong> <%= ordine.getData_ordine() %></p>
    <p><strong>Stato:</strong> In Elaborazione</p>
    
    <h3>Prodotti Acquistati</h3>
    <table>
        <thead>
            <tr>
                <th>Prodotto</th>
                <th>Prezzo Unitario</th>
                <th>Quantità</th>
                <th>Totale</th>
            </tr>
        </thead>
        <tbody>
            <% for (ProdottoOrdineBean item : dettagli) { 
                double totaleRiga = item.getPrezzo() * item.getQuantita();
            %>
                <tr>
                    <td><strong><%= item.getNome_prodotto() %></strong></td>
                    <td><%= String.format("%.2f", item.getPrezzo())%> €</td>
                   
                    
                    <td>x<%= item.getQuantita() %></td>
                    <td><%= String.format("%.2f", totaleRiga) %> €</td>
                </tr>
            <% } %>
        </tbody>
    </table>
    
    <h3 style="text-align: right; margin-top: 25px;">
        Totale Complessivo: <%= String.format("%.2f", ordine.getCosto_totale() / 100.0) %> €
    </h3>
</div>
<div style="margin-top: 20px; display: flex; gap: 15px;">
    
    <a href="<%= request.getContextPath() %>/FatturaServlet?id=<%= ordine.getId_ordine() %>" 
       style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; font-weight: bold; display: inline-block;">
       📄 Scarica Fattura PDF
    </a>
    
    <a href="<%= request.getContextPath() %>/AreaRiservataServlet" 
       style="background-color: #999; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; font-weight: bold; display: inline-block;">
       Torna Indietro
    </a>
</div>
<%@ include file="footer.jsp" %>

</body>
</html>