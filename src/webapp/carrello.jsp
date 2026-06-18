<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="model.ProdottoBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Il tuo Carrello</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f4f4f4; }
        .totale-container { text-align: right; margin-top: 20px; }
        .btn-rimuovi { background-color: #ff4d4d; color: white; border: none; padding: 6px 12px; cursor: pointer; border-radius: 4px; }
        .btn-rimuovi:hover { background-color: #ff1a1a; }
        .error-message { color: red; font-weight: bold; }
        .empty-cart { text-align: center; margin-top: 50px; color: #777; }
    </style>
</head>
<body>

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
            <p>Torna allo <a href="catalogo.jsp">shop</a> per aggiungere prodotti!</p>
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
                        <td><%= quantita %></td>
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

</body>
</html>