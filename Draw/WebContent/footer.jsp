<%@page import="bg.tusofia.draw.controllers.*"
	import="bg.tusofia.draw.model.SessionParams"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
%>
<div class="footer-draw font">
	<a href="<%=ElementsController.PROJ%>index.jsp"><%=TLController.getTl(sParams, "footer.home")%></a>
	|
	<%
		if (sParams.getsAccount() != null && sParams.getsAccount().getUser_id() > 0) {
	%>
	<a href="<%=ElementsController.PROJ%>logout.jsp"><%=TLController.getTl(sParams, "footer.logout")%></a>
	<%
		} else {
	%>
	<a href="<%=ElementsController.PROJ%>logout.jsp"><%=TLController.getTl(sParams, "footer.login")%></a>
	<%
		}
	%>| <a href="<%=ElementsController.PROJ%>languages.jsp"><%=TLController.getTl(sParams, "footer.language")%></a>
	| <a href="<%=ElementsController.PROJ%>about.jsp"><%=TLController.getTl(sParams, "footer.about")%></a>
	<% String requestUri = request.getRequestURI();
	if (requestUri.contains("/img-prev/img/")){
		int idx = requestUri.indexOf("/img/");
		requestUri = requestUri.substring(idx + 5);
		idx = requestUri.indexOf("/");
		if (idx != -1) {
			String userIdfooter = requestUri.substring(0, idx);
			String imgId = requestUri.substring(idx + 1);
			if (sParams.getsAccount() != null && sParams.getsAccount().getUser_id() > 0) {
				if ( sParams.getsAccount().getUser_id() == Long.parseLong(userIdfooter)){
					%>
	|  <a href="<%=ElementsController.PROJ%>/delete/<%= userIdfooter + "/" + imgId %>"><%=TLController.getTl(sParams, "general.remove")%></a>
					<%
				}
			}
		}
	}
 	%>
</div>
<script type ="text/javascript" src="/Draw/resources/js/auth.js"></script>
 <audio id="hidden-player" loop>
  <source src="/Draw/resources/audio/さかばと 近代的自由、現代的孤高.ogg" type="audio/ogg">
</audio> 
<script>
document.getElementById("hidden-player").volume = 0.1; 
//document.getElementById('hidden-player').play();
</script>

<script type ="text/javascript" src="/Draw/resources/js/audio.js"></script>