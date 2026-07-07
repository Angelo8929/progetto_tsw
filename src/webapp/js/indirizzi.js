document.addEventListener("DOMContentLoaded", () => {
    
    const form = document.querySelector("form[action='SalvaIndirizzoServlet']");
    if (!form) return;

    const destinatario = document.getElementById("destinatario");
    const via = document.getElementById("indirizzo_via");
    const civico = document.getElementById("civico");
    const citta = document.getElementById("citta");
    
    
    const submitBtn = form.querySelector("input[type='submit']");

   
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

    
    const destinatarioRegex = /^[a-zA-Z\s]{4,50}$/;
    
    const viaRegex = /^[a-zA-Z0-9\s'.,-]{4,100}$/;
    
    const civicoRegex = /^[0-9]{1,5}[a-zA-Z\/]{0,4}$/;
    
    const cittaRegex = /^[a-zA-Z\s'.-]{2,50}$/;

    
    let isDestinatarioValid = false;
    let isViaValid = false;
    let isCivicoValid = false;
    let isCittaValid = false;

    
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

    
    validaFormGenerale();

    
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

    
    form.addEventListener("submit", (event) => {
        if (!(isDestinatarioValid && isViaValid && isCivicoValid && isCittaValid)) {
            event.preventDefault();
        }
    });
});