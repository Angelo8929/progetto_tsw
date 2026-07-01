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

@WebServlet("/ProdottoServlet")
public class ProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO pdao;

	public ProdottoServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		pdao = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("id");
		String errorParam = request.getParameter("error");

		String errorMessage = (String) request.getAttribute("errorMessage");

		if (errorParam != null && errorParam.equals("true")) {
			errorMessage = "Si è verificato un errore durante l'aggiunta del prodotto al carrello. Riprova.";
		}

		if (idParam == null || idParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}

		try {
			int productId = Integer.parseInt(idParam);
			ProdottoBean prodotto = pdao.doRetrieveByKey(productId);

			if (prodotto != null) {
				request.setAttribute("prodotto", prodotto);

				if (errorMessage != null) {
					request.setAttribute("errorMessage", errorMessage);
				}

				request.getRequestDispatcher("prodotto.jsp").forward(request, response);
			} else {

				request.setAttribute("errorMessage", "Il prodotto richiesto non esiste.");
				request.getRequestDispatcher("/404.jsp").forward(request, response);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}