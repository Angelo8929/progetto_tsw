<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrdineBean" %>
<%@ page import="java.util.List" %>
<%
    List<OrdineBean> tuttiGliOrdini = (List<OrdineBean>) request.getAttribute("tuttiGliOrdini");

    // Recuperiamo i vecchi valori per lasciarli scritti nei campi di testo
    String filtroEmail = (String) request.getAttribute("filtroEmail");
    String dataInizio = (String) request.getAttribute("dataInizio");
    String dataFine = (String) request.getAttribute("dataFine");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pannello Admin - Gestione Ordini</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f7f6; }
        .container { max-width: 950px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
        .box-filtri { background: #f9f9f9; padding: 15px; border: 1px solid #e0e0e0; border-radius: 6px; margin-bottom: 20px; }
        .form-filtri { display: flex; flex-wrap: wrap; gap: 15px; align-items: flex-end; }
        .filtro-gruppo { display: flex; flex-direction: column; }
        .filtro-gruppo label { font-size: 13px; font-weight: bold; margin-bottom: 5px; color: #333; }
        .filtro-gruppo input { padding: 6px 10px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-filtra { background-color: #008CBA; color: white; border: none; padding: 7px 15px; cursor: pointer; border-radius: 4px; font-weight: bold; height: 32px; }
        .btn-reset { background-color: #e7e7e7; color: black; text-decoration: none; padding: 6px 12px; border-radius: 4px; font-size: 14px; text-align: center; height: 18px; line-height: 18px; border: 1px solid #ccc; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 10px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #eee; }
        .back-link { display: inline-block; margin-bottom: 20px; color: #008CBA; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <a href="<%= request.getContextPath() %>/AreaRiservataServlet" class="back-link">← Torna al Pannello Admin</a>
    <h2>Cronologia Ordini Globale (AlcoMarket)</h2>
    
    <%-- SEZIONE FILTRI DI RICERCA --%>
    <div class="box-filtri">
        <form action="<%= request.getContextPath() %>/admin/OrdiniAdminServlet" method="get" class="form-filtri">
            
            <div class="filtro-gruppo" style="flex: 1; min-width: 200px;">
                <label for="filtroEmail">Email Cliente</label>
                <input type="text" id="filtroEmail" name="filtroEmail" placeholder="Es. utente@email.it" value="<%= filtroEmail %>">
            </div>
            
            <div class="filtro-gruppo">
                <label for="dataInizio">Da Data</label>
                <input type="date" id="dataInizio" name="dataInizio" value="<%= dataInizio %>">
            </div>
            
            <div class="filtro-gruppo">
                <label for="dataFine">A Data</label>
                <input type="date" id="dataFine" name="dataFine" value="<%= dataFine %>">
            </div>
            
            <button type="submit" class="btn-filtra">Filtra 🔍</button>
            <a href="<%= request.getContextPath() %>/admin/OrdiniAdminServlet" class="btn-reset">Reset</a>
        </form>
    </div>

    <%-- TABELLA RISULTATI --%>
    <% if (tuttiGliOrdini == null || tuttiGliOrdini.isEmpty()) { %>
        <p style="color: #ff0000; font-weight: bold; margin-top: 20px;">Nessun ordine corrisponde ai criteri di ricerca impostati.</p>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>ID Ordine</th>
                    <th>Email Cliente</th>
                    <th>Data Acquisto</th>
                    <th>Articoli</th>
                    <th>Totale Incassato</th>
                </tr>
            </thead>
            <tbody>
                <% for (OrdineBean ordine : tuttiGliOrdini) { %>
                    <tr>
                        <td><strong>#<%= ordine.getId_ordine() %></strong></td>
                        <td><%= ordine.getEmail_utente() %></td>
                        <td><%= ordine.getData_ordine() %></td>
                        <td><%= ordine.getNum_prodotti() %> pezzi</td>
                        <td style="font-weight: bold; color: #4CAF50;"><%= String.format("%.2f", ordine.getCosto_totale() / 100.0) %> €</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>
</div>

</body>
</html>