package bg.tusofia.draw.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;
import bg.tusofia.draw.utils.JDBCCtrl;

public class SiteImageDAO {

	private static Logger logger = LoggerFactory.getLogger(SiteImageDAO.class);

	public static List<SiteImage> getUsers(long userId, long limit, long offset)  {
		List<SiteImage> list = new ArrayList<SiteImage>();
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;

		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT DISTINCT im.img_id, im.user_id, im.name, im.location, us.username "
					+ "FROM kerrigan.images im JOIN kerrigan.user us ON im.user_id = us.user_id WHERE im.user_id = ? "
					+ "ORDER BY im.img_id DESC LIMIT ? OFFSET ? ");
			stmnt.setLong(1, userId);
			stmnt.setLong(2, limit);
			stmnt.setLong(3, offset);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				SiteImage img = new SiteImage();
				img.setImgId(rs.getLong(1));
				img.setLongUserId(rs.getLong(2));
				img.setName(rs.getString(3));
				img.setPath(rs.getString(4));
				img.setUsername(rs.getString(5));
				list.add(img);
			}
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return list;
	}

	public static List<SiteImage> getNewest(long limit, long offset) {
		List<SiteImage> list = new ArrayList<SiteImage>();
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;

		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT DISTINCT im.img_id, im.user_id, im.name, im.location, us.username "
					+ "FROM kerrigan.images im "
					+ "JOIN kerrigan.user us "
					+ "ON im.user_id = us.user_id ORDER BY im.img_id DESC LIMIT ? OFFSET ?");
			stmnt.setLong(1, limit);
			stmnt.setLong(2, offset);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				SiteImage img = new SiteImage();
				img.setImgId(rs.getLong(1));
				img.setLongUserId(rs.getLong(2));
				img.setName(rs.getString(3));
				img.setPath(rs.getString(4));
				img.setUsername(rs.getString(5));
				list.add(img);
			}
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return list;
	}
	
	
	
	public static List<SiteImage> getTagsImages(String tags, long limit, long offset) {
		List<SiteImage> list = new ArrayList<SiteImage>();
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;

		String[] tagsArr = tags.replaceAll("[\\,]", " ").replaceAll("[\\|]", " ").split("[\\s]");
		String sqlArr = JDBCCtrl.createSQLArr(tagsArr.length);
		
		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT DISTINCT im.img_id, im.user_id, im.name, im.location, us.username "
					+ "FROM kerrigan.images im "
					+ "JOIN kerrigan.user us "
					+ "ON im.user_id = us.user_id "
					+ "JOIN kerrigan.imgtotag itt "
					+ "ON im.img_id = itt.img_id "
					+ "JOIN kerrigan.imgtag it "
					+ "ON itt.imgtag_id = it.imgtag_id "
					+ "WHERE it.name IN (" + sqlArr + ") ORDER by im.img_id DESC LIMIT ? OFFSET ?");
			
			for (int i = 0; i < tagsArr.length; i++){
				stmnt.setString(i+1, tagsArr[i]);
			}
			
			stmnt.setLong(tagsArr.length+1, limit);
			stmnt.setLong(tagsArr.length+2, offset);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				SiteImage img = new SiteImage();
				img.setImgId(rs.getLong(1));
				img.setLongUserId(rs.getLong(2));
				img.setName(rs.getString(3));
				img.setPath(rs.getString(4));
				img.setUsername(rs.getString(5));
				list.add(img);
			}
		} catch (SQLException e) {
			logger.error(GF.getError(e));
			e.getStackTrace();
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}
		return list;
	}

	public static SiteImage getDrivePath(String userId, String imgId) {

		SiteImage img = new SiteImage();

		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;

		try {
			img.setLongUserId(Long.parseLong(userId));
			img.setImgId(Long.parseLong(imgId));

			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT DISTINCT name, location FROM kerrigan.images WHERE user_id = ? AND img_id = ?");
			stmnt.setLong(1, img.getLongUserId());
			stmnt.setLong(2, img.getImgId());
			rs = stmnt.executeQuery();
			if (rs.next()) {
				img.setName(rs.getString(1));
				img.setPath(rs.getString(2));
			}
		} catch (SQLException e) {
			img = null;
			logger.error(GF.getError(e));
			e.getStackTrace();
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return img;
	}

	public static boolean insertImage(long userId, String name, String location, List<String> tags) {
		Connection con = JDBCCtrl.getDatabaseConnection();
		PreparedStatement stmnt = null;
		long imgId = 0;
		boolean isSuccessful = false;
		try {
			con.setAutoCommit(false);
			stmnt = con.prepareStatement("INSERT INTO kerrigan.images (user_id, name, location) VALUES (?, ?, ?)");
			stmnt.setLong(1, userId);
			stmnt.setString(2, name);
			stmnt.setString(3, location);
			isSuccessful = stmnt.executeUpdate() > 0 ? true : false;
			if (!isSuccessful) {
				SQLWarning warn = stmnt.getWarnings();
				logger.error(warn.getMessage());

			}
			JDBCCtrl.close(stmnt);
			stmnt = con.prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = stmnt.executeQuery();
			if (rs.next()) {
				imgId = rs.getLong(1);
			}
			rs.close();
			con.commit();
		} catch (SQLException e) {
			logger.error(GF.getError(e));
			isSuccessful = false;
		} finally {
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con); 
		}

		if (imgId != 0 && tags != null && tags.size() > 0) {
			insertTagsForImage(imgId, tags);
		}

		return isSuccessful;
	}

	public static boolean insertTagsForImage(long imgId, List<String> tags) {
		Connection con = JDBCCtrl.getDatabaseConnection();
		PreparedStatement stmnt = null;
		boolean isSuccessful = false;
		try {
			con.setAutoCommit(false);
			stmnt = con.prepareStatement("INSERT IGNORE INTO kerrigan.imgtag (name) VALUES (?)");
			for (String tag : tags) {
				stmnt.setString(1, tag);
				stmnt.executeUpdate();
			}

			stmnt = con.prepareStatement("INSERT IGNORE INTO kerrigan.imgtotag(img_id, imgtag_id) "
					+ "VALUES (?, (SELECT it.imgtag_id FROM imgtag it WHERE it.name = ?))");
			for (String tag : tags) {
				stmnt.setLong(1, imgId);
				stmnt.setString(2, tag);
				stmnt.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			logger.error(GF.getError(e));
			isSuccessful = false;
		} finally {
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return isSuccessful;
	}

	public static void saveScaledImage(String filePath, String outputFile) {
		try {

			BufferedImage sourceImage = ImageIO.read(new File(filePath));
			int width = sourceImage.getWidth();
			int height = sourceImage.getHeight();

			if (width > height) {
				float extraSize = height - 100;
				float percentHight = (extraSize / height) * 100;
				float percentWidth = width - ((width / 100) * percentHight);
				BufferedImage img = new BufferedImage((int) percentWidth, 100, BufferedImage.TYPE_INT_RGB);
				Image scaledImage = sourceImage.getScaledInstance((int) percentWidth, 100, Image.SCALE_SMOOTH);
				img.createGraphics().drawImage(scaledImage, 0, 0, null);
				BufferedImage img2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				img2 = img.getSubimage((int) ((percentWidth - 100) / 2), 0, 100, 100);

				ImageIO.write(img2, "jpg", new File(outputFile));
			} else {
				float extraSize = width - 100;
				float percentWidth = (extraSize / width) * 100;
				float percentHight = height - ((height / 100) * percentWidth);
				BufferedImage img = new BufferedImage(100, (int) percentHight, BufferedImage.TYPE_INT_RGB);
				Image scaledImage = sourceImage.getScaledInstance(100, (int) percentHight, Image.SCALE_SMOOTH);
				img.createGraphics().drawImage(scaledImage, 0, 0, null);
				BufferedImage img2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				img2 = img.getSubimage(0, (int) ((percentHight - 100) / 2), 100, 100);

				ImageIO.write(img2, "jpg", new File(outputFile));
			}

		} catch (IOException e) {
		}
	}
}
