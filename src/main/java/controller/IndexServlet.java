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



public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProdottoDAO pdao = new ProdottoDAO();

		try {
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			List<ProdottoBean> tuttiIProdotti = prodottoDAO.doRetrieveAll();

			List<ProdottoBean> prodottiInEvidenza = null;

			if (tuttiIProdotti != null && !tuttiIProdotti.isEmpty()) {
				
				java.util.Collections.shuffle(tuttiIProdotti);

				
				int limite = Math.min(tuttiIProdotti.size(), 4);
				prodottiInEvidenza = tuttiIProdotti.subList(0, limite);
			}

			
			request.setAttribute("prodotti", prodottiInEvidenza);

			request.getRequestDispatcher("home.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("errore.jsp");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
