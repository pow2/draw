package bg.tusofia.draw.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.model.SiteOffer;
import bg.tusofia.draw.model.SiteOfferDAO;
import bg.tusofia.draw.model.SiteStylesDAO;
import bg.tusofia.draw.utils.GF;

public class OfferController {
	
	public static final long PAGESIZE = 50;
	
	public static String getJSArrayStyles(){
		return SiteStylesDAO.getJSArrayStyles();
	}
	
	public static boolean remove(Long offerId, Long userId){
		return SiteOfferDAO.removeById(offerId, userId);
	}
	
	public static List<SiteOffer> getNewest(HttpServletRequest request){
		List<SiteOffer> offerList = null;
		String startStr = request.getParameter("start");
		String styles = request.getParameter("styles");
		long start = startStr != null && !startStr.isEmpty() ? Integer.parseInt(startStr) : 0;
		start = start * PAGESIZE;
		if (styles != null && !styles.isEmpty()) {
			offerList = SiteOfferDAO.getStylesOffers(styles, PAGESIZE, start);
		} else {
			offerList = SiteOfferDAO.getNewest(PAGESIZE, start);
		}
		return offerList;
	}
	
	public static String drawPaginator(HttpServletRequest request, List<SiteOffer> list){
		String startStr = request.getParameter("start");
		String styles = request.getParameter("styles");
		styles = (styles != null && !styles.isEmpty()) ? GF.urlEnc(styles) : "";
		int start = startStr != null && !startStr.isEmpty() ? Integer.parseInt(startStr) : 0;
		boolean last = list.size() != PAGESIZE;
		
		if (start == 0 && last){
			return "";
		}
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("<div class=\"search-paginator\">  ");
		int k = (start > 3) ? start - 2 : 0;
		int lastPage = last ? start : start + 1;
		for (int i = k; i <= lastPage; i++){
			sb.append("<a href=\"/Draw/index.jsp?page=offers&styles=" + styles + "&start=" + i + "\" >");
			sb.append( (i+1));
			sb.append("</a>");
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("</div>");
		return sb.toString();
	}

	public static List<SiteOffer> getUserOffers(HttpServletRequest request, SessionParams sParams) {
		List<SiteOffer> offerList = null;
		String tmpUserId = request.getParameter("userid");
		long userId = -1;
		if (tmpUserId != null && !tmpUserId.isEmpty()) {
			try {
				userId = Long.parseLong(tmpUserId);
			} catch (Exception e){
				userId = -1;
			}
		} else {
			userId = sParams.getsAccount().getUser_id();
		}
		offerList = SiteOfferDAO.getUsers(userId, PAGESIZE * 10, 0);
		return offerList;
	}
}
