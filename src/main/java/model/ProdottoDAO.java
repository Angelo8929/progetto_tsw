package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

	public List<ProdottoBean> doRetrieveAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ProdottoBean> prodotti = new ArrayList<>();
		String sql = "SELECT * FROM prodotto;";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setId_prodotto(rs.getInt("id_prodotto"));
				prodotto.setPerc_alcol(rs.getDouble("perc_alcol"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				prodotto.setEffervescenza(rs.getString("effervescenza"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setCategoria(rs.getString("categoria"));
				prodotto.setImgPath(rs.getString("imgPath"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setIva(rs.getInt("iva"));
				prodotto.setDisponibilita(rs.getInt("disponibilita"));

				prodotti.add(prodotto);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
		return prodotti;
	}

	public ProdottoBean doRetrieveByKey(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ProdottoBean prodotto = null;
		String sql = "SELECT * FROM prodotto WHERE id_prodotto=?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				prodotto = new ProdottoBean();
				prodotto.setId_prodotto(rs.getInt("id_prodotto"));
				prodotto.setPerc_alcol(rs.getInt("perc_alcol"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				prodotto.setEffervescenza(rs.getString("effervescenza"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setCategoria(rs.getString("categoria"));
				prodotto.setImgPath(rs.getString("imgPath"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setIva(rs.getInt("iva"));
				prodotto.setDisponibilita(rs.getInt("disponibilita"));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
		return prodotto;
	}

	public List<ProdottoBean> doRetrieveAllFiltered(String[] categories, double priceMin, double priceMax)
			throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();

		StringBuilder sql = new StringBuilder("SELECT * FROM prodotto WHERE (prezzo BETWEEN ? AND ?)");

		boolean haCategorie = (categories != null && categories.length > 0);
		if (haCategorie) {
			sql.append(" AND categoria IN (");
			for (int i = 0; i < categories.length; i++) {
				sql.append("?");
				if (i < categories.length - 1) {
					sql.append(", ");
				}
			}
			sql.append(")");
		}

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql.toString());

			ps.setDouble(1, priceMin);
			ps.setDouble(2, priceMax);

			if (haCategorie) {
				int indiceParametro = 3;
				for (String cat : categories) {
					ps.setString(indiceParametro, cat);
					indiceParametro++;
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setId_prodotto(rs.getInt("id_prodotto"));
				prodotto.setPerc_alcol(rs.getInt("perc_alcol"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				prodotto.setEffervescenza(rs.getString("effervescenza"));
				prodotto.setCategoria(rs.getString("categoria"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setImgPath(rs.getString("imgPath"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setIva(rs.getInt("iva"));
				prodotto.setDisponibilita(rs.getInt("disponibilita"));

				prodotti.add(prodotto);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
		return prodotti;
	}

	public List<ProdottoBean> doRetrieveByPrefix(String prefix) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> lista = new ArrayList<>();

		String sql = "SELECT id_prodotto, nome_prodotto FROM prodotto WHERE nome_prodotto LIKE ? LIMIT 5";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + prefix + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setId_prodotto(rs.getInt("id_prodotto"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				lista.add(prodotto);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
		return lista;
	}

	public void doSave(ProdottoBean prodotto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO prodotto (perc_alcol, nome_prodotto, effervescenza, prezzo, categoria, imgPath, descrizione, iva, disponibilita) "
				+ "VALUES (?, ?, ?, ?, ?, ?,?,?,?)";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setDouble(1, prodotto.getPerc_alcol());
			ps.setString(2, prodotto.getNome_prodotto());
			ps.setString(3, prodotto.getEffervescenza());
			ps.setDouble(4, prodotto.getPrezzo());
			ps.setString(5, prodotto.getCategoria());
			ps.setString(6, prodotto.getImgPath());
			ps.setString(7, prodotto.getDescrizione());
			ps.setInt(8, prodotto.getIva());
			ps.setInt(9, prodotto.getDisponibilita());

			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
	}

	public void doUpdate(ProdottoBean prodotto) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		// FIX: Corretta la sintassi SQL, rimosse le virgole errate e aggiunta la
		// categoria
		String sql = "UPDATE prodotto SET perc_alcol = ?, nome_prodotto = ?, effervescenza = ?, "
				+ "prezzo = ?, categoria = ?, imgPath = ?, descrizione=?, iva=?, disponibilita=? WHERE id_prodotto = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setDouble(1, prodotto.getPerc_alcol());
			ps.setString(2, prodotto.getNome_prodotto());
			ps.setString(3, prodotto.getEffervescenza());
			ps.setDouble(4, prodotto.getPrezzo());
			ps.setString(5, prodotto.getCategoria());
			ps.setString(6, prodotto.getImgPath());
			ps.setString(7, prodotto.getDescrizione());
			ps.setInt(8, prodotto.getIva());
			ps.setInt(9, prodotto.getDisponibilita());
			ps.setInt(10, prodotto.getId_prodotto());

			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
	}

	public void doDelete(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		// UPDATE anziché DELETE
		String sql = "UPDATE prodotto SET disponibilita = 0 WHERE id_prodotto = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
	}

	public boolean scaricaMagazzino(int idProdotto, int quantitaAcquistata) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE prodotto SET disponibilita = disponibilita - ? WHERE id_prodotto = ? AND disponibilita >= ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantitaAcquistata);
			ps.setInt(2, idProdotto);
			ps.setInt(3, quantitaAcquistata); // Sicurezza: non va sotto zero

			int righeAggiornate = ps.executeUpdate();
			return righeAggiornate > 0; // Restituisce false se il prodotto era esaurito nel frattempo
		} finally {
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
	}

}