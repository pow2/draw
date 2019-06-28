package bg.tusofia.draw.controllers;

import bg.tusofia.draw.model.SessionParams;

public class ElementsController {
	public static final String PROJ = "/Draw/";
	
	public static String generateHeader(String contPath, String title, SessionParams params){
		StringBuilder sb = new StringBuilder(2048);
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" charset=\"utf-8\"/>\r");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contPath + "/resources/css/jquery-ui.min.css\"/>\r");
		sb.append("<title>" + title + "</title>\r");
		sb.append("<script type =\"text/javascript\" src=\"" + contPath + "/resources/js/jquery-3.2.1.min.js\"></script>\r");
		//sb.append("<script type =\"text/javascript\" src=\"" + contPath + "/resources/js/jquery-ui.min.js\"></script>\r");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contPath + "/resources/css/scalable-bg.css\"/>\r");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contPath + "/resources/css/" + params.getTheme() + "\"/>\r");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contPath + "/resources/tether/css/tether.min.css\"/>\r");
		sb.append("<script type =\"text/javascript\" src=\"" + contPath + "/resources/tether/js/tether.min.js\"></script>\r");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contPath + "/resources/bootstrap/css/bootstrap.min.css\"/>\r");
		sb.append("<script type =\"text/javascript\" src=\"" + contPath + "/resources/bootstrap/js/bootstrap.js\"></script>\r");
		sb.append("<script type =\"text/javascript\" src=\"" + contPath + "/resources/js/autocomplete.js\"></script>\r");
		return sb.toString();
	}
	
	/*
	public static String generateSecretQuestions(GlobalParameters params){
		ArrayList<String> aList = FetchInformation.getSecretQuestions(params.getLanguage());
		StringBuilder sb = new StringBuilder(1024);
		sb.append("<select class=\"selectpicker\">");
		@SuppressWarnings("rawtypes")
		Iterator it = aList.iterator();
		while(it.hasNext()){
			sb.append("<option>" + (String)it.next() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	*/
}
