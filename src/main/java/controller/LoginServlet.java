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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UtenteDAO udao = new UtenteDAO();
		UtenteBean utenteLoggato = null;

		try {
			utenteLoggato = udao.doLogin(email, Sicurezza.hashPassword(password));
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

		if (utenteLoggato != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", utenteLoggato);
			session.setMaxInactiveInterval(30 * 60);

			session.setAttribute("messaggio_benvenuto", utenteLoggato.getUsername());
			response.sendRedirect("index.jsp");
		} else {
			request.setAttribute("errorMessage", "email/password non validi");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
