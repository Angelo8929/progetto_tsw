document.getElementById("cerca").addEventListener("input", function() {
    let query = this.value.trim();
    let boxSuggerimenti = document.getElementById("suggerimenti");

    if (query.length < 2) { 
        boxSuggerimenti.innerHTML = "";
        boxSuggerimenti.style.display = "none";
        return; 
    }

    // Chiamata AJAX usando Fetch API
    fetch(window.contextPath + "/RicercaSuggerimentiServlet?term=" + encodeURIComponent(query))
        .then(response => response.json())
        .then(prodotti => {
            boxSuggerimenti.innerHTML = "";
            if (prodotti.length > 0) {
                prodotti.forEach(prod => {
                    let elemento = document.createElement("a");
                    // Punta alla tua servlet di dettaglio o pagina prodotto
                    elemento.href = "ProdottoServlet?id=" + prod.id;
                    elemento.textContent = prod.nome;
                    boxSuggerimenti.appendChild(elemento);
                });
                boxSuggerimenti.style.display = "block";
            } else {
                boxSuggerimenti.style.display = "none";
            }
        })
        .catch(error => console.error("Errore AJAX:", error));
});

// Chiude i suggerimenti se l'utente clicca fuori dalla barra di ricerca
document.addEventListener("click", function(e) {
    if (e.target.id !== "cerca") {
        document.getElementById("suggerimenti").style.display = "none";
    }
});