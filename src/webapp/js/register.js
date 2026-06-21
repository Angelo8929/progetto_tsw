document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".form form");
    const email = document.getElementById("email");
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const conferma_password = document.getElementById("conferma_password");
    
    const submitBtn = form.querySelector("input[type='submit']");

    email.placeholder = "Inserisci la tua email";
    username.placeholder = "inserisci il tuo username";
    password.placeholder = "inserisci password";
    conferma_password.placeholder = "conferma password";

    // Mettiamo il focus iniziale sul primo campo all'apertura della pagina
    if (username) {
        username.focus();
    }

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

    const emailError = createErrorElement(email);
    const passwordError = createErrorElement(password);
    const usernameError = createErrorElement(username);
    const confermaPasswordError = createErrorElement(conferma_password);

    const usernameRegex = /^[a-zA-Z0-9._]{5,15}$/;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;

    let isUsernameValid = false;
    let isEmailValid = false;
    let isPasswordValid = false;
    let isConfermaValid = false;

    const validaFormGenerale = () => {
        if (isUsernameValid && isEmailValid && isPasswordValid && isConfermaValid) {
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

    aggiungiGestioneFocusGenerica(username, usernameError);
    aggiungiGestioneFocusGenerica(email, emailError);
    aggiungiGestioneFocusGenerica(password, passwordError);
    aggiungiGestioneFocusGenerica(conferma_password, confermaPasswordError);

    // --- 1. VALIDAZIONE USERNAME (Bordi reattivi, il cursore non si sposta) ---
    username.addEventListener("input", () => {
        const val = username.value.trim();
        if (val === "") {
            usernameError.style.display = "none";
            isUsernameValid = false;
        } else if (!usernameRegex.test(val)) {
            usernameError.textContent = "username deve contenere da 5 fino a 15 caratteri";
            usernameError.style.display = "block";
            username.style.borderColor = "red";
            isUsernameValid = false;
        } else {
            usernameError.style.display = "none";
            username.style.borderColor = "green";
            isUsernameValid = true;
        }
        validaFormGenerale();
    });

    // --- 2. VALIDAZIONE EMAIL + AJAX ---
    email.addEventListener("input", () => {
        const emailValue = email.value.trim();

        if (emailValue === "") {
            emailError.style.display = "none";
            email.style.borderColor = "#ccc";
            isEmailValid = false;
            validaFormGenerale();
            return;
        }

        if (!emailRegex.test(emailValue)) {
            emailError.textContent = "email non valida";
            emailError.style.color = "red";
            emailError.style.display = "block";
            email.style.borderColor = "red";
            isEmailValid = false;
            validaFormGenerale();
            return;
        }

        fetch(`ControllaEmailServlet?email=${encodeURIComponent(emailValue)}`)
            .then(response => response.json())
            .then(data => {
                if (data.esiste) {
                    emailError.textContent = "❌ Questa email è già registrata!";
                    emailError.style.color = "red";
                    emailError.style.display = "block";
                    email.style.borderColor = "red";
                    isEmailValid = false;
                } else {
                    emailError.textContent = "✅ Email disponibile.";
                    emailError.style.color = "green";
                    emailError.style.display = "block";
                    email.style.borderColor = "green";
                    isEmailValid = true;
                }
                validaFormGenerale();
            })
            .catch(error => {
                console.error("Errore AJAX:", error);
            });
    });

    // --- 3. VALIDAZIONE PASSWORD ---
    password.addEventListener("input", () => {
        const val = password.value.trim();
        if (val === "") {
            passwordError.style.display = "none";
            isPasswordValid = false;
        } else if (!passwordRegex.test(val)) {
            passwordError.textContent = "password deve essere tra 8 e 16 caratteri, con almeno una lettera e un numero";
            passwordError.style.display = "block";
            password.style.borderColor = "red";
            isPasswordValid = false;
        } else {
            passwordError.style.display = "none";
            password.style.borderColor = "green";
            isPasswordValid = true;
        }
        
        controlloConfermaPassword();
        validaFormGenerale();
    });

    // --- 4. FUNZIONE E ASCOLTATORE CONFERMA PASSWORD ---
    const controlloConfermaPassword = () => {
        const valConf = conferma_password.value.trim();
        const valPass = password.value.trim();

        if (valConf === "") {
            confermaPasswordError.style.display = "none";
            isConfermaValid = false;
        } else if (valConf !== valPass) {
            confermaPasswordError.textContent = "Le password non corrispondono";
            confermaPasswordError.style.display = "block";
            conferma_password.style.borderColor = "red";
            isConfermaValid = false;
        } else {
            confermaPasswordError.style.display = "none";
            conferma_password.style.borderColor = "green";
            isConfermaValid = true;
        }
    };

    conferma_password.addEventListener("input", () => {
        controlloConfermaPassword();
        validaFormGenerale();
    });

    form.addEventListener("submit", (event) => {
        if (!(isUsernameValid && isEmailValid && isPasswordValid && isConfermaValid)) {
            event.preventDefault();
        }
    });
});