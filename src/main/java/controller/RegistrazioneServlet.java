package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;
import model.UtenteDAO;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrazioneServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> erroriList = new ArrayList<>();
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confermaPassword = request.getParameter("conferma_password");

		String usernameRegex = "^[a-zA-Z0-9._]{5,15}$";
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";

		// Controlli sui singoli campi
		if (username == null || !username.trim().matches(usernameRegex)) {
			erroriList.add(
					"L'username deve contenere da 5 a 15 caratteri (sono ammessi solo lettere, numeri, punti e underscore).");
		}

		if (email == null || !email.trim().matches(emailRegex)) {
			erroriList.add("Il formato dell'email inserito non è valido.");
		}

		if (password == null || !password.trim().matches(passwordRegex)) {
			erroriList.add("La password deve essere tra 8 e 16 caratteri e contenere almeno una lettera e un numero.");
		} else if (!password.equals(confermaPassword)) {
			erroriList.add("La password di conferma non coincide con la password inserita.");
		}

		// Se ci sono errori di validazione formale, ci fermiamo qui
		if (!erroriList.isEmpty()) {
			request.setAttribute("errori", erroriList);
			// Rimandiamo indietro i vecchi dati per non svuotare il form
			request.setAttribute("oldUsername", username);
			request.setAttribute("oldEmail", email);

			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		// Se i controlli formali sono passati, interroghiamo il database
		UtenteDAO udao = new UtenteDAO();
		try {
			if (udao.doRetrieveByEmail(email.trim()) != null) {
				erroriList.add("L'indirizzo email inserito è già associato a un account registrato.");
				request.setAttribute("errori", erroriList);
				request.setAttribute("oldUsername", username);
				request.getRequestDispatcher("register.jsp").forward(request, response);
				return;
			}

			UtenteBean ub = new UtenteBean();
			ub.setEmail(email.trim());
			ub.setUsername(username.trim());
			ub.setPassword(Sicurezza.hashPassword(password));
			ub.setAdmin(false);

			udao.doSave(ub);

			// Salviamo in sessione per non perdere il messaggio nel sendRedirect
			request.setAttribute("successMessage", "Registrazione completata con successo! Adesso puoi accedere.");
			response.sendRedirect("login.jsp");

		} catch (SQLException e) {
			e.printStackTrace();
			erroriList.add("Si è verificato un errore interno del server durante il salvataggio. Riprova più tardi.");
			request.setAttribute("errori", erroriList);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}

	}

}
