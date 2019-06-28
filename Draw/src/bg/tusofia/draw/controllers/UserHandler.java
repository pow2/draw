package bg.tusofia.draw.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;

@WebServlet("/users/*")
public class UserHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(UserHandler.class);
	
	public UserHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String url = request.getRequestURL().toString();
			String userid = url.substring(url.lastIndexOf('/') + 1);
			response.sendRedirect("/Draw/index.jsp?page=home&userid=" + userid);
		} catch (Exception e){
			logger.error(GF.getError(e));
			response.sendRedirect("/Draw/index.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
