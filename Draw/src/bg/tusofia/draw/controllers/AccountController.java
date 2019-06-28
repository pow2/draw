package bg.tusofia.draw.controllers;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.model.SessionParams;
import bg.tusofia.draw.model.SitaAccountDAO;
import bg.tusofia.draw.model.SiteAccount;
import bg.tusofia.draw.utils.GF;
import bg.tusofia.draw.utils.MD5Checksum;

public class AccountController { 
	
	private static Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	private static Mailer mailer;
	static {
		try {
			MailerBuilder.withTransportStrategy(TransportStrategy.SMTP);
			mailer = MailerBuilder.buildMailer();
		} catch (Exception e){
			
		}
	}
	
	public AccountController(){
	}
	
	public static void updateDescription(SessionParams sParams, String description){
		SiteAccount sAccount = sParams.getsAccount();
		if (sAccount.getUser_id() > 0){
			sAccount.setDescription(description);
			SitaAccountDAO.updateDescription(sAccount);
		}
	}
	
	public static boolean createAccount(SessionParams sParams){
		boolean isSuccessful = true;
		SiteAccount sAccount = sParams.getsAccount();
		if (validateAccount(sAccount)){
			sAccount.setLanguage(sParams.getLanguage());
			sAccount.setPrivilege("Basic");
			sAccount.setSecret_question_id(1);
			sAccount.setSecret_answer("");
			sAccount.setStatus("Active");
			sAccount.setTheme_id(1);
			try {
				sAccount = SitaAccountDAO.createSiteAccount(sAccount);
			} catch (Exception e) {
				logger.error(e.getMessage());
				isSuccessful = false;
				sParams.setErrorMessage(TLController.getTl(sParams, e.getMessage()));
			}
			if (isSuccessful) {
				sParams.setLanguage(sAccount.getLanguage());
			}
		} else {
			isSuccessful = false;
			sParams.setErrorMessage(TLController.getTl(sParams, "registration.error.validation"));
		}
		return isSuccessful;
	}
	
	public static boolean validateAccount(SiteAccount sAccount){
		return  ((sAccount.getAge() > 0 && sAccount.getAge() < 150) || 
				sAccount.getEmail().matches("[A-Za-z0-9_\\-\\.]{3,64}[@][\\.A-Za-z0-9]{3,64}") &&
				sAccount.getUsername().matches("[A-Za-z0-9_\\-]{3,64}") &&
				validatePassword(sAccount.getPassword()) && 
				sAccount.getFirst_name().matches("[A-Za-z]{1,64}") &&
				sAccount.getLast_name().matches("[A-Za-z]{1,64}"));
	}
	
	public static boolean validatePassword(String password){
		boolean isValid = false;
		if (password != null && password.matches(".*[A-Z]+.*") && 
				password.matches(".*[0-9]+.*") && password.matches(".*[a-z]+.*") && 
				password.matches("[A-Za-z0-9_\\-@!#]{8,64}")){
			isValid = true;
		}
		return isValid;
	}
	
	public static SiteAccount verifyAccount(String uNameOrEmail, String password){
		SiteAccount sAccount = null;
		if ( GF.isNullOrEmpty(uNameOrEmail) || GF.isNullOrEmpty(password) ){
		} else if (uNameOrEmail.contains("'") || password.contains("'")){
		} else {
			sAccount = SitaAccountDAO.getSiteAccount(uNameOrEmail, password);
			if (sAccount.getUser_id() < 0){
				sAccount = null;
			}
		}
		return sAccount;
	}
	
	public static SiteAccount getSiteAccountInfo(long userId){
		return SitaAccountDAO.getSiteAccountInfo(userId);
	}
	
	public static SiteAccount getSiteAccountInfo(String userId){
		SiteAccount sAccount = null;
		if (userId == null || userId.isEmpty()){
			return sAccount;
		}
		try {
			long userIdLong = Long.parseLong(userId);
			sAccount = SitaAccountDAO.getSiteAccountInfo(userIdLong);
		} catch (Exception e){
			sAccount = new SiteAccount();
		}
		return sAccount;
	}
	
	public static String getHashedPassword(String password){
		String hashedPassword = "";
		if ( !GF.isNullOrEmpty(password)){
			hashedPassword = MD5Checksum.getMD5Checksum(password);
		} 
		return hashedPassword;
	}
	
	
	public static boolean sendNewPassword(SiteAccount sAccount, String password){
		boolean result = false;
		try {
		Email email = EmailBuilder.startingBlank()
			    .from("Draw", "admin@tu-sofia.bg")
			    .to(sAccount.getFirst_name(), sAccount.getEmail())
			    .withSubject("Forgotten password")
			    .withPlainText("The new password is " + sAccount.getPassword())
			    .buildEmail();
			mailer.sendMail(email);
			result = true;
		} catch (Exception e){
			
		}
		return result;
	}
}
