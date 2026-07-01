package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.InfoConsegnaBean;
import model.InfoConsegnaDAO;
import model.UtenteBean;

@WebServlet("/SalvaIndirizzoServlet")
public class SalvaIndirizzoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoConsegnaDAO infoConsegnaDAO;

	public void init() throws ServletException {
		infoConsegnaDAO = new InfoConsegnaDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");

		if (utenteLoggato == null) {
			request.setAttribute("errorMessage", "Devi effettuare il login per salvare un indirizzo.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		String destinatario = request.getParameter("destinatario");
		String via = request.getParameter("via");
		String civicoParam = request.getParameter("civico");
		String citta = request.getParameter("citta");

		if (destinatario == null || via == null || civicoParam == null || citta == null || destinatario.isEmpty()
				|| via.isEmpty() || civicoParam.isEmpty() || citta.isEmpty()) {

			request.setAttribute("errorMessage", "Tutti i campi dell'indirizzo sono obbligatori.");
			request.getRequestDispatcher("/area_riservata.jsp").forward(request, response);
			return;
		}

		try {
			int civico = Integer.parseInt(civicoParam);

			InfoConsegnaBean nuovoIndirizzo = new InfoConsegnaBean();
			nuovoIndirizzo.setDestinatario(destinatario);
			nuovoIndirizzo.setVia(via);
			nuovoIndirizzo.setCivico(civico);
			nuovoIndirizzo.setCitta(citta);
			nuovoIndirizzo.setId_utente(utenteLoggato.getEmail());

			infoConsegnaDAO.doSave(nuovoIndirizzo);

			String provenienza = request.getHeader("Referer");
			if (provenienza != null
					&& (provenienza.contains("CheckoutServlet") || provenienza.contains("checkout.jsp"))) {
				response.sendRedirect(request.getContextPath() + "/CheckoutServlet");
			} else {
				response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet?success=true");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Il numero civico deve essere un valore numerico valido.");

			request.getRequestDispatcher("/area_riservata.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage",
					"Errore nel salvataggio dell'indirizzo nel database: " + e.getMessage());
			request.getRequestDispatcher("/500.jsp").forward(request, response);
		}
	}
}