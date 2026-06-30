package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrdineDAO {

	public int doSave(OrdineBean ordine) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int idGenerato = -1;

		String sql = "INSERT INTO ordine (data_ordine, costo_totale, num_prodotti, email_utente, id_consegna) VALUES (?, ?, ?, ?, ?)";

		try {
			con = ConnectionPool.getConnection();
			
			ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, ordine.getData_ordine());
			ps.setLong(2, ordine.getCosto_totale());
			ps.setInt(3, ordine.getNum_prodotti());
			ps.setString(4, ordine.getEmail_utente());
			ps.setInt(5, ordine.getId_consegna());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				idGenerato = rs.getInt(1);
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
		return idGenerato; 
	}

	
	public void doUpdate(OrdineBean ordine) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE ordine SET data_ordine = ?, costo_totale = ?, num_prodotti = ?, email_utente = ? WHERE id_ordine = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, ordine.getData_ordine());
			ps.setLong(2, ordine.getCosto_totale());
			ps.setInt(3, ordine.getNum_prodotti());
			ps.setString(4, ordine.getEmail_utente());
			ps.setInt(5, ordine.getId_ordine());

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

	
	public OrdineBean doRetrieveByKey(int idOrdine) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OrdineBean ordine = null;

		String sql = "SELECT * FROM ordine WHERE id_ordine = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, idOrdine);

			rs = ps.executeQuery();

			if (rs.next()) {
				ordine = new OrdineBean(); // Istanza per evitare NullPointerException
				ordine.setId_ordine(rs.getInt("id_ordine"));
				ordine.setData_ordine(rs.getString("data_ordine"));
				ordine.setCosto_totale(rs.getLong("costo_totale"));
				ordine.setNum_prodotti(rs.getInt("num_prodotti"));
				ordine.setEmail_utente(rs.getString("email_utente"));
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
		return ordine;
	}

	
	public List<OrdineBean> doRetrieveAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrdineBean> lista = new ArrayList<>();

		String sql = "SELECT * FROM ordine";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				OrdineBean ordine = new OrdineBean();
				ordine.setId_ordine(rs.getInt("id_ordine"));
				ordine.setData_ordine(rs.getString("data_ordine"));
				ordine.setCosto_totale(rs.getLong("costo_totale"));
				ordine.setNum_prodotti(rs.getInt("num_prodotti"));
				ordine.setEmail_utente(rs.getString("email_utente"));

				lista.add(ordine);
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
		return lista;
	}

	
	public Collection<OrdineBean> doRetrieveByUtente(String emailUtente) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrdineBean> lista = new ArrayList<>();

		
		String sql = "SELECT * FROM ordine WHERE email_utente = ? ORDER BY id_ordine DESC";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, emailUtente);

			rs = ps.executeQuery();

			while (rs.next()) {
				OrdineBean ordine = new OrdineBean();
				ordine.setId_ordine(rs.getInt("id_ordine"));
				ordine.setData_ordine(rs.getString("data_ordine"));
				ordine.setCosto_totale(rs.getLong("costo_totale"));
				ordine.setNum_prodotti(rs.getInt("num_prodotti"));
				ordine.setEmail_utente(rs.getString("email_utente"));

				lista.add(ordine);
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
		return lista;
	}

	public List<OrdineBean> doRetrieveWithFilters(String email, String dataInizio, String dataFine)
			throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrdineBean> ordini = new ArrayList<>();

		
		StringBuilder sql = new StringBuilder("SELECT * FROM ordine WHERE 1=1");

		
		if (email != null && !email.trim().isEmpty()) {
			sql.append(" AND email_utente LIKE ?");
		}
		if (dataInizio != null && !dataInizio.trim().isEmpty()) {
			sql.append(" AND data_ordine >= ?");
		}
		if (dataFine != null && !dataFine.trim().isEmpty()) {
			sql.append(" AND data_ordine <= ?");
		}

		sql.append(" ORDER BY data_ordine DESC");

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql.toString());

			int i = 1;
			if (email != null && !email.trim().isEmpty()) {
				ps.setString(i++, "%" + email.trim() + "%");
			}
			if (dataInizio != null && !dataInizio.trim().isEmpty()) {
				ps.setString(i++, dataInizio); // Formato standard HTML5 date: YYYY-MM-DD
			}
			if (dataFine != null && !dataFine.trim().isEmpty()) {
				ps.setString(i++, dataFine);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				OrdineBean ordine = new OrdineBean();
				ordine.setId_ordine(rs.getInt("id_ordine"));
				ordine.setEmail_utente(rs.getString("email_utente"));
				ordine.setData_ordine(rs.getString("data_ordine")); 
				ordine.setNum_prodotti(rs.getInt("num_prodotti"));
				ordine.setCosto_totale(rs.getInt("costo_totale")); 

				ordini.add(ordine);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
		return ordini;
	}
}
