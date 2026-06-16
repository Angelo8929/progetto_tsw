package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrdineDAO {

	// 1. DO SAVE (Inserimento di un nuovo ordine)
	public void doSave(OrdineBean ordine) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO ordine (data_ordine, costo_totale, num_prodotti, email_utente) VALUES (?, ?, ?, ?)";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, ordine.getData_ordine());
			ps.setLong(2, ordine.getCosto_totale());
			ps.setInt(3, ordine.getNum_prodotti());
			ps.setString(4, ordine.getEmail_utente());

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

	// 2. DO UPDATE (Aggiornamento di un ordine esistente)
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

	// 3. DO RETRIEVE BY KEY (Ricerca del singolo ordine tramite ID)
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

	// 4. DO RETRIEVE ALL (Tutti gli ordini del sistema - es. per pannello Admin)
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

	// 5. DO RETRIEVE BY UTENTE (Storico ordini del singolo cliente)
	public Collection<OrdineBean> doRetrieveByUtente(String emailUtente) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrdineBean> lista = new ArrayList<>();

		// Ordinati dal più recente al più vecchio (ottimo per la UI del profilo utente)
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
}
