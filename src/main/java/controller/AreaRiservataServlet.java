package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.InfoConsegnaBean;
import model.InfoConsegnaDAO;
import model.UtenteBean;

@WebServlet("/AreaRiservataServlet")
public class AreaRiservataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoConsegnaDAO infoConsegnaDAO;

	public void init() throws ServletException {
		infoConsegnaDAO = new InfoConsegnaDAO();
	}

	/**
	 * Gestisce l'accesso all'area riservata, caricando i dati dell'utente dal DB
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		// 1. Controllo sicurezza: se non è loggato, va al login
		if (utenteLoggato == null) {
			request.setAttribute("errorMessage", "Devi effettuare il login per accedere all'area riservata.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		try {
			// 2. Recuperiamo gli indirizzi salvati dell'utente dal database
			List<InfoConsegnaBean> listaIndirizzi = infoConsegnaDAO.doRetrieveByUtente(utenteLoggato.getEmail());

			// 3. Passiamo la lista come attributo della request alla JSP
			request.setAttribute("listaIndirizzi", listaIndirizzi);

			// Se la SalvaIndirizzoServlet ci ha rimandato qui con un successo, lo
			// intercettiamo
			String success = request.getParameter("success");
			if (success != null && success.equals("true")) {
				request.setAttribute("successMessage", "Indirizzo salvato con successo!");
			}

			// 4. Inoltriamo la richiesta alla pagina di visualizzazione
			request.getRequestDispatcher("/area_riservata.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero dei dati del profilo: " + e.getMessage());
			request.getRequestDispatcher("/errore.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}