package bg.tusofia.draw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;
import bg.tusofia.draw.utils.JDBCCtrl;

public class SitaAccountDAO {
	private static Logger logger = LoggerFactory.getLogger(SitaAccountDAO.class);
	
	public static boolean updateDescription(SiteAccount sAccount){
		Connection con = JDBCCtrl.getDatabaseConnection();
		PreparedStatement stmnt = null;
		boolean isSuccessful = false;
		try {
			stmnt = con.prepareStatement("UPDATE kerrigan.user u SET u.description = ? WHERE u.user_id = ?");
			
			stmnt.setString(1, sAccount.getDescription());
			stmnt.setLong  (2, sAccount.getUser_id());
			isSuccessful = stmnt.executeUpdate() > 0 ? true : false;
			if (!isSuccessful){
				SQLWarning warn = stmnt.getWarnings();
				logger.error(warn.getMessage());
				
			} 
		} catch (SQLException e) {
			logger.error(GF.getError(e));
			isSuccessful = false;
		} finally {
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}
		return isSuccessful;
	}
	
	
	
	public static SiteAccount getSiteAccount(String uNameOrEmail, String hashedPassword){
		return getSiteAccount(uNameOrEmail, hashedPassword, null);
	}
	
	
	
	private static SiteAccount getSiteAccount(String uNameOrEmail, String hashedPassword, Connection con){
		boolean isConnectionNeeded = (con == null) ? true : false;
		SiteAccount sa = new SiteAccount();
		if ( GF.isNullOrEmpty(uNameOrEmail) || GF.isNullOrEmpty(hashedPassword)){
			return sa;
		}
		if (isConnectionNeeded) {
			con = JDBCCtrl.getDatabaseConnection();
		}
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			stmnt = con.prepareStatement("SELECT u.user_id, u.username, u.email, u.first_name, "
						+ "u.last_name, u.age, l.language_name, p.privilege_name, u.description "
						+ "FROM kerrigan.user u "
						+ "JOIN kerrigan.language l "
						+ "ON u.language_id = l.language_id "
						+ "JOIN kerrigan.privilege p "
						+ "ON u.privilege_id = p.privilege_id "
						+ "JOIN kerrigan.status s "
						+ "ON u.status_id = s.status_id "
						+ "WHERE (u.email = ? OR u.username = ? ) AND u.password = ? "
						+ "AND s.status_name = 'Active'");
			stmnt.setString(1, uNameOrEmail);
			stmnt.setString(2, uNameOrEmail);
			stmnt.setString(3, hashedPassword);
			rs = stmnt.executeQuery();
			if (rs.next()){
				sa.setUser_id(rs.getLong(1));
				sa.setUsername(rs.getString(2));
				sa.setEmail(rs.getString(3));
				sa.setFirst_name(rs.getString(4));
				sa.setLast_name(rs.getString(5));
				sa.setAge(rs.getInt(6));
				sa.setLanguage(rs.getString(7));
				sa.setPrivilege(rs.getString(8));
				sa.setDescription(rs.getString(9));
			} 
			
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			if (isConnectionNeeded) {
				JDBCCtrl.close(con);
			}
		}
		
