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
			// 1. Recupero dei parametri di filtraggio inviati dal form della sidebar
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

			// 2. Controllo della logica dei filtri
			// Se ci sono filtri attivi, deleghiamo una ricerca mirata, altrimenti prendiamo
			// tutto il catalogo
			if ((categorieSelezionate != null && categorieSelezionate.length > 0)
					|| (prezzoMinParam != null && !prezzoMinParam.isEmpty())
					|| (prezzoMaxParam != null && !prezzoMaxParam.isEmpty())) {

				// Passiamo l'array delle categorie al nuovo metodo del DAO
				prodotti = prodottoDAO.doRetrieveAllFiltered(categorieSelezionate, prezzoMin, prezzoMax);
			} else {
				prodotti = prodottoDAO.doRetrieveAll();
			}

			// 3. Passiamo la lista dei prodotti come attributo della request
			request.setAttribute("prodotti", prodotti);

			// 4. Inoltriamo la richiesta alla tua pagina del catalogo (es. catalogo.jsp o
			// index.jsp)
			// Assicurati che il nome del file .jsp qui sotto sia identico a quello reale
			// del tuo progetto
			request.getRequestDispatcher("/catalogo.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel caricamento del catalogo prodotti: " + e.getMessage());
			request.getRequestDispatcher("/errore.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// Se l'utente scrive valori non numerici nei campi prezzo, ricarichiamo senza
			// filtri
			response.sendRedirect(request.getContextPath() + "/CatalogoServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}