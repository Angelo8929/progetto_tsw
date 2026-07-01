package controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarrelloBean;
import model.CarrelloDAO;
import model.ProdottoBean;
import model.ProdottoCarrelloBean;
import model.ProdottoCarrelloDAO;
import model.ProdottoDAO;
import model.UtenteBean;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;
	private ProdottoCarrelloDAO prodottoCarrelloDAO;
	private ProdottoDAO prodottoDAO;

	public CarrelloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		carrelloDAO = new CarrelloDAO();
		prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		Map<ProdottoBean, Integer> elementiCarrello = new LinkedHashMap<>();
		double prezzoTotale = 0.0;

		try {
			if (utenteLoggato != null) {

				CarrelloBean carrelloUtente = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());

				if (carrelloUtente == null) {

					carrelloUtente = new CarrelloBean();
					carrelloUtente.setId_utente(utenteLoggato.getEmail());
					carrelloDAO.doSave(carrelloUtente);

					Map<Integer, Integer> carrelloOspite = (Map<Integer, Integer>) session
							.getAttribute("carrelloOspite");
					if (carrelloOspite != null && !carrelloOspite.isEmpty()) {
						for (Map.Entry<Integer, Integer> entry : carrelloOspite.entrySet()) {
							ProdottoCarrelloBean nuovoProdotto = new ProdottoCarrelloBean();
							nuovoProdotto.setId_carrello(carrelloUtente.getId_carrello());
							nuovoProdotto.setId_prodotto(entry.getKey());
							nuovoProdotto.setQuantita(entry.getValue());

							prodottoCarrelloDAO.doSave(nuovoProdotto);
						}
						session.removeAttribute("carrelloOspite");
					}
				}

				List<ProdottoCarrelloBean> righeCarrello = prodottoCarrelloDAO
						.doRetrieveByCarrello(carrelloUtente.getId_carrello());

				for (ProdottoCarrelloBean riga : righeCarrello) {
					ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(riga.getId_prodotto());
					if (prodotto != null) {
						int qta = riga.getQuantita();
						elementiCarrello.put(prodotto, qta);
						prezzoTotale += (prodotto.getPrezzo() + (prodotto.getPrezzo() * (prodotto.getIva() / 100.0)))
								* qta;
					}
				}

			} else {

				Map<Integer, Integer> carrelloOspite = (Map<Integer, Integer>) session.getAttribute("carrelloOspite");

				if (carrelloOspite != null && !carrelloOspite.isEmpty()) {
					for (Map.Entry<Integer, Integer> entry : carrelloOspite.entrySet()) {
						Integer idProdotto = entry.getKey();
						Integer qta = entry.getValue();

						ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
						if (prodotto != null) {
							elementiCarrello.put(prodotto, qta);
							prezzoTotale += prodotto.getPrezzo()
									+ (prodotto.getPrezzo() * (prodotto.getIva() / 100.0)) * qta;
						}
					}
				}
			}

			request.setAttribute("elementiCarrello", elementiCarrello);
			request.setAttribute("prezzoTotale", prezzoTotale);

			request.getRequestDispatcher("/carrello.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel caricamento del carrello: " + e.getMessage());
			request.getRequestDispatcher("/500.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		String action = request.getParameter("action");

		if (action != null && action.equals("rimuovi")) {
			int idProdotto = Integer.parseInt(request.getParameter("id_prodotto"));

			try {
				if (utenteLoggato != null) {

					CarrelloBean carrelloUtente = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());
					if (carrelloUtente != null) {

						prodottoCarrelloDAO.doDeleteByProdottoAndCarrello(idProdotto, carrelloUtente.getId_carrello());
					}
				} else {

					Map<Integer, Integer> carrelloOspite = (Map<Integer, Integer>) session
							.getAttribute("carrelloOspite");
					if (carrelloOspite != null) {
						carrelloOspite.remove(idProdotto);
						session.setAttribute("carrelloOspite", carrelloOspite);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect(request.getContextPath() + "/CarrelloServlet");

	}

}
