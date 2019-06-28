<%@page import="bg.tusofia.draw.utils.GF"
	import="bg.tusofia.draw.controllers.*"
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
					TLController.getTl(params, "registration.headers.title"), params)%>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="draw-body-wrapper font">
		<div class="draw-reg-form-wrapper font">
			<div class="draw-title"><%=TLController.getTl(params, "registration.headers.title")%></div>
			<% if (!GF.isNullOrEmpty(request.getParameter("error"))) { %>
			<div class="draw-error"><%=request.getParameter("error")%></div>
			<% } %>
			<div class="draw-body">
				<form method="POST" action="userctrl.jsp">

					<div class="loginRow">
						<label class="control-label" for="username"><%=TLController.getTl(params, "registration.labels.username")%>:
						</label> <input class="form-control" id="username" name="username"
							placeholder="<%=TLController.getTl(params, "registration.input.username")%>"
							title="" value="" type="text" required>
					</div>

					<div class="loginRow">
						<label class="control-label" for="password"><%=TLController.getTl(params, "registration.labels.password")%>:
						</label> <input class="form-control" id="password" name="password"
							placeholder="<%=TLController.getTl(params, "registration.input.password")%>"
							title="" value="" type="password" required>
					</div>
					<% if (!GF.isNullOrEmpty(request.getParameter("error"))) { %>
					<div style="font-size:12px;"><%=TLController.getTl(params,"registration.error.password")%></div>
					<% } %>

					<div class="loginRow">
						<label class="control-label" for="email"><%=TLController.getTl(params, "registration.labels.email")%>:
						</label> <input class="form-control" type="email" id="email" name="email"
							placeholder="<%=TLController.getTl(params, "registration.input.email")%>"
							title="" value="" type="text" required>
					</div>

					<div class="loginRow">
						<label class="control-label" for="fname"><%=TLController.getTl(params, "registration.labels.fname")%>:
						</label> <input class="form-control" id="fname" name="fname"
							placeholder="<%=TLController.getTl(params, "registration.input.fname")%>"
							title="" value="" type="text" required>
					</div>

					<div class="loginRow">
						<label class="control-label" for="lname"><%=TLController.getTl(params, "registration.labels.lname")%>:
						</label> <input class="form-control" id="lname" name="lname"
							placeholder="<%=TLController.getTl(params, "registration.input.lname")%>"
							title="" value="" type="text" required>
					</div>


					<div class="loginRow">
						<label class="control-label" for="age"><%=TLController.getTl(params, "registration.labels.age")%>:
						</label> <input class="form-control" id="age" name="age"
							placeholder="<%=TLController.getTl(params, "registration.input.age")%>"
							title="" value="" type="text" required>
					</div>

					<input class="form-control" type="hidden" id="submit-type"
						name=submit-type value="registration" type="text">
					<!-- 
<div class="loginRow">
<label class="control-label" for="answer"><!%= TLController.getTl(params, "registration.labels.question") %>: </label>
<!%= ElementsController.generateSecretQuestions(params) %>
<input class="form-control" id="answer" name="answer" placeholder="<!%= TLController.getTl(params, "registration.input.question") %>" title="" value="" type="text" required>
</div> -->
					<div class="loginRow">
						<input
							value="<%=TLController.getTl(params, "registration.button.register")%>"
							class="btnRegistration btn" type="submit">
					</div>
				
				</form>
				<div class="draw-free-center"><%=TLController.getTl(params, "login.text.or")%></div>
				<form method="GET" action="login.jsp">
					<div class="loginRow">
						<input
							value="<%=TLController.getTl(params, "login.button.login")%>"
							class="btnLogin btn" type="submit">
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>