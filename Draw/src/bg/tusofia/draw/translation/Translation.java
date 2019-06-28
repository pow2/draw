/**
 * @author Plamen.Prangov
 * 
 * DO NOT MODIFY UNDER ANY CIRCUMSTANCES
 */
package bg.tusofia.draw.translation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;
import bg.tusofia.draw.utils.HtmlEscape;

public class Translation {
    
    private static final String TL_PATH = "trans";
    private static final String ALL_LANG_PATH = "configuration/all_languages";
    private static final String TL_DELIMITER = "=";
    private static final String LANG_NAME = "language.name";
    private static final String EMPTY = "";
    private static final String UTF8 = "UTF-8";
	private static final String DEFAULT_LANGUAGE = "English (US)";
	private static final String COOKIE_NAME = "At6KcPzN";
	private static final int INIT_CAPACITY = 4;
	private static final int EXPIRATION_TIME = 60 * 60 * 24 * 365 * 10;
    private static final int INIT_CAPACITY_TL = 64;
  
    private static HashMap<String, HashMap<String, String>> langMap = null;
    private static ArrayList<String> langList = null;
    
    
    private static Logger log = LoggerFactory.getLogger(Translation.class);
    
    public Translation(){
        sanityChecker();
    }
    
    private static void sanityChecker(){
        if (langMap == null  || langList == null){
            loadLanguageMap();
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static synchronized void loadLanguageMap(){
        if (langMap != null && langList != null){
            return;
        }
        langMap = new HashMap<String, HashMap<String, String>>(INIT_CAPACITY);
        langList = new ArrayList<String>(INIT_CAPACITY);
        ArrayList<String> aList = langFileList();//GF.fileSearch(TL_PATH, "");
        for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
            String langFile = (String) iterator.next();
            HashMap<String, String> hMap = parseLangFile(langFile);
            if (!hMap.isEmpty()){
                langList.add(hMap.get(LANG_NAME));
                langMap.put(lTr(hMap.get(LANG_NAME)), hMap);
            }
        }
        
    }
    
    private static ArrayList<String> langFileList(){
    	ArrayList<String> aList = new ArrayList<String>(INIT_CAPACITY);
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(GF.getInputStream(ALL_LANG_PATH), UTF8))) {
                String line = null;
                while ( (line = br.readLine()) != null){
                    if (!line.isEmpty()){
                        aList.add(line);
                    }
                }
        } catch (Exception e){
        }
    	return aList;
    }
    
    private static HashMap<String, String> parseLangFile(String langFile){
        HashMap<String, String> hMap = new HashMap<String, String>(INIT_CAPACITY_TL);
        String relPath = TL_PATH  + "/" + langFile;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(GF.getInputStream(relPath), UTF8))) {
                String line = null;
                while ( (line = br.readLine()) != null){
                    if (!line.isEmpty()){
                        String[] arr = line.split(TL_DELIMITER, 2);
                        if (arr.length > 1){
                            hMap.put(arr[0].trim(), arr[1].trim());
                        }
                    }
                }
        } catch (Exception e){
        	log.error(e.getMessage());
        }
        return hMap;
    }
    
    public static String getTrans(String language, String key){
        String lang = language;
        sanityChecker();
        String resp = null;
        try {
            if (langMap.containsKey(lTr(lang))){
                resp = langMap.get(lTr(lang)).get(key);
            } else {
                if (langMap.containsKey(lTr(DEFAULT_LANGUAGE))){
                    resp = langMap.get(lTr(DEFAULT_LANGUAGE)).get(key);
                }
            }
        } catch (Exception e){
        	log.error(e.getMessage());
        }
        if (resp == null){
            resp = EMPTY;
        }
        return resp;
    }
    
    private static String lTr(String language){
        return language.toLowerCase();
    }
    
    public static ArrayList<String> getLanguages(){
        sanityChecker();
        ArrayList<String> aList = new ArrayList<String>(langList);
        return aList;
    }
    public static String getReqLanguage(HttpServletRequest request){
		String resp = DEFAULT_LANGUAGE;
		Cookie[] cookies = null;
		Cookie cookie = null;
		cookies = request.getCookies();
		if( cookies != null ) {
            int len = cookies.length;
            for (int i = 0; i < len; i++) {
               cookie = cookies[i];
               if((cookie.getName()).equalsIgnoreCase(COOKIE_NAME)) {
                  resp = GF.urlDec(cookie.getValue());
                  break;
               }
            }
         } 
		return resp;
	}
	
	public static void setRespLanguage(HttpServletResponse response, String language){
		setRespLanguage(response, language, true);
	}
	
	public static void setRespLanguage(HttpServletResponse response, String language, boolean unescape){
		String lang = (unescape) ? HtmlEscape.unescape(language) : language;
		if (!langMap.containsKey(lTr(lang))){
			lang = DEFAULT_LANGUAGE;
		}
		Cookie langCookie = new Cookie(COOKIE_NAME, GF.urlEnc(lang));
		langCookie.setMaxAge(EXPIRATION_TIME);
		response.addCookie(langCookie);
	}
    
    private static void clearCache(){
    	langMap = null;
        langList = null;
    }
    
    private static final String CACHE_REFRESH = "Translation cache is refreshed";
    
    public static void cacheRefresh() {
    	clearCache();
    	loadLanguageMap();
    	log.info("Trans.cacheRefresh(): " + CACHE_REFRESH);
    }
    
}
