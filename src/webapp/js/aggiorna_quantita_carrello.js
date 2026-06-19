document.querySelectorAll(".input-quantita").forEach(input => {
    input.addEventListener("change", function() {
        let nuovaQuantita = parseInt(this.value);
        let idProdotto = this.getAttribute("data-id");

        // Se l'input non è un numero o è minore di 1, forziamo a 1 sia sul client che nella richiesta
        if (isNaN(nuovaQuantita) || nuovaQuantita < 1) {
            this.value = 1;
            nuovaQuantita = 1;
        }

        let params = new URLSearchParams();
        params.append("idProdotto", idProdotto);
        params.append("quantita", nuovaQuantita);

        fetch(window.contextPath + "/AggiornaQuantitaCarrelloServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                location.reload(); 
            } else {
                alert(data.error);
            }
        })
        .catch(error => console.error("Errore aggiornamento carrello:", error));
    });
});