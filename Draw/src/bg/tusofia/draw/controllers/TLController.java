package bg.tusofia.draw.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.translation.Translation;


public class TLController {
	public static String getTl(SessionParams sParams, String key){
		return Translation.getTrans(sParams.getLanguage(), key);
	}
	public static String getLanguage(HttpServletRequest request){
		return Translation.getReqLanguage(request);
	}
	public static ArrayList<String> getAllLanguages(){
		return Translation.getLanguages();
	}
	public static void setLanguage(HttpServletResponse response, SessionParams sParams){
		Translation.setRespLanguage(response, sParams.getLanguage());
	}
}
