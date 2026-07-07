document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".form form");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    
    emailInput.placeholder = "Inserisci la tua email (es. nome@dominio.it)";
    passwordInput.placeholder = "Inserisci la tua password (almeno 6 caratteri)";

    
    if (emailInput) {
        emailInput.focus();
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

    const emailError = createErrorElement(emailInput);
    const passwordError = createErrorElement(passwordInput);

   
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    
    form.addEventListener("submit", (event) => {
        let isFormValid = true;

       
        if (!emailRegex.test(emailInput.value.trim())) {
            emailError.textContent = "Inserisci un indirizzo email valido (es. testo@esempio.com).";
            emailError.style.display = "block";
            isFormValid = false;
        } else {
            emailError.style.display = "none"; 
        }

        
        if (!passwordRegex.test(passwordInput.value)) {
            passwordError.textContent = "La password deve contenere almeno 6 caratteri, di cui una lettera e un numero.";
            passwordError.style.display = "block";
            isFormValid = false;
        } else {
            passwordError.style.display = "none"; 
        }

        
        if (!isFormValid) {
            event.preventDefault(); 
        }
    });

    
    emailInput.addEventListener("input", () => emailError.style.display = "none");
    passwordInput.addEventListener("input", () => passwordError.style.display = "none");
});