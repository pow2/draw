function auth_verify_session() {
	$.ajax({
		type : 'GET',
		url : 'js-timer-check-alpha.jsp',
		contentType : 'application/json',
		success : function(response) {
			null;
		},
		error : function(response) {
			if (response.status == 401) {
				window.location.href = "login.jsp";
			}
		},
	});
}

setInterval(auth_verify_session, 10000);