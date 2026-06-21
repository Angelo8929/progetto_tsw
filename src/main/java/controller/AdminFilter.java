package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;

// Il filtro intercetta qualsiasi richiesta che inizi con /admin/
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
		// Inizializzazione se necessaria
	}

	public void destroy() {
		// Pulizia risorse se necessaria
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);

		boolean loggedIn = false;
		boolean isAdmin = false;

		if (session != null) {
			UtenteBean utente = (UtenteBean) session.getAttribute("user");
			if (utente != null) {
				loggedIn = true;

				// Controlla il flag isAdmin sul tuo Bean.
				// Se nel DB è un INT (0 o 1), ricordati di mutarlo in: utente.getIsAdmin() == 1
				if (utente.getIsAdmin()) {
					isAdmin = true;
				}
			}
		}

		if (loggedIn && isAdmin) {
			// L'utente è un amministratore: lascia proseguire la richiesta verso la
			// servlet/JSP
			chain.doFilter(request, response);
		} else {
			// FIX: Usiamo il forward al posto del sendRedirect per non perdere l'attributo
			// errorMessage
			request.setAttribute("errorMessage", "Accesso negato: area riservata agli amministratori.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}