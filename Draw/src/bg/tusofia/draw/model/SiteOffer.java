package bg.tusofia.draw.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sun.crypto.provider.TlsKeyMaterialGenerator;

import bg.tusofia.draw.utils.GF;

public class SiteOffer {
	private long offerId = 0;
	private long userId = 0;
	private String username = "";
	private String styles = "";
	private String description = "";
	private String email = "";
	private String minprice = "";
	private String maxprice = "";
	private int type = 0;
	private List<String> stylesList = null;
	

	public SiteOffer() {
	}
	
	public SiteOffer(HttpServletRequest request, SiteAccount sAccount) {
		setUserId(sAccount.getUser_id());
		setUsername(sAccount.getUsername());
		setDescription(request.getParameter("description"));
		setStyles(request.getParameter("styles"));
		setType(request.getParameter("offer-type"));
		setMinprice(request.getParameter("min-price"));
		setMaxprice(request.getParameter("max-price"));
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
		initStyles();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}
	
	public String getTypeStr() {
		if (type == 1){
			return "creates";
		} else {
			return "hires";
		}
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setType(String type) {
		if (GF.eqIC("work", type)){
			this.type = 1;
		} else {
			this.type = 0;
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void delete(){
		
	}
	
	private void initStyles(){
		if (styles == null || styles.isEmpty()){
			stylesList = new ArrayList<String>(0);
			return;
		}
		String[] arr = styles.split("[\\s,]+");
		stylesList = new ArrayList<String>(arr.length);
		for (String style : arr) {
			stylesList.add(style);
		}
	}

	public List<String> getStylesList() {
		if(stylesList == null){
			stylesList = new ArrayList<String>(0);
		}
		return stylesList;
	}
	
	public boolean insert(){
		return SiteOfferDAO.insertOffer(this);
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMinprice() {
		return minprice;
	}

	public void setMinprice(String minprice) {
		this.minprice = minprice;
	}

	public String getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(String maxprice) {
		this.maxprice = maxprice;
	}
	
	public String getUserUrl(){
		return "/Draw/users/" + userId;
	}
	
}
