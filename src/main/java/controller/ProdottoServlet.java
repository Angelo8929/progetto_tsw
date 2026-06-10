package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;

/**
 * Servlet implementation class ProdottoServlet
 */
@WebServlet("/ProdottoServlet")
public class ProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProdottoDAO pdao = new ProdottoDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProdottoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("id");
		// TODO Auto-generated method stub
		ProdottoBean prodotto = new ProdottoBean();
		try {
			int productId = Integer.parseInt(idParam);
			prodotto = pdao.doRetrieveByKey(productId);

			request.setAttribute("prodotto", prodotto);
			System.out.println(prodotto.getId_prodotto());
			request.getRequestDispatcher("prodotto.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
