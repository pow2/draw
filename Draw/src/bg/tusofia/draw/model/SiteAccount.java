package bg.tusofia.draw.model;

import javax.servlet.http.HttpServletRequest;

import bg.tusofia.draw.controllers.AccountController;
import bg.tusofia.draw.utils.GF;

public class SiteAccount {
	private long user_id            = -1;
	private String username         = "";
	private String password         = "";
	private String hashedPassword   = "";
	private String email            = "";
	private int language_id         = -1;
	private String language         = "";
	private int theme_id            = -1;
	private int status_id           = -1;
	private String status           = "";
	private int privilege_id        = -1;
	private String privilege        = "";
	private String first_name       = "";
	private String last_name        = "";
	private int age                 = -1;
	private int secret_question_id  = -1;
	private String secret_answer    = "";
	private String description      = "";
	
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHashedPassword() {
		if ( GF.isNullOrEmpty(hashedPassword)){
			hashedPassword = AccountController.getHashedPassword(password);
		} else {
			hashedPassword = "";
		}
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}
	public int getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(int theme_id) {
		this.theme_id = theme_id;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public int getPrivilege_id() {
		return privilege_id;
	}
	public void setPrivilege_id(int privilege_id) {
		this.privilege_id = privilege_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setAge(String age) {
		try {
			this.age = Integer.parseInt(age.trim());
		} catch (Exception e) {
			this.age = 1;
		}
	}
	public int getSecret_question_id() {
		return secret_question_id;
	}
	public void setSecret_question_id(int secret_question_id) {
		this.secret_question_id = secret_question_id;
	}
	public String getSecret_answer() {
		return secret_answer;
	}
	public void setSecret_answer(String secret_answer) {
		this.secret_answer = secret_answer;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		if (description == null){
			description = "";
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SiteAccount() {
	}
	public SiteAccount(HttpServletRequest request) {
		  setPassword(request.getParameter("password"));
		  setUsername(request.getParameter("username"));
		  setFirst_name(request.getParameter("fname"));
		  setLast_name(request.getParameter("lname"));
		  setAge(request.getParameter("age"));
		  setEmail(request.getParameter("email"));
	}
	
}
