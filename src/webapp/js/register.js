document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".form form");
    const email = document.getElementById("email");
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const conferma_password = document.getElementById("conferma_password");


    email.placeholder = "Inserisci la tua email";
    username.placeholder = "inserisci il tuo username";
    password.placeholder = "inserisci password";
    conferma_password.placeholder = "conferma password";

    if (email) {
        email.focus();
    }


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


    const emailError = createErrorElement(email);
    const passwordError = createErrorElement(password);
    const usernameError = createErrorElement(username);
    const confermaPasswordError = createErrorElement(conferma_password);


    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    const usernameRegex = /^[a-zA-Z0-9_]{5,15}$/;

    form.addEventListener("submit", (event) => {
        let isFormValid = true;
        if (!usernameRegex.test(username.value.trim())) {
            usernameError.textContent = "username deve contenere almeno 5 caratteri";
            usernameError.style.display = "block";
            isFormValid = false;
        } else {
            usernameError.style.display = "none";
        }


        if (!emailRegex.test(email.value.trim())) {
            emailError.textContent = "email non valida";
            emailError.style.display = "block";
            isFormValid = false;
        } else {
            emailError.style.display = "none";
        }


        if (!passwordRegex.test(password.value.trim())) {
            passwordError.textContent = "password deve essere di almeno 6 caratteri, con almeno una lettera e un numero";
            passwordError.style.display = "block";
            isFormValid = false;
        } else {
            passwordError.style.display = "none";
        }

        // Validazione Conferma Password
        if (conferma_password.value.trim() === "") {
            confermaPasswordError.textContent = "Conferma la password";
            confermaPasswordError.style.display = "block"; // <-- Nota le virgolette
            isFormValid = false;
        } else if (conferma_password.value !== password.value) {
            confermaPasswordError.textContent = "Le password non corrispondono";
            confermaPasswordError.style.display = "block";
            isFormValid = false;
        } else {
            confermaPasswordError.style.display = "none";
        }

        // Blocco dell'invio se il form non è valido
        if (!isFormValid) {
            event.preventDefault();
        }





    })
}
)


