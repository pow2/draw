<%@page import="java.util.List" import="bg.tusofia.draw.model.SessionParams" import="bg.tusofia.draw.controllers.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page trimDirectiveWhitespaces="true" %>
<% SessionParams sParams = SessionController.loadSession(request); %>
<form method="POST" action="userctrl.jsp">
<div id="pad-body">
<div class="loginRow">
<%= TLController.getTl(sParams, "offer.input.looking") %>
</div>
<div class="loginRow">
<div class="offer-check">
  <input class="form-check-input" type="radio" name="offer-type" id="no-offer-type-1" value="work">
  <label class="form-check-label" for="no-offer-type-1">
    <%= TLController.getTl(sParams, "offer.input.work") %>
  </label>
</div>
<div class="offer-check">
  <input class="form-check-input" type="radio" name="offer-type" id="no-offer-type-2" value="worker" checked="checked">
  <label class="form-check-label" for="no-offer-type-2">
    <%= TLController.getTl(sParams, "offer.input.worker") %>
  </label>
</div>
</div>
<div class="loginRow">
<label class="control-label" for="no-styles"><%= TLController.getTl(sParams, "offer.input.styles") %>: </label>
<input autocomplete="off" class="form-control" id="no-styles" name="styles" placeholder="<%= TLController.getTl(sParams, "offer.input.styles") %>" title="" value="" type="text" required>
</div>
<div class="loginRow">
<label class="control-label" for="no-description"><%= TLController.getTl(sParams, "offer.input.description") %>: </label>
<textarea class="form-control" id="no-description" name="description" placeholder="<%= TLController.getTl(sParams, "offer.input.description") %>" rows="3"></textarea>
</div>
<input class="form-control" type="hidden" id="submit-type" name=submit-type value="newoffer">
<div class="loginRow">
<input value="<%= TLController.getTl(sParams, "offer.button.createoffer") %>" class="btnLogin btn" type="submit">
</div>
</form>

<script>
var stylesArr = <%= OfferController.getJSArrayStyles() %>;
autocomplete(document.getElementById("no-styles"), stylesArr);
</script>
<div> <%= TLController.getTl(sParams, "offer.text.yours") %>: </div>
<% List<SiteOffer> offerList = OfferController.getUserOffers(request, sParams); %>
<%@include file="offerview.jsp" %>
</div>