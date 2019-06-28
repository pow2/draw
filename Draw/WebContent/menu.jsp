<%@page import="bg.tusofia.draw.model.SiteAccount"%>
<%@page import="bg.tusofia.draw.utils.GF"%>
<%@page import="bg.tusofia.draw.model.SessionParams"
	import="bg.tusofia.draw.controllers.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	SessionParams sParams = SessionController.loadSession(request);
	String userid = request.getParameter("userid");
%>
<div class="draw-main-menu">
<% if (GF.isNullOrEmpty(userid)) { %>
	<form method="GET" action="userctrl.jsp" id="m-menu-form-id">
		<input class="btn-m-menu" type="submit"
			value="<%=TLController.getTl(sParams, "m-menu.input.index")%>"
			id="m-menu-index-id" /> <input class="btn-m-menu" type="submit"
			value="<%=TLController.getTl(sParams, "m-menu.input.upload")%>"
			id="m-menu-uni-id" /> <input class="btn-m-menu" type="submit"
			value="<%=TLController.getTl(sParams, "m-menu.input.search")%>"
			id="m-menu-search-id" /> <input class="btn-m-menu" type="submit"
			value="<%=TLController.getTl(sParams, "m-menu.input.checkoffer")%>"
			id="m-menu-co-id" /> <input class="btn-m-menu" type="submit"
			value="<%=TLController.getTl(sParams, "m-menu.input.regoffer")%>"
			id="m-menu-ro-id" /> <input name="submit-type" type="hidden" value="" />
	</form>
<% } else { %>
	<form method="GET" action="index.jsp" id="m-menu-form-id">
		<input class="btn-m-menu" type="submit"
			value="<%=TLController.getTl(sParams, "m-menu.input.back-to-profile")%>"/>
	</form>
	<% SiteAccount sAccount = AccountController.getSiteAccountInfo(request.getParameter("userid"));%>
	<a href="mailto:<%= sAccount.getEmail() %>>?Subject=Hello" target="_top"><button class="btn-m-menu">Send Mail</button></a>
<% } %>
</div>
<% if (GF.isNullOrEmpty(userid)) { %>
<script type="text/javascript">
	$('.btn-m-menu').click(function(e) {
		e.preventDefault();
		var btnId = $(this).attr('id');
		$('.btn-m-menu').removeClass('active-m-btn')
		var elem = $('#' + btnId);
		elem.addClass('active-m-btn');

		var url = '';
		if (btnId === 'm-menu-uni-id') {
			url = 'upload.jsp';
		} else if (btnId === 'm-menu-index-id') {
			url = 'home.jsp';
		} else if (btnId === 'm-menu-search-id') {
			url = 'search.jsp';
		} else if (btnId === 'm-menu-co-id') {
			url = 'offers.jsp';
		} else if (btnId === 'm-menu-ro-id') {
			url = 'newoffer.jsp';
		}
		$.ajax({
			type : "get",
			url : url,
			data : '',
			success : function(data) {
				$('#draw-body-heap-id').html(data);
			}
		});
	});
</script>
<% } %>