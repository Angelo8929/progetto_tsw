package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.InfoConsegnaBean;
import model.InfoConsegnaDAO;
import model.OrdineBean;
import model.OrdineDAO;
import model.ProdottoBean;
import model.ProdottoDAO;
import model.UtenteBean;

@WebServlet("/AreaRiservataServlet")
public class AreaRiservataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoConsegnaDAO infoConsegnaDAO;
	private OrdineDAO ordineDAO;
	private ProdottoDAO prodottoDAO;

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
			// 1. CARICAMENTO COMUNE (Sia per Admin che per Cliente normale)
			// Estraiamo gli ordini personali e gli indirizzi personali legati all'email di
			// chi è loggato
			Collection<OrdineBean> ordiniPersonali = ordineDAO.doRetrieveByUtente(utenteLoggato.getEmail());
			List<InfoConsegnaBean> indirizziPersonali = infoConsegnaDAO.doRetrieveByUtente(utenteLoggato.getEmail());

			// Settiamo gli attributi comuni che la JSP si aspetta in ogni caso
			request.setAttribute("listaOrdini", ordiniPersonali);
			request.setAttribute("listaIndirizzi", indirizziPersonali); // FIX: Ora viene passato SEMPRE, anche
																		// all'admin!

			// 2. CONTROLLO RUOLO (Caricamento dati aggiuntivi solo se Admin)
			if (utenteLoggato.getIsAdmin()) {
				request.setAttribute("isAdmin", true);

				// Carichiamo i dati globali per la plancia di comando dell'admin
				List<OrdineBean> tuttiGliOrdini = ordineDAO.doRetrieveAll();
				List<ProdottoBean> tuttiIProdotti = prodottoDAO.doRetrieveAll();

				request.setAttribute("listaProdotti", tuttiIProdotti);
				request.setAttribute("listaOrdiniTotali", tuttiGliOrdini);
			} else {
				request.setAttribute("isAdmin", false);
			}

			// Gestione di un eventuale parametro di successo nell'URL
			String success = request.getParameter("success");
			if (success != null && success.equals("true")) {
				request.setAttribute("successMessage", "Operazione completata con successo!");
			}

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