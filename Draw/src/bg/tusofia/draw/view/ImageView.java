package bg.tusofia.draw.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.controllers.ImageController;
import bg.tusofia.draw.model.SiteImage;
import bg.tusofia.draw.utils.GF;

@WebServlet(name = "ImageView", urlPatterns = { "/img/*" })
public class ImageView extends HttpServlet {
	
	private static Logger logger = LoggerFactory.getLogger(ImageView.class);
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		SiteImage img = ImageController.fetchImage(uri);
		if (img != null) {
			try {
				int idx = uri.indexOf(ImageController.THUMB);
				boolean isThumb = false;
				if (idx != -1) {
					isThumb = true;
				}
				
				String ext = img.getName().toLowerCase();
				ext = ext.substring(ext.lastIndexOf(".") + 1);
				if (ext.equalsIgnoreCase("jpg"))
					ext = "jpeg";
				response.setContentType("image/" + ext);
				
				InputStream is = isThumb ? img.getThumbIS() : img.getImageIS();
				BufferedImage bi = ImageIO.read(is);
				OutputStream os = response.getOutputStream();
				ImageIO.write(bi, ext, os);
			} catch (Exception e) {
				logger.error(GF.getError(e));
				return;
			}

		} else {
			return;
		}

	}
}