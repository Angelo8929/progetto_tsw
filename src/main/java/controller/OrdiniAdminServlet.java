package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrdineBean;
import model.OrdineDAO;

@WebServlet("/admin/OrdiniAdminServlet")
public class OrdiniAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDAO ordineDAO;

	public void init() throws ServletException {
		ordineDAO = new OrdineDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String filtroEmail = request.getParameter("filtroEmail");
		String dataInizio = request.getParameter("dataInizio");
		String dataFine = request.getParameter("dataFine");

		try {
			
			List<OrdineBean> tuttiGliOrdini = ordineDAO.doRetrieveWithFilters(filtroEmail, dataInizio, dataFine);

			
			request.setAttribute("tuttiGliOrdini", tuttiGliOrdini);
			request.setAttribute("filtroEmail", filtroEmail != null ? filtroEmail : "");
			request.setAttribute("dataInizio", dataInizio != null ? dataInizio : "");
			request.setAttribute("dataFine", dataFine != null ? dataFine : "");

			request.getRequestDispatcher("/admin/ordini_admin.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}