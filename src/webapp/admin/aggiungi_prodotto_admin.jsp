<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Prodotto - Admin</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/aggiungi_prodotto_admin.css" media="all" />
    
</head>
<body>

<%@ include file="../header.jsp"  %>

<div class="form-container">
    <a href="<%= request.getContextPath() %>/AreaRiservataServlet" class="back-link">← Torna al Pannello Admin</a>
    <h2>Inserisci un nuovo Prodotto nel Catalogo</h2>
    
    <form action="<%= request.getContextPath() %>/admin/AdminUploadProdottoServlet" method="post">
        <div class="form-gruppo">
            <label>Nome Prodotto</label>
            <input type="text" name="nome" required>
        </div>
        <div class="form-gruppo">
            <label>Categoria Principale</label>
            <select name="categoria">
                <option value="Alcolici">Alcolici</option>
                <option value="Superalcolici">Superalcolici</option>
                <option value="Analcolici">Analcolici</option>
            </select>
        </div>
        <div class="form-gruppo">
            <label>Sottocategoria / Stile</label>
            <input type="text" name="sottocategoria" placeholder="Es. Rum, Birra IPA">
        </div>
        <div class="form-gruppo">
            <label>Prezzo al pubblico (€)</label>
            <input type="number" name="prezzo" step="0.01" min="0.01" required>
        </div>
        <div class="form-gruppo">
            <label>Gradazione Alcolica (% Vol)</label>
            <input type="number" name="percAlcol" step="0.1" min="0" max="100"value="0.0">
        </div>
        <div class="form-gruppo">
            <label>Nome file Immagine</label>
            <input type="text" name="imgPath" placeholder="Es. vodka.png" required>
        </div>
        <div class="form-gruppo">
            <label>Descrizione</label>
            <input type="text" name="descrizione" placeholder="prodotto bello" required>
        </div>
        <div class="form-gruppo">
            <label>Iva</label>
            <input type="text" name="iva" placeholder="22" required>
        </div>
        <div class="form-gruppo">
        
        <label for="disponibilita">Disponibilita</label>
        <input type="number" name="disponibilita" min="0" value="0">
        </div>
        
        
        
        <button type="submit" class="btn-salva">Carica Prodotto</button>
    </form>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>