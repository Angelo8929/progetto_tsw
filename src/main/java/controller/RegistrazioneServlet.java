package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;
import model.UtenteDAO;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrazioneServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UtenteDAO udao = new UtenteDAO();

		try {
			UtenteBean ub = new UtenteBean();

			ub.setEmail(email);
			ub.setUsername(username);
			ub.setPassword(Sicurezza.hashPassword(password));
			ub.setAdmin(false);

			udao.doSave(ub);

			System.out.println("daje");
			String success = "Registrazione avvenuta con successo!";
			request.setAttribute("successMessage", success);
			request.getRequestDispatcher("login.jsp").forward(request, response);

		} catch (SQLException e) {
			request.setAttribute("errorMessage", "errore del server durante la registrazione");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			e.printStackTrace();
		}

	}

}
