package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CarrelloDAO {
	public CarrelloBean doRetreiveByUtente(String email) throws SQLException {
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

		String sql = "insert into carrello values (?,?)";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, carrello.getId_carrello());
			ps.setString(2, carrello.getId_utente());

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

	public void doUpdate(CarrelloBean carrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		// Assumo che id_carrello sia la chiave primaria
		String sql = "UPDATE carrello SET email_utente = ? WHERE id_carrello = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			// ATTENZIONE: se nel DB email_utente è una stringa (come suggerisce il
			// doRetreiveByUtente),
			// dovresti usare carrello.getEmail_utente() [String].
			// Qui ho tenuto ps.setInt per consistenza con il tuo codice doSave.
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
				// FIX: Inizializzo l'oggetto prima di popolarlo altrimenti lancia
				// NullPointerException
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
