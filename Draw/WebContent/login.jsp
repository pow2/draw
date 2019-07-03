<%@page import="bg.tusofia.draw.utils.GF" import="bg.tusofia.draw.controllers.*"
	import="bg.tusofia.draw.model.SessionParams"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
	if (sParams.getsAccount() != null && sParams.getsAccount().getUser_id() > 0) {
		response.sendRedirect("index.jsp");
	}
%>
<!doctype html>
<html>
<head>
<%=ElementsController.generateHeader(request.getContextPath(),
					TLController.getTl(sParams, "login.headers.title"), sParams)%>
<body>
	<jsp:include page="header.jsp" />
	<div class="draw-body-wrapper font">
		<div class="draw-reg-form-wrapper font">
			<div class="draw-title"><%=TLController.getTl(sParams, "login.headers.title")%></div>
			<% if (!GF.isNullOrEmpty(request.getParameter("error"))) { %>
			<div class="draw-error"><%=request.getParameter("error")%></div>
			<% } %>
			<div class="draw-body">
				<form method="POST" action="userctrl.jsp">
					<div class="loginRow">
						<label class="control-label" for="username"><%=TLController.getTl(sParams, "login.labels.username")%>:
						</label> <input class="form-control" id="username" name="username"
							placeholder="<%=TLController.getTl(sParams, "login.input.username")%>"
							title="" value="" type="text" required>
					</div>
					<div class="loginRow">
						<label class="control-label" for="password"><%=TLController.getTl(sParams, "login.labels.password")%>:
						</label> <input class="form-control" id="password" name="password"
							placeholder="<%=TLController.getTl(sParams, "login.input.password")%>"
							title="" value="" type="password" required>
					</div>
					<input class="form-control" type="hidden" id="submit-type"
						name=submit-type value="login">
					<div class="loginRow">
						<input
							value="<%=TLController.getTl(sParams, "login.button.login")%>"
							class="btnLogin btn" type="submit">
					</div>
				</form>
				<div class="draw-free-center"><%=TLController.getTl(sParams, "login.text.or")%></div>
				<form method="GET" action="registration.jsp">
					<div class="loginRow">
						<input
							value="<%=TLController.getTl(sParams, "registration.button.register")%>"
							class="btnRegistration btn" type="submit">
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
