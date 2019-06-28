<%@page import="bg.tusofia.draw.utils.MD5Checksum"
	import="bg.tusofia.draw.model.SessionParams"
	import="bg.tusofia.draw.controllers.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
%>
<div class="header-draw font">
	<div class="float-right">
		<div class="header-draw-uname font"><%=sParams.getsAccount().getUsername()%></div>
		<div class="header-draw-avatar">
			<img class="header-draw-avatarimg"
				src="https://www.gravatar.com/avatar/<%=MD5Checksum.getMD5Checksum(sParams.getsAccount().getEmail())%>?s=256" />
		</div>
	</div>
</div>
