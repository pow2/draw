<%@page import="bg.tusofia.draw.model.SiteImage"%>
<%@page import="java.util.List"%>
<%@page import="bg.tusofia.draw.model.SessionParams"
	import="bg.tusofia.draw.controllers.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
%>

<div id="pad-body">

<form method="GET" action="index.jsp" id="m-search-form-id" autocomplete="off">
<div class="loginRow">
	<input type="text" name="tags" id="s-tags" autocomplete="off"
		<%= (request.getParameter("tags") != null && !request.getParameter("tags").isEmpty()) ? "value=\"" + request.getParameter("tags") + "\"" : "placeholder=\"" + TLController.getTl(sParams, "search.input.tags") + "\""%> />
</div>
<input type="hidden" id="m-search-hidden-page"
		name="page" value="search" />
<div class="loginRow">
	<input class="btn btnImgSearch" type="submit" id="m-search-btn-id"
		value="<%=TLController.getTl(sParams, "search.input.img")%>" />
</div>
</form>

<script>
var imgTagArr = <%= ImageController.getJSArrayTags() %>;
autocomplete(document.getElementById("s-tags"), imgTagArr);
</script>

<% 
List<SiteImage> imgList = ImageController.getNewest(request);
if (imgList != null && imgList.size() > 0){ 
%>
<%@include file="imageview.jsp" %>
<% } %>

<%=ImageController.drawPaginator(request, imgList) %>
</div>
	