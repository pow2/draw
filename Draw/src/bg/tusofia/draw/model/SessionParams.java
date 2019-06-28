package bg.tusofia.draw.model;

public class SessionParams {
	private static final String EMPTY = "";
	
	private String language = EMPTY;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@SuppressWarnings("unused")
	private String theme = "";

	public String getTheme() {
		return "theme-01.css";
		//return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	private SiteAccount sAccount = null;

	public SiteAccount getsAccount() {
		if (sAccount == null) {
			sAccount = new SiteAccount();
		}
		return sAccount;
	}

	public void setsAccount(SiteAccount sAccount) {
		this.sAccount = sAccount;
	}
	
	
	private String errorMessage = "";

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
