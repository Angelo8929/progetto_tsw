package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;
import model.ProdottoDAO;

@WebServlet("/CatalogoServlet")
public class CatalogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;

	public void init() throws ServletException {
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String[] categorieSelezionate = request.getParameterValues("categoria");
			String prezzoMinParam = request.getParameter("prezzoMin");
			String prezzoMaxParam = request.getParameter("prezzoMax");

			List<ProdottoBean> prodotti;

			double prezzoMin = (prezzoMinParam != null && !prezzoMinParam.isEmpty())
					? Double.parseDouble(prezzoMinParam)
					: 0.0;
			double prezzoMax = (prezzoMaxParam != null && !prezzoMaxParam.isEmpty())
					? Double.parseDouble(prezzoMaxParam)
					: Double.MAX_VALUE;

			
			if ((categorieSelezionate != null && categorieSelezionate.length > 0)
					|| (prezzoMinParam != null && !prezzoMinParam.isEmpty())
					|| (prezzoMaxParam != null && !prezzoMaxParam.isEmpty())) {

				
				prodotti = prodottoDAO.doRetrieveAllFiltered(categorieSelezionate, prezzoMin, prezzoMax);
			} else {
				prodotti = prodottoDAO.doRetrieveAll();
			}

			
			request.setAttribute("prodotti", prodotti);

			
			request.getRequestDispatcher("/catalogo.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel caricamento del catalogo prodotti: " + e.getMessage());
			request.getRequestDispatcher("/errore.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			
			response.sendRedirect(request.getContextPath() + "/CatalogoServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}