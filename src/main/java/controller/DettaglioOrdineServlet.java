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
import model.OrdineBean;
import model.OrdineDAO;
import model.ProdottoOrdineBean;
import model.ProdottoOrdineDAO;
import model.UtenteBean;

@WebServlet("/DettaglioOrdineServlet")
public class DettaglioOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDAO ordineDAO;
	private ProdottoOrdineDAO prodottoOrdineDAO;

	public void init() throws ServletException {
		ordineDAO = new OrdineDAO();
		prodottoOrdineDAO = new ProdottoOrdineDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		if (utenteLoggato == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String idParam = request.getParameter("id");
		if (idParam == null || idParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
			return;
		}

		try {
			int idOrdine = Integer.parseInt(idParam);

			// 1. Recuperiamo la testata dell'ordine
			OrdineBean ordine = ordineDAO.doRetrieveByKey(idOrdine);

			// Controllo di sicurezza fondamentale: l'ordine deve appartenere all'utente
			// loggato!
			if (ordine == null || !ordine.getEmail_utente().equals(utenteLoggato.getEmail())) {
				response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
				return;
			}

			// 2. Recuperiamo i dettagli dei prodotti acquistati
			List<ProdottoOrdineBean> dettagli = prodottoOrdineDAO.doRetrieveByOrdine(idOrdine);

			// Passiamo i dati alla JSP di dettaglio
			request.setAttribute("ordine", ordine);
			request.setAttribute("dettagli", dettagli);

			request.getRequestDispatcher("/dettaglio_ordine.jsp").forward(request, response);

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}