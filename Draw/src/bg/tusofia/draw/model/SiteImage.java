package bg.tusofia.draw.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;

public class SiteImage {

	private static final Logger logger = LoggerFactory.getLogger(SiteImage.class);
	private static String HDD_PATH;

	static {
		try{
			HDD_PATH = GF.loadProperties("configuration/storage.properties").getProperty("location");
		} catch (Exception e){
			HDD_PATH = "D:/ImgStorage/";
		}
	}
	
	private byte[] image;
	private long longUserId;
	private String username;
	private long imgId;
	private List<String> tags;
	private String path = "";
	private String name = "";
	private String imgName = "";

	public SiteImage() {

	}


	public String getUrl(){
		return "/Draw/img/" + longUserId + "/" + imgId;
	}
	
	public String getUrlPrev(){
		return "/Draw/img-prev/img/" + longUserId + "/" + imgId;
	}
	
	public String getUserUrl(){
		return "/Draw/users/" + longUserId;
	}
	
	public String getThumbUrl(){
		return "/Draw/img/" + longUserId + "/" + imgId + "_thumb";
	}

	public SiteImage(FileItem item, long longUserId) {
		this.longUserId = longUserId;
		String userId = String.format("%018d", longUserId);
		name = item.getName();
		path = HDD_PATH + userId + "\\" + UUID.randomUUID() + "";
		save(item);
	}

	public boolean flush() {
		return SiteImageDAO.insertImage(longUserId, name, path, tags);
	}
	
	public InputStream getImageIS() throws FileNotFoundException{
		if (!path.isEmpty()){
			File file = new File(path);
			return new FileInputStream(file);
		} else {
			return null;
		}
	}
	
	public InputStream getThumbIS() throws FileNotFoundException{
		if (!path.isEmpty()){
			File file = new File(path + "_thumb");
			return new FileInputStream(file);
		} else {
			return null;
		}
	}

	private void save(FileItem item) {
		
		File savedFile = new File(path);
		File parent = savedFile.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Could not create a dir: " + parent);
		}
		try {
			item.write(savedFile);
			SiteImageDAO.saveScaledImage(path, path+"_thumb");
		} catch (Exception e) {
			logger.error(GF.getError(e));
		}
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * @return the longUserId
	 */
	public long getLongUserId() {
		return longUserId;
	}

	/**
	 * @param longUserId
	 *            the longUserId to set
	 */
	public void setLongUserId(long longUserId) {
		this.longUserId = longUserId;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the imgId
	 */
	public long getImgId() {
		return imgId;
	}

	/**
	 * @param imgId the imgId to set
	 */
	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}



	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	
	public String getImgName() {
		if (imgName == null || imgName.isEmpty()){
			return formatName();
		}
		return imgName;
	}
	
	
	private String formatName(){
		String result = "";
		if (name != null){
			int idx = name.lastIndexOf(".");
			result = name.substring(0, idx);
			if (result.length() > 14) {
				result = result.substring(0, 14);
			}
		}
		return result;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	

}
