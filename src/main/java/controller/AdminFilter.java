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


@WebFilter("/admin/*")
public class AdminFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	public void destroy() {
		
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

				if (utente.getIsAdmin()) {
					isAdmin = true;
				}
			}
		}

		if (loggedIn && isAdmin) {
			
			chain.doFilter(request, response);
		} else {
			
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			request.getRequestDispatcher("/403.jsp").forward(request, response);
		}
	}
}