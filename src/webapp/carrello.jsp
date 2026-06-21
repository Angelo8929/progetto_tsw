<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="model.ProdottoBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Il tuo Carrello</title>
    <link rel="stylesheet" href="css/carrello.css" media="all" />
    
</head>
<body>
<%@ include file="header.jsp"  %>

    <h1>Il tuo Carrello Shopping</h1>

    <%
        // Recuperiamo i dati passati dalla Servlet tramite gli attributi della Request
        String errorMessage = (String) request.getAttribute("errorMessage");
        Map<ProdottoBean, Integer> elementiCarrello = (Map<ProdottoBean, Integer>) request.getAttribute("elementiCarrello");
        Double prezzoTotale = (Double) request.getAttribute("prezzoTotale");
        
        // Se per qualche motivo l'attributo del prezzo è nullo, lo impostiamo a 0
        if (prezzoTotale == null) {
            prezzoTotale = 0.0;
        }
    %>

    <%-- Gestione Messaggi di Errore --%>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <%-- Se il carrello è vuoto (la mappa è null o non ha elementi) --%>
    <% if (elementiCarrello == null || elementiCarrello.isEmpty()) { %>
        <div class="empty-cart">
            <h2>Il tuo carrello è vuoto.</h2>
            <p>Torna allo <a href="CatalogoServlet">shop</a> per aggiungere prodotti!</p>
        </div>
    <% } else { %>
        
        <%-- Se il carrello ha prodotti, mostriamo la tabella --%>
        <table>
            <thead>
                <tr>
                    <th>Immagine</th>
                    <th>Prodotto</th>
                    <th>Prezzo Unitario</th>
                    <th>Quantità</th>
                    <th>Totale</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Cicliamo sulla LinkedHashMap usando un normale ciclo for-each di Java
                    for (Map.Entry<ProdottoBean, Integer> entry : elementiCarrello.entrySet()) {
                        ProdottoBean prodotto = entry.getKey();
                        int quantita = entry.getValue();
                        double totaleRiga = prodotto.getPrezzo() * quantita;
                %>
                    <tr>
                        <td>
                            <img src="<%= request.getContextPath() %>/<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome_prodotto() %>" width="60">
                        </td>
                        <td><strong><%= prodotto.getNome_prodotto() %></strong></td>
                        <td><%= prodotto.getPrezzo() %> €</td>
                        
                        <%-- FIX CRUCIALE: L'input ora è racchiuso dentro i tag <td> --%>
                        <td>
                            <input type="number" 
                                   class="input-quantita" 
                                   value="<%= quantita %>" 
                                   min="1" 
                                   data-id="<%= prodotto.getId_prodotto() %>" 
                                   style="width: 60px; padding: 5px;" />
                        </td>
                        
                        <td><%= totaleRiga %> €</td>
                        <td>
                            <%-- Form per la rimozione del prodotto --%>
                            <form action="<%= request.getContextPath() %>/CarrelloServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="rimuovi">
                                <input type="hidden" name="id_prodotto" value="<%= prodotto.getId_prodotto() %>">
                                <button type="submit" class="btn-rimuovi">Rimuovi</button>
                            </form>
                        </td>
                    </tr>
                <% 
                    } // Fine del ciclo for
                %>
            </tbody>
        </table>

        <div class="totale-container">
            <h2>Totale Complessivo: <span><%= prezzoTotale %> €</span></h2>
            <br>
            <a href="CheckoutServlet" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; font-weight: bold;">Procedi al Checkout</a>
        </div>
        
    <% } // Fine del blocco else %>
    <%@ include file="footer.jsp" %>
    <script>
    // Questa variabile globale sarà leggibile da qualsiasi file .js esterno caricato dopo
    window.contextPath = "<%= request.getContextPath() %>";
</script>
<script src="js/aggiorna_quantita_carrello.js"></script>
</body>
</html>