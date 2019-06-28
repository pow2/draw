
<%@page import="bg.tusofia.draw.controllers.*"
	import="bg.tusofia.draw.model.SessionParams"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	SessionParams params = SessionController.loadSession(request);
	params.setLanguage(request.getParameter("language"));
	if (params.getLanguage() != null) {
		TLController.setLanguage(response, params);
	}
	String redirectURL = ElementsController.PROJ + "languages.jsp";
	response.sendRedirect(redirectURL);
%>