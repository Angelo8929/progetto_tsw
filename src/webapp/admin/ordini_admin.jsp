<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrdineBean" %>
<%@ page import="java.util.List" %>
<%
    List<OrdineBean> tuttiGliOrdini = (List<OrdineBean>) request.getAttribute("tuttiGliOrdini");

    
    String filtroEmail = (String) request.getAttribute("filtroEmail");
    String dataInizio = (String) request.getAttribute("dataInizio");
    String dataFine = (String) request.getAttribute("dataFine");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pannello Admin - Gestione Ordini</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/ordini_admin.css" media="all" />
    
    
    
</head>
<body>
<%@ include file="../header.jsp"  %>

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
                        <td><a
							href="../DettaglioOrdineServlet?id=<%=ordine.getId_ordine()%>"
							>
								#<%=ordine.getId_ordine()%>
						</a></td>
                        <td><%= ordine.getEmail_utente() %></td>
                        <td><%= ordine.getData_ordine() %></td>
                        <td><%= ordine.getNum_prodotti() %> pezzi</td>
                        <td style="font-weight: bold; color: #4CAF50;"><%= ordine.getCosto_totale() %> €</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>