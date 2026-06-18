package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;
import model.ProdottoDAO;

/**
 * Servlet implementation class ProdottoServlet
 */
@WebServlet("/ProdottoServlet")
public class ProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO pdao; // Buona norma inizializzarlo nel metodo init()

	public ProdottoServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		pdao = new ProdottoDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("id");
		String errorParam = request.getParameter("error"); // <--- Intercettiamo il flag di errore dall'URL

		// Controlliamo se c'è già un messaggio di errore ereditato da un eventuale
		// forward
		String errorMessage = (String) request.getAttribute("errorMessage");

		// Se arriviamo da un sendRedirect con "?error=true", creiamo un messaggio
		// personalizzato
		if (errorParam != null && errorParam.equals("true")) {
			errorMessage = "Si è verificato un errore durante l'aggiunta del prodotto al carrello. Riprova.";
		}

		if (idParam == null || idParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/index.jsp"); // Evita NumberFormatException se l'id manca
			return;
		}

		try {
			int productId = Integer.parseInt(idParam);
			ProdottoBean prodotto = pdao.doRetrieveByKey(productId);

			if (prodotto != null) {
				request.setAttribute("prodotto", prodotto);

				// Se esiste un messaggio di errore, lo passiamo alla JSP
				if (errorMessage != null) {
					request.setAttribute("errorMessage", errorMessage);
				}

				request.getRequestDispatcher("prodotto.jsp").forward(request, response);
			} else {
				// Prodotto non trovato nel DB
				request.setAttribute("errorMessage", "Il prodotto richiesto non esiste.");
				request.getRequestDispatcher("errore.jsp").forward(request, response);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore di connessione al database: " + e.getMessage());
			request.getRequestDispatcher("errore.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}