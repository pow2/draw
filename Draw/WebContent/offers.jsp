<%@page import="bg.tusofia.draw.utils.GF" import="bg.tusofia.draw.model.SiteOffer" import="java.util.List" import="bg.tusofia.draw.model.SessionParams" import="bg.tusofia.draw.controllers.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page trimDirectiveWhitespaces="true" %>
<% SessionParams sParams = SessionController.loadSession(request); %>
<% List<SiteOffer> offerList = OfferController.getNewest(request); %>

<div id="pad-body">
<form method="GET" action="index.jsp" id="o-offer-form-id" autocomplete="off">
<div class="loginRow">
	<input type="text" name="styles" id="o-styles" autocomplete="off"
		<%= (!GF.isNullOrEmpty(request.getParameter("styles"))) ? "value=\"" + request.getParameter("styles") + "\"" : "placeholder=\"" + TLController.getTl(sParams, "offer.input.styles") + "\""%> />
</div>
<input type="hidden" id="o-offer-hidden-page"
		name="page" value="offers" />
<div class="loginRow">
	<input class="btn btnOfferSearch" type="submit" id="o-offer-btn-id"
		value="<%=TLController.getTl(sParams, "offer.button.search")%>" />
</div>
</form>
<script>
var stylesArr = <%= OfferController.getJSArrayStyles() %>;
autocomplete(document.getElementById("o-styles"), stylesArr);
</script>
<%@include file="offerview.jsp" %>
<%= OfferController.drawPaginator(request, offerList) %>
</div>