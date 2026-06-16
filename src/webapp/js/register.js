document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".form form");
    const email = document.getElementById("email");
    const username = document.getElementById("username");
	const telefono = document.getElementById("telefono");
    const password = document.getElementById("password");
    const conferma_password = document.getElementById("conferma_password");


    email.placeholder = "Inserisci la tua email";
    username.placeholder = "inserisci il tuo username";
	telefono.placeholder= "inserisci il tuo numero di telefono";
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
	const telefonoError= createErrorElement(telefono);
    const confermaPasswordError = createErrorElement(conferma_password);


    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
    const usernameRegex = /^[a-zA-Z0-9_]{5,15}$/;
	const telefonoRegex= /^\+\d{12}$/;

    form.addEventListener("submit", (event) => {
        let isFormValid = true;
        if (!usernameRegex.test(username.value.trim())) {
            usernameError.textContent = "username deve contenere da 5 fino a 15 aratteri";
            usernameError.style.display = "block";
            isFormValid = false;
        } else {
            usernameError.style.display = "none";
        }
		
		
		if(!telefonoRegex.test(telefono.value.trim())) {
			telefonoError.textContent="Il telefono deve contenere + ed essere seguito da 12 cifre";
			telefonoError.style.display="block";
			isFormValid=false;
			
		} else{
			telefonoError.style.display="none";
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


