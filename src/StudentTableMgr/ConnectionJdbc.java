package StudentTableMgr;

import java.sql.*;

public class ConnectionJdbc {
	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	public ConnectionJdbc() {
		String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
		String jdbc_url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "scott";
		String pwd = "tiger";
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, user, pwd);
			conn.setAutoCommit(false);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
