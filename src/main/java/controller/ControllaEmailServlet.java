package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;
import model.UtenteDAO; // Sostituisci con il nome esatto del tuo DAO utenti

@WebServlet("/ControllaEmailServlet")
public class ControllaEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDAO utenteDAO;

	public void init() throws ServletException {
		utenteDAO = new UtenteDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		boolean esiste = false;

		if (email != null && !email.trim().isEmpty()) {
			try {
				// Utilizza il metodo del tuo DAO che recupera l'utente tramite email
				// Se il metodo restituisce un oggetto diverso da null, l'email esiste già
				UtenteBean utente = utenteDAO.doRetrieveByEmail(email.trim());
				if (utente != null && utente.getEmail() != null) {
					esiste = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Impostiamo l'header di risposta per specificare il formato JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// Scriviamo l'oggetto JSON direttamente nello stream di output
		response.getWriter().write("{\"esiste\": " + esiste + "}");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}