package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarrelloBean;
import model.CarrelloDAO;
import model.ProdottoCarrelloDAO;
import model.UtenteBean;

@WebServlet("/AggiornaQuantitaCarrelloServlet")
public class AggiornaQuantitaCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;
	private ProdottoCarrelloDAO prodottoCarrelloDAO;

	public void init() throws ServletException {
		carrelloDAO = new CarrelloDAO();
		prodottoCarrelloDAO = new ProdottoCarrelloDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		if (utenteLoggato == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			out.print("{\"error\": \"Utente non loggato\"}");
			return;
		}

		String idProdottoParam = request.getParameter("idProdotto");
		String quantitaParam = request.getParameter("quantita");

		try {
			int idProdotto = Integer.parseInt(idProdottoParam);
			int quantita = Integer.parseInt(quantitaParam);

			if (quantita < 1) {
				out.print("{\"error\": \"La quantità deve essere almeno 1\"}");
				return;
			}

			
			CarrelloBean carrello = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());
			if (carrello == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.print("{\"error\": \"Carrello non trovato per questo utente\"}");
				return;
			}

			
			prodottoCarrelloDAO.doUpdateQuantity(carrello.getId_carrello(), idProdotto, quantita);

			out.print("{\"status\": \"success\", \"message\": \"Quantità aggiornata\"}");

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"error\": \"Errore durante l'aggiornamento\"}");
		}
	}
}