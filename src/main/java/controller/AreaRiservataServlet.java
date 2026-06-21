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
import model.OrdineDAO;
import model.ProdottoDAO;
import model.UtenteBean;

@WebServlet("/AreaRiservataServlet")
public class AreaRiservataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoConsegnaDAO infoConsegnaDAO;
	private OrdineDAO ordineDAO;
	private ProdottoDAO prodottoDAO;

	// Inizializziamo tutti i DAO qui, una volta sola all'avvio della servlet
	public void init() throws ServletException {
		infoConsegnaDAO = new InfoConsegnaDAO();
		ordineDAO = new OrdineDAO();
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		if (utenteLoggato == null) {
			request.setAttribute("errorMessage", "Devi effettuare il login per accedere all'area riservata.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		try {
			// CONTROLLO RUOLO
			// CONTROLLO RUOLO
			if (utenteLoggato.getIsAdmin()) {

				request.setAttribute("isAdmin", true);

				// 1. Carichiamo i dati globali per i pulsanti gestionali dell'admin
				java.util.List<model.OrdineBean> tuttiGliOrdini = ordineDAO.doRetrieveAll();
				java.util.List<model.ProdottoBean> tuttiIProdotti = prodottoDAO.doRetrieveAll();
				request.setAttribute("listaProdotti", tuttiIProdotti);
				// Passiamo gli ordini totali con un nome diverso per non fare confusione nella
				// JSP
				request.setAttribute("listaOrdiniTotali", tuttiGliOrdini);

				// 2. NOVITÀ: Carichiamo ANCHE gli ordini personali fatti dall'account admin
				// stesso
				java.util.Collection<model.OrdineBean> ordiniPersonaliAdmin = ordineDAO
						.doRetrieveByUtente(utenteLoggato.getEmail());
				request.setAttribute("listaOrdini", ordiniPersonaliAdmin);

			} else {
				request.setAttribute("isAdmin", false);

				// Carichiamo i dati del cliente normale (resta invariato)
				List<InfoConsegnaBean> listaIndirizzi = infoConsegnaDAO.doRetrieveByUtente(utenteLoggato.getEmail());
				java.util.Collection<model.OrdineBean> listaOrdini = ordineDAO
						.doRetrieveByUtente(utenteLoggato.getEmail());

				request.setAttribute("listaIndirizzi", listaIndirizzi);
				request.setAttribute("listaOrdini", listaOrdini); // Stesso nome attributo
			}

			String success = request.getParameter("success");
			if (success != null && success.equals("true")) {
				request.setAttribute("successMessage", "Operazione completata con successo!");
			}

			request.getRequestDispatcher("/area_riservata.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero dei dati del profilo: " + e.getMessage());
			// Più sicuro deviare su una pagina di errore dedicata per evitare crash
			// parziali della JSP
			request.getRequestDispatcher("/errore.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}