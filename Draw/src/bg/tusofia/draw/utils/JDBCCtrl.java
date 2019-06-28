package bg.tusofia.draw.utils;

import java.beans.Statement;
import java.io.Closeable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCCtrl {
	
	private static Logger logger = LoggerFactory.getLogger(JDBCCtrl.class);
	
	private static Connection testCon = null;
	
	public static void setTestConnection(Connection testCon){
		JDBCCtrl.testCon = testCon;
	}

	public static Connection getDatabaseConnection() {
		if (testCon != null){
			return testCon;
		}
		try {
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:jboss/datasources/KerriganDS");
			java.sql.Connection conn = ds.getConnection();
			return conn;
		} catch (Exception e) {
			logger.error("The following error occurred while trying to connect to database:" + e.getMessage());
			return null;
		}
	}

	public static void close(ResultSet... resultSets) {

		if (resultSets == null) {
			return;
		}

		for (ResultSet resultSet : resultSets) {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {
					logger.error(GF.getError(e));
				}
			}
		}

	}

	public static void close(Statement... statements) {

		if (statements == null) {
			return;
		}

		for (Statement statement : statements) {

			if (statement != null) {
				try {
					((Closeable) statement).close();
				} catch (Exception e) {
					logger.error(GF.getError(e));
				}
			}
		}

	}
	
	public static void close(PreparedStatement... statements) {

		if (statements == null) {
			return;
		}

		for (PreparedStatement statement : statements) {

			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					logger.error(GF.getError(e));
				}
			}
		}

	}

	public static void close(CallableStatement... callableStatements) {

		if (callableStatements == null) {
			return;
		}

		for (CallableStatement callableStatement : callableStatements) {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (Exception e) {
					logger.error(GF.getError(e));
				}

			}
		}
	}

	public static void close(Connection... connections) {

		if (connections == null) {
			return;
		}

		for (Connection connection : connections) {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					logger.error(GF.getError(e));
				}
			}
		}

	}

	public static String createSQLArr(int cnt) {
		StringBuilder sb = new StringBuilder(128);
		sb.append("  ");
		for (int i = 0; i < cnt; i++) {
			sb.append("?, ");
		}
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}
}
