document.addEventListener("DOMContentLoaded", () => {
    // Intercettiamo il form specifico dell'indirizzo
    const form = document.querySelector("form[action='SalvaIndirizzoServlet']");
    if (!form) return; // Sicurezza nel caso in cui lo script venga caricato altrove

    const destinatario = document.getElementById("destinatario");
    const via = document.getElementById("indirizzo_via");
    const civico = document.getElementById("civico");
    const citta = document.getElementById("citta");
    
    // Pulsante di sottomissione
    const submitBtn = form.querySelector("input[type='submit']");

    // Funzione helper per creare i box di errore sotto i campi
    const createErrorElement = (inputElement) => {
        const errorSpan = document.createElement("span");
        errorSpan.className = "error-message";
        errorSpan.style.color = "red";
        errorSpan.style.fontSize = "0.85em";
        errorSpan.style.display = "none"; 
        errorSpan.style.marginTop = "5px";
        inputElement.parentNode.appendChild(errorSpan);
        return errorSpan;
    };

    const destinatarioError = createErrorElement(destinatario);
    const viaError = createErrorElement(via);
    const civicoError = createErrorElement(civico);
    const cittaError = createErrorElement(citta);

    // --- REGEX DI VALIDAZIONE ---
    // Destinatario: Solo lettere e spazi, minimo 4 caratteri (es. "Luca")
    const destinatarioRegex = /^[a-zA-Z\s]{4,50}$/;
    // Via: Lettere, spazi ed eventualmente numeri, minimo 4 caratteri (es. "Via Roma")
    const viaRegex = /^[a-zA-Z0-9\s'.,-]{4,100}$/;
    // Civico: Numeri seguiti opzionalmente da lettere (es. "104", "12/A", "23B")
    const civicoRegex = /^[0-9]{1,5}[a-zA-Z\/]{0,4}$/;
    // Città: Solo lettere e spazi, minimo 2 caratteri (es. "Napoli", "Teverola")
    const cittaRegex = /^[a-zA-Z\s'.-]{2,50}$/;

    // STATO DEL FORM
    let isDestinatarioValid = false;
    let isViaValid = false;
    let isCivicoValid = false;
    let isCittaValid = false;

    // --- FUNZIONE DI ABILITAZIONE DINAMICA DEL BOTTONE ---
    const validaFormGenerale = () => {
        if (isDestinatarioValid && isViaValid && isCivicoValid && isCittaValid) {
            submitBtn.disabled = false;
            submitBtn.style.opacity = "1";
            submitBtn.style.cursor = "pointer";
        } else {
            submitBtn.disabled = true;
            submitBtn.style.opacity = "0.5"; 
            submitBtn.style.cursor = "not-allowed";
        }
    };

    // Stato iniziale: disabilitato
    validaFormGenerale();

    // Gestione focus estetica generica
    const aggiungiGestioneFocusGenerica = (inputElement, errorElement) => {
        inputElement.addEventListener("focus", () => {
            if (inputElement.style.borderColor !== "green" && inputElement.style.borderColor !== "red") {
                inputElement.style.borderColor = "#008CBA";
            }
        });
        inputElement.addEventListener("blur", () => {
            if (inputElement.value.trim() === "") {
                inputElement.style.borderColor = "#ccc";
                errorElement.style.display = "none";
            }
        });
    };

    aggiungiGestioneFocusGenerica(destinatario, destinatarioError);
    aggiungiGestioneFocusGenerica(via, viaError);
    aggiungiGestioneFocusGenerica(civico, civicoError);
    aggiungiGestioneFocusGenerica(citta, cittaError);

    // --- 1. DESTINATARIO (INPUT) ---
    destinatario.addEventListener("input", () => {
        const val = destinatario.value.trim();
        if (val === "") {
            destinatarioError.style.display = "none";
            isDestinatarioValid = false;
        } else if (!destinatarioRegex.test(val)) {
            destinatarioError.textContent = "Inserisci un nome e cognome valido (solo lettere, min 4 caratteri)";
            destinatarioError.style.display = "block";
            destinatario.style.borderColor = "red";
            isDestinatarioValid = false;
        } else {
            destinatarioError.style.display = "none";
            destinatario.style.borderColor = "green";
            isDestinatarioValid = true;
        }
        validaFormGenerale();
    });

    // --- 2. VIA (INPUT) ---
    via.addEventListener("input", () => {
        const val = via.value.trim();
        if (val === "") {
            viaError.style.display = "none";
            isViaValid = false;
        } else if (!viaRegex.test(val)) {
            viaError.textContent = "Inserisci una via o piazza valida (min 4 caratteri)";
            viaError.style.display = "block";
            via.style.borderColor = "red";
            isViaValid = false;
        } else {
            viaError.style.display = "none";
            via.style.borderColor = "green";
            isViaValid = true;
        }
        validaFormGenerale();
    });

    // --- 3. CIVICO (INPUT) ---
    civico.addEventListener("input", () => {
        const val = civico.value.trim();
        if (val === "") {
            civicoError.style.display = "none";
            isCivicoValid = false;
        } else if (!civicoRegex.test(val)) {
            civicoError.textContent = "Numero civico non valido (es. 104 o 12/A)";
            civicoError.style.display = "block";
            civico.style.borderColor = "red";
            isCivicoValid = false;
        } else {
            civicoError.style.display = "none";
            civico.style.borderColor = "green";
            isCivicoValid = true;
        }
        validaFormGenerale();
    });

    // --- 4. CITTÀ (INPUT) ---
    citta.addEventListener("input", () => {
        const val = citta.value.trim();
        if (val === "") {
            cittaError.style.display = "none";
            isCittaValid = false;
        } else if (!cittaRegex.test(val)) {
            cittaError.textContent = "Inserisci una città valida (solo lettere)";
            cittaError.style.display = "block";
            citta.style.borderColor = "red";
            isCittaValid = false;
        } else {
            cittaError.style.display = "none";
            citta.style.borderColor = "green";
            isCittaValid = true;
        }
        validaFormGenerale();
    });

    // Sicurezza extra al submit
    form.addEventListener("submit", (event) => {
        if (!(isDestinatarioValid && isViaValid && isCivicoValid && isCittaValid)) {
            event.preventDefault();
        }
    });
});