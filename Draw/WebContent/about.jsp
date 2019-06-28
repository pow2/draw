<%@page import="bg.tusofia.draw.controllers.*"
	import="bg.tusofia.draw.model.SessionParams"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams params = SessionController.loadSession(request);
%>
<!doctype html>
<html>
<head>
<%=ElementsController.generateHeader(request.getContextPath(),
					TLController.getTl(params, "about.headers.title"), params)%>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="draw-body-wrapper font">
		<div class="draw-reg-form-wrapper font">
			<div class="draw-title"><%=TLController.getTl(params, "about.headers.title")%></div>
			<div class="draw-body">
				<div class="draw-free-center"><%=TLController.getTl(params, "about.description")%></div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>