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

@WebServlet("/admin/AdminModificaProdottoServlet")
public class AdminModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;

	public void init() throws ServletException {
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("id");

		try {
			List<ProdottoBean> listaProdotti = prodottoDAO.doRetrieveAll();
			request.setAttribute("listaProdotti", listaProdotti);

			if (idParam != null && !idParam.isEmpty()) {
				int idProdotto = Integer.parseInt(idParam);
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
				if (prodotto != null) {
					request.setAttribute("prodottoSelezionato", prodotto);
				}
			}
			String msgSuccess = request.getParameter("successMessage");
			String msgError = request.getParameter("errorMessage");

			if (msgSuccess != null)
				request.setAttribute("successMessage", msgSuccess);
			if (msgError != null)
				request.setAttribute("errorMessage", msgError);
			
			request.getRequestDispatcher("/admin/modifica_prodotti_admin.jsp").forward(request, response);

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {
			int idProdotto = Integer.parseInt(request.getParameter("id_prodotto"));
			String nome = request.getParameter("nome");
			double prezzo = Double.parseDouble(request.getParameter("prezzo"));
			String categoria = request.getParameter("categoria");
			String imgPath = request.getParameter("imgPath");
			String effervescenza = request.getParameter("effervescenza");
			String descrizione = request.getParameter("descrizione");
			int iva = Integer.parseInt(request.getParameter("iva"));
			int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));

			
			String percAlcolParam = request.getParameter("percAlcol");
			double percAlcol = 0;

			if (percAlcolParam != null && !percAlcolParam.trim().isEmpty() && !"Analcolici".equals(categoria)) {
				
				percAlcol = Double.parseDouble(percAlcolParam);
			}

			
			ProdottoBean prodottoAggiornato = new ProdottoBean();
			prodottoAggiornato.setId_prodotto(idProdotto);
			prodottoAggiornato.setNome_prodotto(nome);
			prodottoAggiornato.setPrezzo(prezzo);
			prodottoAggiornato.setCategoria(categoria);
			prodottoAggiornato.setImgPath(imgPath);

			
			prodottoAggiornato.setEffervescenza("Analcolici".equals(categoria) ? effervescenza : "");
			prodottoAggiornato.setPerc_alcol(percAlcol);
			prodottoAggiornato.setDescrizione(descrizione);
			prodottoAggiornato.setIva(iva);
			prodottoAggiornato.setDisponibilita(disponibilita);

			
			prodottoDAO.doUpdate(prodottoAggiornato);

			request.setAttribute("successMessage", "Prodotto aggiornato con successo!");
			request.getRequestDispatcher("/AreaRiservataServlet").forward(request, response);

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante l'aggiornamento del prodotto: dati non validi.");
			request.getRequestDispatcher("/AreaRiservataServlet").forward(request, response);
		}
	}
}