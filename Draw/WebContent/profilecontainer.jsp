<%@page import="bg.tusofia.draw.model.SiteAccount"%>
<%@page import="bg.tusofia.draw.utils.MD5Checksum"  import="bg.tusofia.draw.controllers.*" import="bg.tusofia.draw.model.SessionParams"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page trimDirectiveWhitespaces="true" %>
<% SessionParams sParams = SessionController.loadSession(request);
if (sParams.getsAccount() == null || sParams.getsAccount().getUser_id() < 1){
	if(!response.isCommitted()) {
		response.sendRedirect("login.jsp");
	}
} 
	SiteAccount sAccount = AccountController.getSiteAccountInfo(request.getParameter("userid"));%>
   <div class="draw-user-panel font">
		<div class="draw-up-name font"><%= (sAccount == null) ? (TLController.getTl(sParams, "up.text.hi") + " " + sParams.getsAccount().getFirst_name()) : sAccount.getUsername() %></div>
		<div class="draw-up-avatar">
			<img class="draw-up-avatarimg" src="https://www.gravatar.com/avatar/<%= MD5Checksum.getMD5Checksum( (sAccount == null)  ? sParams.getsAccount().getEmail() : sAccount.getEmail()) %>?s=256"/>
		</div>
		<form method="POST" action="userctrl.jsp" id="description-form-id">
		    <div class="draw-up-desc-title"><%= TLController.getTl(sParams, "up.text.desc.title") %></div>
			<textarea form ="description-form-id" class="up-desc" name="description" id="up-desc-id"><%= (sAccount == null) ? sParams.getsAccount().getDescription() : sAccount.getDescription() %></textarea>
			<input name="submit-type" type="hidden" value="description"/>
			<% if (sAccount == null) { %>
			<input class="btnSaveDesc btn" type="submit" value="<%= TLController.getTl(sParams, "up.button.save") %>"/>
			<% } %>
		</form>
	</div>
    <script type="text/javascript">
		var s_height = document.getElementById('up-desc-id').scrollHeight;
		s_height = s_height;
		document.getElementById('up-desc-id').setAttribute('style','height:'+s_height+'px');
		<% if (sAccount == null) { %>
		$("#description-form-id").submit(
		  function(e) {
		    var url = "ajaxhandler";
		    $.ajax({
		           type: "POST", url: url, data: $("#description-form-id").serialize(),
		           //success: function(data){alert(data);}
		         });
		    e.preventDefault(); 
		  }
		);
		<% } %>
	</script>