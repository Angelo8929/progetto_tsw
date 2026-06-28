package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;
import model.ProdottoDAO;

@WebServlet("/admin/AdminCancellaProdottoServlet")
public class AdminCancellaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;

	public void init() throws ServletException {
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("id");
		String redirectUrl = request.getContextPath() + "/admin/AdminModificaProdottoServlet";

		if (idParam == null || idParam.trim().isEmpty()) {
			response.sendRedirect(redirectUrl + "?errorMessage=" + URLEncoder.encode("ID prodotto mancante.", "UTF-8"));
			return;
		}

		try {
			int id_prodotto = Integer.parseInt(idParam);
			ProdottoBean prodottoDaCancellare = prodottoDAO.doRetrieveByKey(id_prodotto);

			if (prodottoDaCancellare == null || prodottoDaCancellare.getNome_prodotto() == null) {
				response.sendRedirect(redirectUrl + "?errorMessage="
						+ URLEncoder.encode("Prodotto non trovato nel catalogo.", "UTF-8"));
				return;
			}

			
			prodottoDAO.doDelete(id_prodotto);

			
			String successMessage = "Prodotto '" + prodottoDaCancellare.getNome_prodotto()
					+ "' eliminato con successo.";
			response.sendRedirect(redirectUrl + "?successMessage=" + URLEncoder.encode(successMessage, "UTF-8"));

		} catch (NumberFormatException e) {
			response.sendRedirect(
					redirectUrl + "?errorMessage=" + URLEncoder.encode("ID prodotto non valido.", "UTF-8"));
		} catch (SQLException e) {
			e.printStackTrace();
			String erroreMsg = "Errore interno del database durante la cancellazione del prodotto. Riprova.";
			response.sendRedirect(redirectUrl + "?errorMessage=" + URLEncoder.encode(erroreMsg, "UTF-8"));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}