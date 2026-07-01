package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarrelloBean;
import model.CarrelloDAO;
import model.InfoConsegnaBean;
import model.InfoConsegnaDAO;
import model.ProdottoBean;
import model.ProdottoCarrelloBean;
import model.ProdottoCarrelloDAO;
import model.ProdottoDAO;
import model.UtenteBean;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;
	private ProdottoCarrelloDAO prodottoCarrelloDAO;
	private ProdottoDAO prodottoDAO;
	private InfoConsegnaDAO infoConsegnaDAO;

	public void init() throws ServletException {
		carrelloDAO = new CarrelloDAO();
		prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		prodottoDAO = new ProdottoDAO();
		infoConsegnaDAO = new InfoConsegnaDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		if (utenteLoggato == null) {
			request.setAttribute("errorMessage", "Devi effettuare il login per procedere al checkout.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		double prezzoTotale = 0.0;

		try {

			CarrelloBean carrelloUtente = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());
			if (carrelloUtente != null) {
				List<ProdottoCarrelloBean> righeCarrello = prodottoCarrelloDAO
						.doRetrieveByCarrello(carrelloUtente.getId_carrello());
				for (ProdottoCarrelloBean riga : righeCarrello) {
					ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(riga.getId_prodotto());
					if (prodotto != null) {
						prezzoTotale += (prodotto.getPrezzo() + (prodotto.getPrezzo() * (prodotto.getIva() / 100.0)))
								* riga.getQuantita();
					}
				}

			}

			if (prezzoTotale <= 0) {
				response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
				return;
			}

			List<InfoConsegnaBean> listaIndirizzi = infoConsegnaDAO.doRetrieveByUtente(utenteLoggato.getEmail());

			request.setAttribute("listaIndirizzi", listaIndirizzi);
			request.setAttribute("prezzoTotale", prezzoTotale);

			request.getRequestDispatcher("/checkout.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la fase di checkout: " + e.getMessage());
			request.getRequestDispatcher("/500.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}