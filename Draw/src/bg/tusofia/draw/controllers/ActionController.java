package bg.tusofia.draw.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.model.SiteAccount;
import bg.tusofia.draw.model.SiteOffer;
import bg.tusofia.draw.utils.GF;

public class ActionController {
	
	private static Logger logger = LoggerFactory.getLogger(ActionController.class);
	
	public static String getRedirect(HttpServletRequest request){
		String redirect = "index.jsp";
		try {
			SessionParams sParams = SessionController.loadSession(request);
			String submitType = request.getParameter("submit-type");
			if ( GF.isNullOrEmpty(submitType) ){
				redirect = "index.jsp";
			} else {
			  if ( GF.eqIC(submitType, "registration") ){
				  SiteAccount sAccount = new SiteAccount(request);
				  sParams.setsAccount(sAccount);
				  
				  if (AccountController.createAccount(sParams)){
					  redirect = "login.jsp"; 
				  } else {
					  redirect = "registration.jsp?error=" + GF.urlEnc(sParams.getErrorMessage()); 
				  }
			  } else if ( GF.eqIC(submitType, "login")) {
				  String uNameOrEmail = request.getParameter("username");
				  String password = request.getParameter("password");
				  boolean isOk = false;
				  if (!GF.isNullOrEmpty(uNameOrEmail) && !GF.isNullOrEmpty(password)){
					  //if (AccountController.validatePassword(password)){
						  SiteAccount sAccount = AccountController.verifyAccount(uNameOrEmail, AccountController.getHashedPassword(password));
						  if (sAccount != null){
							  sParams.setsAccount(sAccount);
							  isOk = true;
						  }
					  //}
				  }
				  
				  if (isOk){
					  redirect = "index.jsp";  
				  } else {
					  redirect = "login.jsp?error=InvalidCredentials";
				  }
			  } else if ( GF.eqIC(submitType, "description")) {
				  String description = request.getParameter("description");
				  AccountController.updateDescription(sParams, description);  
				  redirect = "index.jsp";
			  } else if ( GF.eqIC(submitType, "newoffer")) {
				  SiteOffer offer = new SiteOffer(request, sParams.getsAccount());
				  
				  offer.insert();
				  
				  redirect = "index.jsp";
			  } else {
				  redirect = "index.jsp";
			  }
			} 
		} catch (Exception e){
			logger.error(GF.getError(e));
			redirect = "index.jsp";
		}
		return redirect;
	}
}