		return sa;
	}
	
	public static SiteAccount resetPassword(String uNameOrEmail, String newHashedPassword) throws Exception{
		SiteAccount sa = null;
		if ( GF.isNullOrEmpty(uNameOrEmail) || GF.isNullOrEmpty(newHashedPassword)){
			return sa;
		}
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("UPDATE kerrigan.user u SET u.password = ? "
					+ "WHERE u.user_id = ? OR u.email = ? ");
			stmnt.setString(1, newHashedPassword);
			stmnt.setString(2, uNameOrEmail);
			int result = stmnt.executeUpdate();
			stmnt.close();
			if (result == 0){
				throw new Exception("Not existing");
			}
			sa = getSiteAccount(uNameOrEmail,newHashedPassword, con);
			
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}
		
		return sa;
	}
	
	public static SiteAccount getSiteAccountInfo(Long userid){
		
		SiteAccount sa = new SiteAccount();
		
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		
		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT u.user_id, u.username, u.email, u.first_name, "
						+ "u.last_name, u.age, l.language_name, p.privilege_name, u.description "
						+ "FROM kerrigan.user u "
						+ "JOIN kerrigan.language l "
						+ "ON u.language_id = l.language_id "
						+ "JOIN kerrigan.privilege p "
						+ "ON u.privilege_id = p.privilege_id "
						+ "JOIN kerrigan.status s "
						+ "ON u.status_id = s.status_id "
						+ "WHERE u.user_id = ?");
			stmnt.setLong(1, userid);
			rs = stmnt.executeQuery();
			if (rs.next()){
				sa.setUser_id(rs.getLong(1));
				sa.setUsername(rs.getString(2));
				sa.setEmail(rs.getString(3));
				sa.setFirst_name(rs.getString(4));
				sa.setLast_name(rs.getString(5));
				sa.setAge(rs.getInt(6));
				sa.setLanguage(rs.getString(7));
				sa.setPrivilege(rs.getString(8));
				sa.setDescription(rs.getString(9));
			} 
			
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}
		
		return sa;
	}
	
	public static SiteAccount createSiteAccount(SiteAccount sa) throws Exception{
		boolean isSuccessful = false;
		Connection con = JDBCCtrl.getDatabaseConnection();
		if (con == null){
			throw new Exception("Cannot connect to database");
		}
		PreparedStatement stmnt = null;
		String errorMsg = "";
		try {
			stmnt = con.prepareStatement("INSERT INTO kerrigan.user" +
					"(username," +
					"password," +
					"email," + 
					"language_id," +
					"theme_id," + 
					"status_id," +
					"privilege_id," +
					"first_name," +
					"last_name," +
					"age," +
					"secret_question_id," +
					"secret_answer)" +
					"VALUES" +
					"(?," +
					"?," +
					"?," +
					"(SELECT language_id FROM kerrigan.language l WHERE l.language_name = ? LIMIT 1)," +
					"?," +
					"(SELECT status_id FROM kerrigan.status s WHERE s.status_name = ? LIMIT 1)," +
					"(SELECT privilege_id FROM kerrigan.privilege p WHERE p.privilege_name = ? LIMIT 1)," +
					"?," +
					"?," +
					"?," +
					"?," +
					"?)");
			
			stmnt.setString(1, sa.getUsername());
			stmnt.setString(2, sa.getHashedPassword());
			stmnt.setString(3, sa.getEmail());
			stmnt.setString(4, sa.getLanguage());
			stmnt.setInt(   5, sa.getTheme_id());
			stmnt.setString(6, sa.getStatus());
			stmnt.setString(7, sa.getPrivilege());
			stmnt.setString(8, sa.getFirst_name());
			stmnt.setString(9, sa.getLast_name());
			stmnt.setInt(   10, sa.getAge());
			stmnt.setInt(   11, sa.getSecret_question_id());
			stmnt.setString(12, sa.getSecret_answer());
			isSuccessful = stmnt.executeUpdate() > 0 ? true : false;
			
			if (!isSuccessful){
				SQLWarning warn = stmnt.getWarnings();
				String warnStr = warn.getMessage();
				if ( !GF.isNullOrEmpty(warnStr) && warnStr.contains("Duplicate entry")){
					warnStr = "Duplicate entry";
				}
				throw new Exception(warnStr);
			} else {
				JDBCCtrl.close(stmnt);
				stmnt = null;
				SiteAccount sAcc = getSiteAccount(sa.getUsername(), sa.getHashedPassword(), con);
				if (sAcc.getUser_id() > -1) {
					sa = sAcc;
				}
			}
		} catch (SQLException e) {
			logger.error(GF.getError(e));
			if (e.getMessage().contains("Duplicate entry")){
				errorMsg = "registration.error.dupe";
			} else {
				errorMsg = "registration.error.general";
			}
			isSuccessful = false;
		} finally {
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}
		if (!isSuccessful){
			throw new Exception(errorMsg);
		}
		return sa;
	}
}
