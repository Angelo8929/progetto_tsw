package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

	private static List<Connection> freeDbConnections;

	static String url = "jdbc:mysql://localhost:3306/progetto_tsw"
			+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimecode=false&serverTimezone=UTC";

	static String username = "root";
	static String password = "Angelo1!";

	static {
		freeDbConnections = new LinkedList<Connection>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found!" + e);
		}
	}

	private static Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		newConnection = DriverManager.getConnection(url, username, password);
		newConnection.setAutoCommit(true);
		return newConnection;
	}

	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;
		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			ConnectionPool.freeDbConnections.remove(0);
			try {
				if (connection.isClosed())
					connection = ConnectionPool.getConnection();
			} catch (SQLException e) {
				connection = ConnectionPool.getConnection();
			}
		} else
			connection = ConnectionPool.createDBConnection();
		return connection;
	}

	public static synchronized void releaseConnection(Connection connection) {
		ConnectionPool.freeDbConnections.add(connection);
	}

}
