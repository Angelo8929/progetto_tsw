package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;
import model.ProdottoDAO;

@WebServlet("/RicercaSuggerimentiServlet")
public class RicercaSuggerimentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;

	public void init() throws ServletException {
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String prefisso = request.getParameter("term");

		// Impostiamo il tipo di contenuto come JSON con codifica UTF-8
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		if (prefisso == null || prefisso.trim().length() < 2) {
			out.print("[]");
			return;
		}

		try {
			// Chiediamo al DAO i prodotti che corrispondono al testo digitato
			List<ProdottoBean> suggeriti = prodottoDAO.doRetrieveByPrefix(prefisso.trim());

			// Costruiamo manualmente l'array JSON [{ "id": 1, "nome": "..." }, ...]
			StringBuilder json = new StringBuilder("[");
			for (int i = 0; i < suggeriti.size(); i++) {
				ProdottoBean prod = suggeriti.get(i);

				json.append("{");
				json.append("\"id\":").append(prod.getId_prodotto()).append(",");
				// Escape dei doppi apici per evitare problemi di sintassi JSON se il nome
				// contiene virgolette
				String nomeSanificato = prod.getNome_prodotto().replace("\"", "\\\"");
				json.append("\"nome\":\"").append(nomeSanificato).append("\"");
				json.append("}");

				if (i < suggeriti.size() - 1) {
					json.append(",");
				}
			}
			json.append("]");

			out.print(json.toString());

		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("[]");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}