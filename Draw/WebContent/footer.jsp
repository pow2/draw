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