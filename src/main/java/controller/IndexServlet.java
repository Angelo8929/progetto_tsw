package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;
import model.ProdottoDAO;

/**
 * Servlet implementation class CatalogoServlet
 */

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			// 1. Recuperiamo tutti i prodotti dal database
			List<ProdottoBean> tuttiIProdotti = prodottoDAO.doRetrieveAll();

			List<ProdottoBean> prodottiInEvidenza = null;

			if (tuttiIProdotti != null && !tuttiIProdotti.isEmpty()) {
				// 2. Mescoliamo la lista in modo completamente casuale
				java.util.Collections.shuffle(tuttiIProdotti);

				// 3. Prendiamo al massimo 4 prodotti (usiamo Math.min per evitare crash se sul
				// DB ci sono meno di 4 prodotti totali)
				int limite = Math.min(tuttiIProdotti.size(), 4);
				prodottiInEvidenza = tuttiIProdotti.subList(0, limite);
			}

			// 4. Passiamo alla JSP la lista che ora contiene esattamente 4 prodotti casuali
			request.setAttribute("prodotti", prodottiInEvidenza);

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("500.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
