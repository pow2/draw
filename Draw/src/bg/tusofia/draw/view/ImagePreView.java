package bg.tusofia.draw.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.controllers.ElementsController;
import bg.tusofia.draw.controllers.SessionController;
import bg.tusofia.draw.controllers.TLController;
import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.utils.GF;

@WebServlet(name = "ImagePreView", urlPatterns = { "/img-prev/*" })
public class ImagePreView extends HttpServlet {

	private static final long serialVersionUID = 1L; 
	
	private static Logger logger = LoggerFactory.getLogger(ImagePreView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		String uri = request.getRequestURI();
		if (uri != null && !uri.isEmpty()) {
			int idx = uri.indexOf("/img-prev/");
			uri = uri.substring(idx + 10);
		}

	    response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();

			SessionParams sParams = SessionController.loadSession(request);
			out.append("<!doctype html><html>");
			out.append("<head>");
			out.append(ElementsController.generateHeader(request.getContextPath(),
					TLController.getTl(sParams, "imgprev.header.title"), sParams));
			
			out.append("</head>");
			out.append("<body>");
			out.append("\n");

			request.getRequestDispatcher("/header.jsp").include(request, response);
			out.append("<div class=\"draw-body-wrapper\">");
			out.append("<div class=\"img-prev-wrapper\">");
			out.append("<img class=\"img-prev-img\" src=\"/Draw/" + uri + "\"/>");
			out.append("</div>");
			out.append("</div>");

			request.getRequestDispatcher("/footer.jsp").include(request, response);

			out.append("\n");
			out.append("</body>");
			out.append("</html>");

		} catch (IOException e) {
			logger.error(GF.getError(e));
		}

	}
}