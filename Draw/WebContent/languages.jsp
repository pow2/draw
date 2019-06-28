<%@page import="bg.tusofia.draw.controllers.*" import="bg.tusofia.draw.model.SessionParams" import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page trimDirectiveWhitespaces="true" %>
<% SessionParams params = SessionController.loadSession(request); %>
<!doctype html><html>
<head>
<%= ElementsController.generateHeader(request.getContextPath(), TLController.getTl(params, "languages.header.title"), params) %>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="draw-body-wrapper font">
<div class="draw-reg-form-wrapper font">
      <div class="draw-title"><%= TLController.getTl(params, "languages.header.title") %></div>
      <div class="draw-body">
<div class="languagesCurrLang"><%= TLController.getTl(params, "languages.text.currlangis") %> <%= params.getLanguage() %></div><br/>
<% ArrayList<String> aList = TLController.getAllLanguages(); %>
<% int aListSize = aList.size(); 
   if (aListSize > 0)
{%>
<div class="languagesList"><% } %>
<% for (int i = 0; i < aListSize; i++)
{ %>
 <a class="draw-langugage-text" href="<%= ElementsController.PROJ %>changelanguage.jsp?language=<%= aList.get(i) %>"><%= aList.get(i) %></a><br>
<% } %>
<% if (aListSize > 0)
{%>
</div>
<% } %>
</div>
</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>