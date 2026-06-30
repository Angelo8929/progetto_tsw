package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoOrdineDAO {

	
	public void doSave(ProdottoOrdineBean dettaglio) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO prodotto_ordine (nome_prodotto, id_ordine, id_prodotto, prezzo, quantita, iva) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, dettaglio.getNome_prodotto());
			ps.setInt(2, dettaglio.getId_ordine());
			ps.setInt(3, dettaglio.getId_prodotto());
			ps.setDouble(4, dettaglio.getPrezzo());
			ps.setInt(5, dettaglio.getQuantita());
			ps.setInt(6, dettaglio.getIva());

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

	
	public void doUpdate(ProdottoOrdineBean prodottoOrdine) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE prodotto_ordine SET nome_prodotto = ?, id_prodotto = ?, id_ordine = ?, prezzo = ?, quantita = ?, iva = ? WHERE id = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, prodottoOrdine.getNome_prodotto());
			ps.setInt(2, prodottoOrdine.getId_prodotto());
			ps.setInt(3, prodottoOrdine.getId_ordine());
			ps.setDouble(4, prodottoOrdine.getPrezzo());
			ps.setInt(5, prodottoOrdine.getQuantita());
			ps.setInt(6, prodottoOrdine.getIva());
			ps.setInt(7, prodottoOrdine.getId());

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

	
	public ProdottoOrdineBean doRetrieveByKey(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdottoOrdineBean prodottoOrdine = null;

		String sql = "SELECT * FROM prodotto_ordine WHERE id = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				prodottoOrdine = new ProdottoOrdineBean(); 
				prodottoOrdine.setId(rs.getInt("id"));
				prodottoOrdine.setNome_prodotto(rs.getString("nome_prodotto"));
				prodottoOrdine.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoOrdine.setId_ordine(rs.getInt("id_ordine"));
				prodottoOrdine.setPrezzo(rs.getFloat("prezzo"));
				prodottoOrdine.setQuantita(rs.getInt("quantita"));
				prodottoOrdine.setIva(rs.getInt("iva"));
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
		return prodottoOrdine;
	}

	
	public List<ProdottoOrdineBean> doRetrieveAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoOrdineBean> lista = new ArrayList<>();

		String sql = "SELECT * FROM prodotto_ordine";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoOrdineBean prodottoOrdine = new ProdottoOrdineBean();
				prodottoOrdine.setId(rs.getInt("id"));
				prodottoOrdine.setNome_prodotto(rs.getString("nome_prodotto"));
				prodottoOrdine.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoOrdine.setId_ordine(rs.getInt("id_ordine"));
				prodottoOrdine.setPrezzo(rs.getFloat("prezzo"));
				prodottoOrdine.setQuantita(rs.getInt("quantita"));
				prodottoOrdine.setIva(rs.getInt("iva"));

				lista.add(prodottoOrdine);
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

	
	public List<ProdottoOrdineBean> doRetrieveByOrdine(int idOrdine) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoOrdineBean> lista = new ArrayList<>();

		String sql = "SELECT * FROM prodotto_ordine WHERE id_ordine = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, idOrdine);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoOrdineBean prodottoOrdine = new ProdottoOrdineBean();
				prodottoOrdine.setId(rs.getInt("id"));
				prodottoOrdine.setNome_prodotto(rs.getString("nome_prodotto"));
				prodottoOrdine.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoOrdine.setId_ordine(rs.getInt("id_ordine"));
				prodottoOrdine.setPrezzo(rs.getFloat("prezzo"));
				prodottoOrdine.setQuantita(rs.getInt("quantita"));
				prodottoOrdine.setIva(rs.getInt("iva"));

				lista.add(prodottoOrdine);
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
