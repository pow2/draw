package bg.tusofia.draw.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.controllers.ImageController;
import bg.tusofia.draw.controllers.SessionController;
import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.utils.GF;

@WebServlet(name = "ImageDelete", urlPatterns = { "/delete/*" })
public class ImageDelete extends HttpServlet {

	private static final long serialVersionUID = 1L; 
	
	private static Logger logger = LoggerFactory.getLogger(ImagePreView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		String requestUri = request.getRequestURI();
		SessionParams sParams = SessionController.loadSession(request);
		int idx = requestUri.indexOf("/delete/");
		requestUri = requestUri.substring(idx + 8);
		idx = requestUri.indexOf("/");
		if (idx != -1) {
			String userIdfooter = requestUri.substring(0, idx);
			String imgId = requestUri.substring(idx + 1);
			if (sParams.getsAccount() != null && sParams.getsAccount().getUser_id() > 0) {
				if ( sParams.getsAccount().getUser_id() == Long.parseLong(userIdfooter)){
					ImageController.deleteImage(userIdfooter, imgId);
				}
			}
		}
		

		try {
			response.sendRedirect("/Draw/index.jsp");
		} catch (Exception e) {
			logger.error(GF.getError(e));
		}

	}
}