document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".form form");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    // --- 1. GESTIONE DEI PLACEHOLDER ---
    // (Istruzioni di compilazione visibili nei placeholder)
    emailInput.placeholder = "Inserisci la tua email (es. nome@dominio.it)";
    passwordInput.placeholder = "Inserisci la tua password (almeno 6 caratteri)";

    // --- 2. GESTIONE DEL FOCUS ---
    // Imposta il focus automatico sul primo campo vuoto all'avvio
    if (emailInput) {
        emailInput.focus();
    }

    // --- 3. CREAZIONE DEGLI ELEMENTI DI ERRORE INLINE ---
    // Funzione di supporto per creare un contenitore per i messaggi di errore sotto ogni input
    const createErrorElement = (inputElement) => {
        const errorSpan = document.createElement("span");
        errorSpan.className = "error-message";
        errorSpan.style.color = "red";
        errorSpan.style.fontSize = "0.85em";
        errorSpan.style.display = "none"; // Nascosto di default
        errorSpan.style.marginTop = "5px";
        inputElement.parentNode.appendChild(errorSpan);
        return errorSpan;
    };

    const emailError = createErrorElement(emailInput);
    const passwordError = createErrorElement(passwordInput);

    // --- 4. ESPRESSIONI REGOLARI (REGEX) ---
    // Regex Email standard
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    // Regex Password: Minimo 6 caratteri, almeno una lettera e un numero
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    // --- 5. VALIDAZIONE AL SUBMIT ---
    form.addEventListener("submit", (event) => {
        let isFormValid = true;

        // Validazione Email
        if (!emailRegex.test(emailInput.value.trim())) {
            emailError.textContent = "Inserisci un indirizzo email valido (es. testo@esempio.com).";
            emailError.style.display = "block"; // Mostra errore inline
            isFormValid = false;
        } else {
            emailError.style.display = "none"; // Nascondi errore se corretto
        }

        // Validazione Password
        if (!passwordRegex.test(passwordInput.value)) {
            passwordError.textContent = "La password deve contenere almeno 6 caratteri, di cui una lettera e un numero.";
            passwordError.style.display = "block"; // Mostra errore inline
            isFormValid = false;
        } else {
            passwordError.style.display = "none"; // Nascondi errore se corretto
        }

        // Se anche solo un campo è errato, blocca l'invio del form al server
        if (!isFormValid) {
            event.preventDefault(); 
        }
    });

    // Optional: Rimuove l'errore inline mentre l'utente digita per migliorare la UX
    emailInput.addEventListener("input", () => emailError.style.display = "none");
    passwordInput.addEventListener("input", () => passwordError.style.display = "none");
});