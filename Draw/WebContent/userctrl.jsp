<%@page import="bg.tusofia.draw.controllers.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String redirect = ActionController.getRedirect(request);
	response.sendRedirect(redirect);
%>
