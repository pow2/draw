package bg.tusofia.draw.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.units.MemoryUnit.MB;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.model.SiteImage;
import bg.tusofia.draw.model.SiteImageDAO;
import bg.tusofia.draw.model.SiteTagsDAO;
import bg.tusofia.draw.utils.GF;

public class ImageController {
	
	public static final long PAGESIZE = 50;

	public static final String THUMB = "_thumb";
	
	private static Map<String, SiteImage> CACHE = null;
	
	static {
		CACHE = new HashMap<String, SiteImage>(1024);
	}

	/*
	private static CacheManager cacheManager=null;
	private static Cache<String, SiteImage> CACHE = null;

	static {
		try {
			cacheManager = newCacheManagerBuilder()
			      .withCache("CACHE", newCacheConfigurationBuilder(String.class, SiteImage.class, heap(50).offheap(0, MB)))
			      .build(true);
			CACHE = cacheManager.getCache("CACHE", String.class, SiteImage.class);
		} catch (Exception e){
			
		}
	}*/

	private static synchronized SiteImage getFromCache(String key) {
		return CACHE.containsKey(key) ? CACHE.get(key) : null;
	}

	private static synchronized void putInCache(String key, SiteImage value) {
		if (!CACHE.containsKey(key)) {
			if (CACHE.size() > 1023){
				CACHE = new HashMap<String, SiteImage>(1024);
			}
			CACHE.put(key, value);
		}
	}

	public static SiteImage fetchImage(String requestUri) {
		SiteImage img = null;
		if (requestUri != null && !requestUri.isEmpty()) {
			try {
				int idx = requestUri.indexOf("/img/");
				requestUri = requestUri.substring(idx + 5);
				idx = requestUri.indexOf("/");
				if (idx == -1) {
					return img;
				}
				String userId = requestUri.substring(0, idx);
				String imgId = requestUri.substring(idx + 1);
				idx = imgId.indexOf(THUMB);

				if (idx != -1) {
					imgId = imgId.substring(0, idx);
				}

				String key = userId + "/" + imgId;
				img = getFromCache(key);
				if (img == null) {
					img = SiteImageDAO.getDrivePath(userId, imgId);
					if (img == null) {
						return img;
					}
					putInCache(key, img);
				}

			} catch (Exception e) {
				img = null;
			}
		}
		return img;
	}
	
	public static String getJSArrayTags(){
		return SiteTagsDAO.getJSArrayTags();
	}
	
	public static void deleteImage(String userId, String imgId){
		SiteImageDAO.deleteImage(userId, imgId);
	}
	
	public static List<SiteImage> getNewest(HttpServletRequest request){
		List<SiteImage> imgList = null;
		String startStr = request.getParameter("start");
		String tags = request.getParameter("tags");
		long start = startStr != null && !startStr.isEmpty() ? Integer.parseInt(startStr) : 0;
		start = start * PAGESIZE;
		if (tags != null && !tags.isEmpty()) {
			imgList = SiteImageDAO.getTagsImages(tags, PAGESIZE, start);
		} else {
			imgList = SiteImageDAO.getNewest(PAGESIZE, start);
		}
		return imgList;
	}
	
	public static String drawPaginator(HttpServletRequest request, List<SiteImage> list){
		String startStr = request.getParameter("start");
		String tags = request.getParameter("tags");
		tags = (tags != null && !tags.isEmpty()) ? GF.urlEnc(tags) : "";
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
			sb.append("<a href=\"/Draw/index.jsp?page=search&tags=" + tags + "&start=" + i + "\" >");
			sb.append( (i+1));
			sb.append("</a>");
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("</div>");
		return sb.toString();
	}

	public static List<SiteImage> getUserImages(HttpServletRequest request, SessionParams sParams) {
		List<SiteImage> imgList = null;
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
		
		imgList = SiteImageDAO.getUsers(userId, PAGESIZE * 10, 0);
		return imgList;
	}
}
