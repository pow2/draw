package bg.tusofia.draw.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.utils.GF;

/**
 * Servlet implementation class AjaxHandler
 */
@WebServlet("/ajaxhandler")
public class AjaxHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    SessionParams sParams = SessionController.loadSession(request);
		String submitType = request.getParameter("submit-type");
		if ( GF.eqIC(submitType, "description") ) {
			String description = request.getParameter("description");
			AccountController.updateDescription(sParams, description); 
			response.getWriter().append("{ \"status\": \"OK\"}");
		} else {
			response.getWriter().append("{ \"status\": \"ERROR\"}");
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
