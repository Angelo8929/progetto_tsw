package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Sostituisci con il nome esatto della tua classe di connessione (es. ConnessioneDB, DriverManagerConnectionPool)
// import model.ConnectionPool; 

public class InfoConsegnaDAO {

	/**
	 * Salva un nuovo indirizzo di consegna nel database. Lascia che l'id_consegna
	 * venga generato automaticamente (AUTO_INCREMENT).
	 */
	public synchronized void doSave(InfoConsegnaBean info) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Specifichiamo solo le colonne dei dati, lasciando l'id all'AUTO_INCREMENT del
		// DB
		String sql = "INSERT INTO info_consegna (via, civico, citta, destinatario, id_utente) VALUES (?, ?, ?,?, ?)";

		try {
			con = ConnectionPool.getConnection(); // Adatta con la tua classe ConnectionPool
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, info.getVia());
			ps.setInt(2, info.getCivico());
			ps.setString(3, info.getCitta());
			ps.setString(4, info.getDestinatario());
			ps.setString(5, info.getId_utente());

			ps.executeUpdate();

			// Recuperiamo l'ID generato automaticamente dal database
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int idGenerato = rs.getInt(1);
				info.setId_consegna(idGenerato); // Aggiorna il Bean in memoria
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

	/**
	 * Recupera tutti gli indirizzi di consegna associati a uno specifico utente.
	 */
	public synchronized List<InfoConsegnaBean> doRetrieveByUtente(String emailUtente) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<InfoConsegnaBean> listaIndirizzi = new ArrayList<>();
		String sql = "SELECT * FROM info_consegna WHERE id_utente = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, emailUtente);

			rs = ps.executeQuery();

			while (rs.next()) {
				InfoConsegnaBean info = new InfoConsegnaBean();
				info.setId_consegna(rs.getInt("id_consegna"));
				info.setVia(rs.getString("via"));
				info.setCivico(rs.getInt("civico"));
				info.setCitta(rs.getString("citta"));
				info.setDestinatario(rs.getString("destinatario"));
				info.setId_utente(rs.getString("id_utente"));

				listaIndirizzi.add(info);
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

		return listaIndirizzi;
	}

}