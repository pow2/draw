<%@page import="bg.tusofia.draw.controllers.SessionController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<% SessionController.killSession(request);
response.sendRedirect("login.jsp"); %>