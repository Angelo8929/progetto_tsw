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

@WebServlet("/admin/AdminUploadProdottoServlet")
public class AdminUploadProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;

	public void init() throws ServletException {
		prodottoDAO = new ProdottoDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {
			// Recupero e parsing dei dati dal form della JSP
			String nome = request.getParameter("nome");
			String categoria = request.getParameter("categoria");
			double prezzo = Double.parseDouble(request.getParameter("prezzo"));
			String percAlcolParam = (request.getParameter("percAlcol"));
			String imgPath = request.getParameter("imgPath");

			// Campi opzionali o aggiuntivi del tuo DB (imposta valori di default se vuoti)

			String effervescenza = request.getParameter("effervescenza");
			String descrizione = request.getParameter("descrizione");
			int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));
			int iva = Integer.parseInt(request.getParameter("iva"));

			double percAlcol = 0;

			if (percAlcolParam != null && !percAlcolParam.trim().isEmpty() && !"Analcolici".equals(categoria)) {
				// Il tuo DB ha un INT su questa colonna, quindi facciamo il cast corretto a
				// Integer
				percAlcol = Double.parseDouble(percAlcolParam);
			}

			// Costruzione del Bean
			ProdottoBean nuovoProdotto = new ProdottoBean();
			nuovoProdotto.setNome_prodotto(nome);
			nuovoProdotto.setCategoria(categoria); // se presente nel tuo Bean, altrimenti setta sottocategoria
			nuovoProdotto.setPrezzo(prezzo);
			nuovoProdotto.setPerc_alcol(percAlcol);
			nuovoProdotto.setImgPath(imgPath != null ? imgPath : "");

			nuovoProdotto.setEffervescenza("Analcolici".equals(categoria) ? effervescenza : "");
			nuovoProdotto.setDescrizione(descrizione);
			nuovoProdotto.setIva(iva);
			nuovoProdotto.setDisponibilita(disponibilita);

			// Salvataggio nel DB tramite il metodo doSave del tuo ProdottoDAO
			prodottoDAO.doSave(nuovoProdotto);

			// Reindirizziamo all'area riservata con un messaggio di successo
			request.setAttribute("successMessage", "Prodotto inserito con successo nel catalogo!");
			request.getRequestDispatcher("/AreaRiservataServlet").forward(request, response);

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante l'inserimento del prodotto: " + e.getMessage());
			request.getRequestDispatcher("/AreaRiservataServlet").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}