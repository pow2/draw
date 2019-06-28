<%@page import="bg.tusofia.draw.model.SiteImage" import="bg.tusofia.draw.model.SessionParams" import="bg.tusofia.draw.controllers.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page trimDirectiveWhitespaces="true" %>

<% 	for (int i = 0; i < imgList.size(); ++i) {
		SiteImage img = imgList.get(i);
%>
<div class="img-box-container">
	<a href="<%=img.getUrlPrev()%>" target="_blank"> <img
		src="<%=img.getThumbUrl()%>" alt="<%=img.getImgName()%>" />
		<div><%=img.getImgName()%></div>
	</a>
	<a href="<%=img.getUserUrl()%>" target="_blank">
		<div>by: <%=img.getUsername()%></div>
	</a>
</div>
<%
	}
%>