package core.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

	private static Connection conn;
	
	protected static Connection createConnection() throws Exception {
		String host = "127.0.0.1";
		String port = "5432";
		String name = "hackathon_sebrae";
		String usuario = "postgres";
		String senha = "123456";
		
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://"+ host +":"+ port +"/"+ name, usuario, senha);
		conn.setAutoCommit(false);
		return conn;
	}

	protected static Connection startConnection() throws Exception {
		return createConnection();
	}

	protected static Connection getConnection() throws Exception {
		if (conn == null) conn = createConnection();
		return conn;
	}
}
