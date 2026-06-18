package controller;

import java.io.IOException;
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

/**
 * Servlet implementation class AggiungiAlCarrelloServlet
 */
@WebServlet("/AggiungiAlCarrelloServlet")
public class AggiungiAlCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;
	private ProdottoCarrelloDAO prodottoCarrelloDAO;
	private ProdottoDAO prodottoDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public AggiungiAlCarrelloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		carrelloDAO = new CarrelloDAO();
		prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		prodottoDAO = new ProdottoDAO();

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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		Integer id_prodotto = null;
		int quantita;

		try {
			id_prodotto = Integer.parseInt(request.getParameter("id_prodotto"));
			quantita = Integer.parseInt(request.getParameter("quantita"));

			if (quantita <= 0) {
				// FIX: Meglio usare un sendRedirect alla Servlet se possibile, o assicurarsi
				// che la servlet ProdottoServlet gestisca l'errore
				request.setAttribute("errorMessage", "La quantità deve essere almeno 1.");
				request.getRequestDispatcher("/ProdottoServlet?id=" + id_prodotto).forward(request, response);
				return;
			}

			ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(id_prodotto);
			if (prodotto == null) {
				request.setAttribute("errorMessage", "prodotto non trovato");
				request.getRequestDispatcher("/ProdottoServlet?id=" + id_prodotto).forward(request, response);
				return;
			}

			String imgPath = prodotto.getImgPath();

			if (utenteLoggato != null) {
				CarrelloBean carrelloUtente = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());

				if (carrelloUtente == null) {
					carrelloUtente = new CarrelloBean();
					carrelloUtente.setId_utente(utenteLoggato.getEmail());
					carrelloDAO.doSave(carrelloUtente);

					session.setAttribute("carrello", carrelloUtente);
				}

				ProdottoCarrelloBean prodottoEsistente = prodottoCarrelloDAO
						.doRetrieveByProdottoAndCarrello(id_prodotto, carrelloUtente.getId_carrello());

				if (prodottoEsistente != null) {
					prodottoEsistente.setQuantita(prodottoEsistente.getQuantita() + quantita);
					prodottoEsistente.setImgPath(imgPath);
					prodottoCarrelloDAO.doUpdate(prodottoEsistente);
				} else {
					ProdottoCarrelloBean nuovoProdotto = new ProdottoCarrelloBean();
					nuovoProdotto.setId_prodotto(id_prodotto);
					nuovoProdotto.setId_carrello(carrelloUtente.getId_carrello());
					nuovoProdotto.setImgPath(imgPath);
					nuovoProdotto.setQuantita(quantita);
					prodottoCarrelloDAO.doSave(nuovoProdotto);
				}

			} else {
				@SuppressWarnings("unchecked")
				Map<Integer, Integer> carrelloOspite = (Map<Integer, Integer>) session.getAttribute("carrelloOspite");

				if (carrelloOspite == null) {
					carrelloOspite = new java.util.HashMap<>();
				}

				int quantitaEsistente = carrelloOspite.getOrDefault(id_prodotto, 0);
				carrelloOspite.put(id_prodotto, quantitaEsistente + quantita);

				session.setAttribute("carrelloOspite", carrelloOspite);
			}

			response.sendRedirect(request.getContextPath() + "/CarrelloServlet");

		} catch (Exception e) {
			e.printStackTrace();
			// FIX FONDAMENTALE: Reindirizziamo alla Servlet passandogli l'id del prodotto.
			// La Servlet ricaricherà l'attributo "prodotto" salvando la pagina di dettaglio
			// dal crash.
			response.sendRedirect(request.getContextPath() + "/ProdottoServlet?id="
					+ (id_prodotto != null ? id_prodotto : "") + "&error=true");
		}

	}

}
