<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Prodotto - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f7f6; }
        .form-container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
        .form-gruppo { margin-bottom: 15px; }
        .form-gruppo label { display: block; font-weight: bold; margin-bottom: 5px; }
        .form-gruppo input, .form-gruppo select { width: 100%; padding: 8px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
        .btn-salva { background-color: #4CAF50; color: white; border: none; padding: 10px 15px; cursor: pointer; border-radius: 4px; font-weight: bold; }
        .back-link { display: inline-block; margin-bottom: 20px; color: #008CBA; text-decoration: none; }
    </style>
</head>
<body>

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

</body>
</html>