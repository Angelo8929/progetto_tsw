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
import model.CarrelloBean;
import model.CarrelloDAO;
import model.OrdineBean;
import model.OrdineDAO;
import model.ProdottoBean;
import model.ProdottoCarrelloBean;
import model.ProdottoCarrelloDAO;
import model.ProdottoDAO;
import model.ProdottoOrdineBean;
import model.ProdottoOrdineDAO;
import model.UtenteBean;

@WebServlet("/CompletaOrdineServlet")
public class CompletaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;
	private ProdottoCarrelloDAO prodottoCarrelloDAO;
	private ProdottoOrdineDAO prodottoOrdineDAO;
	private ProdottoDAO prodottoDAO;
	private OrdineDAO ordineDAO;

	@Override
	public void init() throws ServletException {
		carrelloDAO = new CarrelloDAO();
		prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		prodottoOrdineDAO = new ProdottoOrdineDAO();
		prodottoDAO = new ProdottoDAO();
		ordineDAO = new OrdineDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		
		if (utenteLoggato == null) {
			request.setAttribute("errorMessage", "Devi effettuare il login per completare un ordine.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		
		String idConsegnaParam = request.getParameter("id_consegna");
		if (idConsegnaParam == null || idConsegnaParam.isEmpty()) {
			request.setAttribute("errorMessage", "Devi selezionare un indirizzo di spedizione valido per proseguire.");
			request.getRequestDispatcher("/CheckoutServlet").forward(request, response);
			return;
		}

		try {
			int idConsegna = Integer.parseInt(idConsegnaParam);

			
			CarrelloBean carrelloUtente = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());
			if (carrelloUtente == null) {
				response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
				return;
			}

			
			List<ProdottoCarrelloBean> righeCarrello = prodottoCarrelloDAO
					.doRetrieveByCarrello(carrelloUtente.getId_carrello());

			
			if (righeCarrello == null || righeCarrello.isEmpty()) {
				request.setAttribute("errorMessage", "Il tuo carrello è vuoto. Impossibile completare l'acquisto.");
				request.getRequestDispatcher("/CarrelloServlet").forward(request, response);
				return;
			}

			long costoTotaleCentesimi = 0;
			int numProdottiTotali = 0;

			
			for (ProdottoCarrelloBean riga : righeCarrello) {
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(riga.getId_prodotto());
				if (prodotto != null) {
					
					if (prodotto.getDisponibilita() < riga.getQuantita()) {
						request.setAttribute("errorMessage", "Ci dispiace, il prodotto '" + prodotto.getNome_prodotto()
								+ "' non ha scorte sufficienti (Disponibili: " + prodotto.getDisponibilita() + " pz).");
						request.getRequestDispatcher("/CarrelloServlet").forward(request, response);
						return;
					}

					costoTotaleCentesimi += (long) (prodotto.getPrezzo() * 100) * riga.getQuantita();
					numProdottiTotali += riga.getQuantita();
				}
			}

			
			for (ProdottoCarrelloBean riga : righeCarrello) {
				
				boolean successoScarico = prodottoDAO.scaricaMagazzino(riga.getId_prodotto(), riga.getQuantita());

				if (!successoScarico) {
					request.setAttribute("errorMessage",
							"Errore: le scorte di magazzino sono cambiate. Riprova l'acquisto.");
					request.getRequestDispatcher("/CarrelloServlet").forward(request, response);
					return;
				}
			}

			
			OrdineBean nuovoOrdine = new OrdineBean();
			java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss");

			nuovoOrdine.setData_ordine(java.time.LocalDateTime.now().format(dtf));
			nuovoOrdine.setCosto_totale(costoTotaleCentesimi);
			nuovoOrdine.setNum_prodotti(numProdottiTotali);
			nuovoOrdine.setEmail_utente(utenteLoggato.getEmail());
			nuovoOrdine.setId_consegna(idConsegna);

			
			int idOrdineGenerato = ordineDAO.doSave(nuovoOrdine);

			
			if (idOrdineGenerato != -1) {
				for (ProdottoCarrelloBean riga : righeCarrello) {
					ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(riga.getId_prodotto());

					if (prodotto != null) {
						ProdottoOrdineBean dettaglio = new ProdottoOrdineBean();
						dettaglio.setNome_prodotto(prodotto.getNome_prodotto());
						dettaglio.setId_ordine(idOrdineGenerato);
						dettaglio.setId_prodotto(riga.getId_prodotto());
						dettaglio.setPrezzo(prodotto.getPrezzo());
						dettaglio.setQuantita(riga.getQuantita());
						dettaglio.setIva(prodotto.getIva() != 0 ? prodotto.getIva() : 22); // Usa l'IVA dinamica del DB!

						prodottoOrdineDAO.doSave(dettaglio);
					}
				}
			}

			
			for (ProdottoCarrelloBean riga : righeCarrello) {
				prodottoCarrelloDAO.doDeleteByProdottoAndCarrello(riga.getId_prodotto(),
						carrelloUtente.getId_carrello());
			}

			
			response.sendRedirect(request.getContextPath() + "/conferma_ordine.jsp");

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage",
					"Errore del database durante la finalizzazione dell'ordine: " + e.getMessage());
			request.getRequestDispatcher("/errore.jsp").forward(request, response);
		}
	}
}