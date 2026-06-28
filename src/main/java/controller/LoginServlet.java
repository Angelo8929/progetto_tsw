package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;
import model.UtenteDAO;


@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UtenteDAO udao = new UtenteDAO();
		UtenteBean utenteLoggato = null;

		try {
			utenteLoggato = udao.doLogin(email, Sicurezza.hashPassword(password));
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore interno del server. Riprova più tardi.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return; // FIX: Evita di far proseguire l'esecuzione se il DB fallisce
		}

		if (utenteLoggato != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", utenteLoggato);
			session.setMaxInactiveInterval(30 * 60);
			session.setAttribute("messaggio_benvenuto", utenteLoggato.getUsername());

			
			try {
				model.CarrelloDAO carrelloDAO = new model.CarrelloDAO();
				model.ProdottoCarrelloDAO prodottoCarrelloDAO = new model.ProdottoCarrelloDAO();
				model.ProdottoDAO prodottoDAO = new model.ProdottoDAO();

				
				java.util.Map<Integer, Integer> carrelloOspite = (java.util.Map<Integer, Integer>) session.getAttribute("carrelloOspite");

				
				model.CarrelloBean carrelloUtente = carrelloDAO.doRetrieveByUtente(utenteLoggato.getEmail());
				if (carrelloUtente == null) {
					carrelloUtente = new model.CarrelloBean();
					carrelloUtente.setId_utente(utenteLoggato.getEmail());
					carrelloDAO.doSave(carrelloUtente);
				}

				
				if (carrelloOspite != null && !carrelloOspite.isEmpty()) {
					
					for (java.util.Map.Entry<Integer, Integer> entry : carrelloOspite.entrySet()) {
						int idProdotto = entry.getKey();
						int quantitaOspite = entry.getValue();

						
						model.ProdottoCarrelloBean prodottoEsistente = prodottoCarrelloDAO
								.doRetrieveByProdottoAndCarrello(idProdotto, carrelloUtente.getId_carrello());

						if (prodottoEsistente != null) {
							
							prodottoEsistente.setQuantita(prodottoEsistente.getQuantita() + quantitaOspite);
							prodottoCarrelloDAO.doUpdate(prodottoEsistente);
						} else {
							
							model.ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
							String imgPath = (prodotto != null) ? prodotto.getImgPath() : "";

							model.ProdottoCarrelloBean nuovoProdotto = new model.ProdottoCarrelloBean();
							nuovoProdotto.setId_prodotto(idProdotto);
							nuovoProdotto.setId_carrello(carrelloUtente.getId_carrello());
							nuovoProdotto.setImgPath(imgPath);
							nuovoProdotto.setQuantita(quantitaOspite);
							
							prodottoCarrelloDAO.doSave(nuovoProdotto);
						}
					}

					
					session.removeAttribute("carrelloOspite");
				}

				
				session.setAttribute("carrello", carrelloUtente);

			} catch (SQLException e) {
				System.err.println("[ERRORE MERGE CARRELLO]: " + e.getMessage());
				e.printStackTrace();
				
			}

			response.sendRedirect("home");
		} else {
			request.setAttribute("errorMessage", "email/password non validi");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
