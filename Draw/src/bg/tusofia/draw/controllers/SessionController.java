package bg.tusofia.draw.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.utils.GF;

public class SessionController {

	private static final int sessionKeepAlive = 60 * 60 * 24 * 30;
	private static final int sessionKeepAlive_noLog = 60 * 30;
	
	public static final String ATT_SPARAMS = "S-PARAMS";
	
	public SessionController(){
		
	}
	
	public static void killSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session != null) {
		    session.invalidate();
		}
	}
	
	public static SessionParams loadSession(HttpServletRequest request){
		HttpSession hSession = request.getSession();
		SessionParams sParams;
		Object tmpParams;
		if ( (tmpParams = hSession.getAttribute(ATT_SPARAMS)) != null ){
			sParams = (SessionParams) tmpParams;
			hSession.setMaxInactiveInterval(sessionKeepAlive);
		} else {
			sParams = new SessionParams();
			hSession.setAttribute(ATT_SPARAMS, sParams);
			hSession.setMaxInactiveInterval(sessionKeepAlive_noLog);
		}
		if (  GF.isNullOrEmpty(sParams.getLanguage()) ) {
			sParams.setLanguage(TLController.getLanguage(request));
		}
		return sParams;
	}
	
}
