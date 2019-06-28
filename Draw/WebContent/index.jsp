<%@page import="bg.tusofia.draw.utils.GF"%>
<%@page import="bg.tusofia.draw.controllers.*"
	import="bg.tusofia.draw.model.SessionParams"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
	if (sParams.getsAccount() == null || sParams.getsAccount().getUser_id() < 1) {
		response.sendRedirect("login.jsp");
	}
%>
<!doctype html>
<html>
<head>
<%=ElementsController.generateHeader(request.getContextPath(),
					TLController.getTl(sParams, "index.header.title"), sParams)%>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="draw-body-wrapper">
		<jsp:include page="menu.jsp" />
		<div class="draw-body-heap" id="draw-body-heap-id">
			<%
				String currentPage = request.getParameter("page");
			%>
			<%
				if (GF.isNullOrEmpty(currentPage)|| currentPage.equals("home")) {
			%>
			<jsp:include page="home.jsp" />
			<%
				} else if (currentPage.equals("offers")) {
			%>
			<jsp:include page="offers.jsp" />
			<%
				} else if (currentPage.equals("newoffer")) {
			%>
			<jsp:include page="newoffer.jsp" />
			<%
				} else if (currentPage.equals("search")) {
			%>
			<jsp:include page="search.jsp" />
			<%
				} else if (currentPage.equals("upload")) {
			%>
			<jsp:include page="upload.jsp" />
			<%
				}
			%>
		</div>
		<jsp:include page="profilecontainer.jsp" />
	</div>
	<jsp:include page="footer.jsp" />

</body>
</html>