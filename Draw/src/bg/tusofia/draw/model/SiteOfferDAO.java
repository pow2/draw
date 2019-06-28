package bg.tusofia.draw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;
import bg.tusofia.draw.utils.JDBCCtrl;

@SuppressWarnings("unused")
public class SiteOfferDAO {

	private static Logger logger = LoggerFactory.getLogger(SiteOfferDAO.class);

	public static SiteOffer getOfferById(long id) {
		SiteOffer offer = null;
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			con = JDBCCtrl.getDatabaseConnection();

			stmnt = con.prepareStatement("SELECT DISTINCT o.offer_id, o.styles, o.description, o.type, o.user_id, u.username "
					+ "FROM kerrigan.offers o " + "JOIN kerrigan.user u " + "ON o.user_id = u.user_id "
					+ "WHERE o.offer_id = ? ");

			stmnt.setLong(1, id);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				offer = new SiteOffer();
				offer.setOfferId(id);
				offer.setStyles(rs.getString(2));
				offer.setDescription(rs.getString(3));
				offer.setType(rs.getInt(4));
				offer.setUserId(rs.getLong(5));
				offer.setUsername(rs.getString(6));
			}

		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return offer;
	}
	
	
	private static boolean insertStylesForOffer(SiteOffer offer) {
		Connection con = JDBCCtrl.getDatabaseConnection();
		PreparedStatement stmnt = null;
		boolean isSuccessful = false;
		try {
			con.setAutoCommit(false);
			stmnt = con.prepareStatement("INSERT IGNORE INTO kerrigan.style (name) VALUES (?)");
			for (String style : offer.getStylesList()) {
				stmnt.setString(1, style);
				stmnt.executeUpdate();
			}

			stmnt = con.prepareStatement("INSERT IGNORE INTO kerrigan.offertostyle (offer_id, style_id) "
					+ "VALUES (?, (SELECT st.style_id FROM style st WHERE st.name = ?))");
			for (String style : offer.getStylesList()) {
				stmnt.setLong(1, offer.getOfferId());
				stmnt.setString(2, style);
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
	
	public static boolean insertOffer(SiteOffer offer) {
		Connection con = null;
		PreparedStatement stmnt = null;
		long offerId = 0;
		boolean isSuccessful = false;
		try {
			con = JDBCCtrl.getDatabaseConnection();
			con.setAutoCommit(false);
			
			stmnt = con.prepareStatement(" INSERT IGNORE INTO kerrigan.offers (styles, description, type, user_id, minprice, maxprice)"
					+ " VALUES (?, ?, ?, ?, ?, ?)");

			stmnt.setString(1, offer.getStyles());
			stmnt.setString(2, offer.getDescription());
			stmnt.setInt(3, offer.getType());
			stmnt.setLong(4, offer.getUserId());
			stmnt.setString(5, offer.getMinprice());
			stmnt.setString(6, offer.getMaxprice());
			isSuccessful = stmnt.executeUpdate() > 0 ? true : false;
			if (!isSuccessful) {
				SQLWarning warn = stmnt.getWarnings();
				logger.error(warn.getMessage());

			}
			JDBCCtrl.close(stmnt);
			stmnt = con.prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = stmnt.executeQuery();
			if (rs.next()) {
				offer.setOfferId(rs.getLong(1));
			}
			rs.close();
			con.commit();
			
			con.commit();
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}
		
		if (offer.getOfferId() != 0 && offer.getStylesList().size() > 0) {
			insertStylesForOffer(offer);
		}

		return isSuccessful;
	}
	

	public static List<SiteOffer> getNewest(long limit, long offset) {
		List<SiteOffer> list = new ArrayList<SiteOffer>();
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT DISTINCT o.offer_id, o.styles, o.description, "
					+ "o.type, o.user_id, u.username, u.email, o.minprice, o.maxprice "
					+ "FROM kerrigan.offers o JOIN kerrigan.user u ON o.user_id = u.user_id "
					+ "ORDER BY o.offer_id DESC "
					+ "LIMIT ? OFFSET ?");
			stmnt.setLong(1, limit);
			stmnt.setLong(2, offset);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				SiteOffer offer = new SiteOffer();
				offer.setOfferId(rs.getLong(1));
				offer.setStyles(rs.getString(2));
				offer.setDescription(rs.getString(3));
				offer.setType(rs.getInt(4));
				offer.setUserId(rs.getLong(5));
				offer.setUsername(rs.getString(6));
				offer.setEmail(rs.getString(7));
				offer.setMinprice(rs.getString(8));
				offer.setMaxprice(rs.getString(9));
				list.add(offer);
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
	
	public static List<SiteOffer> getStylesOffers(String styles, long limit, long offset) {
		List<SiteOffer> list = new ArrayList<SiteOffer>();
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;

		String[] stylesArr = styles.replaceAll("[\\,]", " ").replaceAll("[\\|]", " ").split("[\\s]");
		int len = stylesArr.length;
		String sqlArr = JDBCCtrl.createSQLArr(len);
		
		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT DISTINCT o.offer_id, o.styles, o.description, "
					+ "o.type, o.user_id, u.username, u.email, o.minprice, o.maxprice "
					+ "FROM kerrigan.offers o "
					+ "JOIN kerrigan.user u ON o.user_id = u.user_id "
					+ "JOIN kerrigan.offertostyle ots ON ots.offer_id = o.offer_id "
					+ "JOIN kerrigan.style st ON st.style_id = ots.style_id "
					+ "WHERE st.name IN (" + sqlArr + ")"
					+ "ORDER BY o.offer_id DESC "
					+ "LIMIT ? OFFSET ?");
			for (int i = 0; i < len; i++){
				stmnt.setString(i+1, stylesArr[i]);
			}
			stmnt.setLong(len+1, limit);
			stmnt.setLong(len+2, offset);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				SiteOffer offer = new SiteOffer();
				offer.setOfferId(rs.getLong(1));
				offer.setStyles(rs.getString(2));
				offer.setDescription(rs.getString(3));
				offer.setType(rs.getInt(4));
				offer.setUserId(rs.getLong(5));
				offer.setUsername(rs.getString(6));
				offer.setEmail(rs.getString(7));
				offer.setMinprice(rs.getString(8));
				offer.setMaxprice(rs.getString(9));
				list.add(offer);
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

	
	public static List<SiteOffer> getUsers(long userid, long limit, long offset) {
		List<SiteOffer> offerList = null;
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			con = JDBCCtrl.getDatabaseConnection();

			stmnt = con.prepareStatement("SELECT DISTINCT o.offer_id, o.styles, o.description, "
					+ "o.type, o.user_id, u.username, u.email, o.minprice, o.maxprice "
					+ "FROM kerrigan.offers o JOIN kerrigan.user u ON o.user_id = u.user_id "
					+ "WHERE u.user_id = ? "
					+ "ORDER BY o.offer_id DESC "
					+ "LIMIT ? OFFSET ? ");
			stmnt.setLong(1, userid);
			stmnt.setLong(2, limit);
			stmnt.setLong(3, offset);
			rs = stmnt.executeQuery();
			offerList = new ArrayList<SiteOffer>();
			while (rs.next()) {
				SiteOffer offer = new SiteOffer();
				offer.setOfferId(rs.getLong(1));
				offer.setStyles(rs.getString(2));
				offer.setDescription(rs.getString(3));
				offer.setType(rs.getInt(4));
				offer.setUserId(rs.getLong(5));
				offer.setUsername(rs.getString(6));
				offer.setEmail(rs.getString(7));
				offer.setMinprice(rs.getString(8));
				offer.setMaxprice(rs.getString(9));
				offerList.add(offer);
			}

		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return offerList;
	}
	
	
	public static boolean removeById(long offerId, long userId) {
		boolean result = false;
		SiteOffer offer = null;
		Connection con = null;
		PreparedStatement stmnt = null;
		PreparedStatement stmnt2 = null;
		try {
			con = JDBCCtrl.getDatabaseConnection();
			con.setAutoCommit(false);
			stmnt = con.prepareStatement("DELETE FROM kerrigan.offertostyle WHERE offer_id = ? ");
			stmnt.setLong(1, offerId);
			int affectedRows = stmnt.executeUpdate();

			stmnt2 = con.prepareStatement("DELETE FROM kerrigan.offers WHERE offer_id = ? AND user_id = ? ");
			stmnt2.setLong(1, offerId);
			stmnt2.setLong(2, userId);
			affectedRows = stmnt2.executeUpdate();
			if (result = (affectedRows == 1)){
				con.commit();
			} else {
				con.rollback();
			}
			
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(stmnt2);
			JDBCCtrl.close(con);
		}

		return result;
	}
}
