package bg.tusofia.draw.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.model.SiteImage;
import bg.tusofia.draw.utils.GF;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.*;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/upload")
public class UploadHandler extends HttpServlet {

	private static Logger logger = LoggerFactory.getLogger(UploadHandler.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleFiles(request);
		response.sendRedirect("index.jsp");
	}

	private void handleFiles(HttpServletRequest request) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			logger.error("isMultipart is " + isMultipart);
		} else {
			try {
				SessionParams sParams = SessionController.loadSession(request);
				long longUserId = sParams.getsAccount().getUser_id();

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = null;
				items = upload.parseRequest(request);

				Iterator<FileItem> itr = items.iterator();
				List<SiteImage> sImages = new ArrayList<SiteImage>();
				String tags = "";
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						String field = item.getFieldName();
						String fieldValue = item.getString();
						if (GF.eqIC(field, "tags")) {
							tags = fieldValue;
						}
					} else {
						SiteImage sImage = new SiteImage(item, longUserId);
						sImages.add(sImage);
					}
				}

				List<String> listTags = getTags(tags);

				for (SiteImage sImage : sImages) {
					sImage.setTags(listTags);
					sImage.flush();
				}

			} catch (Exception e) {
				logger.error(GF.getError(e));
			}
		}
	}

	private static List<String> getTags(String tags) {
		if (!tags.isEmpty()) {
			tags = tags.replaceAll("[\\,]", " ");
			tags = tags.replaceAll("[\\|]", " ");
		}
		return Arrays.asList(tags.split("[\\s]"));
	}
}
