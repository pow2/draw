package bg.tusofia.draw.controllers;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.jsp" })
public class AuthFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	public AuthFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	private static final String LOGIN = "/login.jsp";
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// check whether session variable is set
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);

			String reqURI = req.getRequestURI();
			
			if (reqURI.toLowerCase().contains("js-timer-check-alpha")) {
				res.setContentType("application/json; charset=utf-8");
				res.setStatus((ses == null || ses.getAttribute(SessionController.ATT_SPARAMS) == null) ? HttpServletResponse.SC_UNAUTHORIZED : HttpServletResponse.SC_OK);
				res.getWriter().append("{ \"auth\" : \"OK\"}");
				return;
			}
			
			String loginURL = req.getContextPath() + LOGIN;

			if (reqURI.contains(LOGIN) && (ses == null || ses.getAttribute(SessionController.ATT_SPARAMS) == null) ){
				chain.doFilter(request, response);
			} else if (reqURI.indexOf(LOGIN) >= 0 && (ses != null && ses.getAttribute(SessionController.ATT_SPARAMS) != null)) {
			res.sendRedirect(req.getContextPath() + LOGIN);
				
			} else if (ses != null && ses.getAttribute(SessionController.ATT_SPARAMS) != null) {
				chain.doFilter(request, response);
			} else {
				
					res.sendRedirect(loginURL);
			}
		} catch (Exception e) {
			logger.error(GF.getError(e));
		}
	}

	@Override
	public void destroy() {

	}
}