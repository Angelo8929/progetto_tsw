package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CarrelloDAO {
	public CarrelloBean doRetrieveByUtente(String email) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CarrelloBean carrello = null;

		String sql = "select * from carrello where email_utente=?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, email);

			rs = ps.executeQuery();

			if (rs.next()) {
				carrello = new CarrelloBean();
				carrello.setId_carrello(rs.getInt("id_carrello"));
				carrello.setId_utente(rs.getString("email_utente"));

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
		return carrello;

	}

	public void doSave(CarrelloBean carrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		String sql = "INSERT INTO carrello (email_utente) VALUES (?)";

		try {
			con = ConnectionPool.getConnection();

			
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, carrello.getId_utente());

			ps.executeUpdate();

			
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int idGenerato = rs.getInt(1);
				carrello.setId_carrello(idGenerato); 
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
	}

	public void doUpdate(CarrelloBean carrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		
		String sql = "UPDATE carrello SET email_utente = ? WHERE id_carrello = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			
			ps.setString(1, carrello.getId_utente());
			ps.setInt(2, carrello.getId_carrello());

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

	public CarrelloBean doRetrieveByKey(int idCarrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CarrelloBean carrello = null;

		String sql = "SELECT * FROM carrello WHERE id_carrello = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, idCarrello);

			rs = ps.executeQuery();

			if (rs.next()) {
				
				carrello = new CarrelloBean();
				carrello.setId_carrello(rs.getInt("id_carrello"));
				carrello.setId_utente(rs.getString("email_utente"));
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
		return carrello;
	}

	public List<CarrelloBean> doRetrieveAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<CarrelloBean> carrelli = new java.util.ArrayList<>();
		String sql = "SELECT * FROM carrello";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				CarrelloBean carrello = new CarrelloBean();
				carrello.setId_carrello(rs.getInt("id_carrello"));
				carrello.setId_utente(rs.getString("email_utente"));

				carrelli.add(carrello);
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
		return carrelli;
	}

}
