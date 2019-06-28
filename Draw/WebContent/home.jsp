<%@page import="bg.tusofia.draw.utils.GF"%>
<%@page import="java.util.List" import="bg.tusofia.draw.model.*"
	import="bg.tusofia.draw.controllers.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
	Long rmOffer = GF.tryParseLong(request.getParameter("rm-offer"));
%>
<div id="pad-body">


<% if (rmOffer != -1) { %>
	<div><%= TLController.getTl(sParams, OfferController.remove(rmOffer, sParams.getsAccount().getUser_id()) ? "general.success" : "general.failure") %></div>
<% } %>


<% 
List<SiteOffer> offerList = OfferController.getUserOffers(request, sParams); 
if (offerList != null && offerList.size() > 0){
%>
<div> <%= TLController.getTl(sParams, "offer.text.yours") %>: </div>
<%@include file="offerview.jsp" %>
<% } %>


<% 
List<SiteImage> imgList = ImageController.getUserImages(request, sParams);
if (imgList != null && imgList.size() > 0){ 
%>
<%@include file="imageview.jsp" %>
<% } %>

</div>