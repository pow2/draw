<%@page import="bg.tusofia.draw.model.SiteOffer" import="bg.tusofia.draw.model.SessionParams" import="bg.tusofia.draw.controllers.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page trimDirectiveWhitespaces="true" %>

<table class="table">
  <thead class="thead-dark">
    <tr>
      <th class="t-id" scope="col">#</th>
      <th class="t-type" scope="col"><%=TLController.getTl(sParams, "offer.input.type")%></th>
      <th scope="col"><%=TLController.getTl(sParams, "offer.input.styles")%></th>
      <th scope="col"><%=TLController.getTl(sParams, "offer.input.description")%></th>
      <th scope="col"><%=TLController.getTl(sParams, "offer.input.user")%></th>
    </tr>
  </thead>
  <tbody>
<% 
for (int i = 0; i < offerList.size(); ++i) {
		SiteOffer offer = offerList.get(i);%>
	<tr>
      <th scope="row" class="t-id"><%=offer.getOfferId()%></th>
      <td class="t-type"><%=TLController.getTl(sParams, "offer.text." + offer.getTypeStr())%></td>
      <td><%=(offer.getStyles() != null && offer.getStyles().length() > 60) ? offer.getStyles().substring(0, 59) + "..." : offer.getStyles() %></td>
      <td><%=(offer.getDescription() != null && offer.getDescription().length() > 60) ? offer.getDescription().substring(0, 59) + "..." : offer.getDescription()%></td>
      <td>
      <% if (offer.getUserId() != sParams.getsAccount().getUser_id()) { %>
       <a href="<%= offer.getUserUrl() %>" target="_blank"><%=offer.getUsername()%><br/><%=offer.getEmail()%></a>
	  <% } else { %>
	   <a href="/Draw/index.jsp?page=home&rm-offer=<%=offer.getOfferId()%>"><button class="btn-m-menu"><%=TLController.getTl(sParams, "general.remove")%></button></a>
	  <% } %>
	  </td>
    </tr>
    
<%
	}
%>
  </tbody>
</table>