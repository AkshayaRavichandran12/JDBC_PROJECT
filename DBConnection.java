package exp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	static final String Db_url="jdbc:mysql://localhost:3306/expensedb";
	static final String uname = "root";
	static final String pass = "1204";
	static Connection conn = null;
	
	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(Db_url,uname, pass);
			System.out.println("Successfully connected...");
		}catch(Exception e) {
			System.out.println(e);
		}
		return conn;
	}
	public static void main(String[] args) {
		getConnection();
	}

}