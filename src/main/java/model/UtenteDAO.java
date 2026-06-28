package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {

	public void doSave(UtenteBean ub) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "insert into utente (email, username, password, isAdmin) values (?,?,?,?)";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, ub.getEmail());
			ps.setString(2, ub.getUsername());
			ps.setString(3, ub.getPassword());
			ps.setBoolean(4, ub.getIsAdmin());

			ps.executeUpdate();

		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				ConnectionPool.releaseConnection(con);
			}
		}
	}

	public UtenteBean doLogin(String email, String password) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UtenteBean ub = null;

		String sql = "select * from utente where email=? and password=?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, email);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				ub = new UtenteBean();
				ub.setEmail(rs.getString("email"));
				ub.setUsername(rs.getString("username"));
				ub.setPassword(rs.getString("password"));
				ub.setAdmin(rs.getBoolean("isAdmin"));
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
			} finally {
				try {
					if (ps != null)
						ps.close();
				} finally {
					ConnectionPool.releaseConnection(con);
				}
			}
		}
		return ub;
	}

	public UtenteBean doRetrieveByEmail(String email) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UtenteBean utente = null;

		
		String sql = "select * from utente where email = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, email);

			rs = ps.executeQuery();

			if (rs.next()) {
				utente = new UtenteBean();
				utente.setEmail(rs.getString("email")); 
				utente.setUsername(rs.getString("username"));
				utente.setPassword(rs.getString("password"));
				utente.setAdmin(rs.getBoolean("isAdmin"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			ConnectionPool.releaseConnection(con);
		}
		return utente;
	}
}