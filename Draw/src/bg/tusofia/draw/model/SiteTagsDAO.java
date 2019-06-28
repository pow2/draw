package bg.tusofia.draw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.tusofia.draw.utils.GF;
import bg.tusofia.draw.utils.JDBCCtrl;

public class SiteTagsDAO {
	
	private static Logger logger = LoggerFactory.getLogger(SiteTagsDAO.class);
	
	private static synchronized void fetchArray(){
		tagsList = getDBTags();
		jsArr = buildJSArray(tagsList);
		lastFetch = System.currentTimeMillis();
	}
	
	public static String getJSArrayTags(){
		if ((System.currentTimeMillis() - lastFetch) > 10000 || jsArr == null || jsArr.isEmpty()){
			fetchArray();
		}
		return jsArr;
	}
	
	
	private static volatile long lastFetch = 0;
	private static volatile List<String> tagsList = null;
	private static volatile String jsArr = null;
	
	private static final Pattern qPattern = Pattern.compile("[\\\"]");
	
	private static String buildJSArray(List<String> tags){
		StringBuilder sb = new StringBuilder(2048);
		sb.append("[  ");
		
		for (String tag : tags) {
			sb.append("\"");
			sb.append(qPattern.matcher(tag).replaceAll("\\\\\""));
			sb.append("\", ");
		}
		sb.setLength(sb.length()-2);
		sb.append("]");
		return sb.toString();
	}
	
	private static List<String> getDBTags() {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			con = JDBCCtrl.getDatabaseConnection();
			stmnt = con.prepareStatement("SELECT it.name "
					+ "FROM kerrigan.imgtag it "
					+ "LIMIT ? OFFSET ?");
			
			stmnt.setLong(1, 1000);
			stmnt.setLong(2, 0);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			//sqlArr.free();
		} catch (SQLException e) {
			logger.error(GF.getError(e));
		} finally {
			JDBCCtrl.close(rs);
			JDBCCtrl.close(stmnt);
			JDBCCtrl.close(con);
		}

		return list;
	}
}
