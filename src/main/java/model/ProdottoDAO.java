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
		String sql = "select * from prodotto;";

		try {
			con = ConnectionPool.getConnection();

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setId_prodotto(rs.getInt("id_prodotto"));
				prodotto.setPerc_alcol(rs.getDouble("perc_alcol"));
				prodotto.setSottocategoria(rs.getString("sottocategoria"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setEffervescenza(rs.getString("effervescenza"));
				prodotto.setFermentazione(rs.getString("fermentazione"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setImgPath(rs.getString("imgPath"));
				prodotto.setProfumo(rs.getString("profumo"));

				prodotti.add(prodotto);
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
		return prodotti;
	}

	public ProdottoBean doRetrieveByKey(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ProdottoBean prodotto = new ProdottoBean();
		String sql = "select * from prodotto where id_prodotto=?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				prodotto.setId_prodotto(rs.getInt("id_prodotto"));
				prodotto.setPerc_alcol(rs.getDouble("perc_alcol"));
				prodotto.setSottocategoria(rs.getString("sottocategoria"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setEffervescenza(rs.getString("effervescenza"));
				prodotto.setFermentazione(rs.getString("fermentazione"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setImgPath(rs.getString("imgPath"));
				prodotto.setProfumo(rs.getString("profumo"));
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
		return prodotto;
	}

	public List<ProdottoBean> doRetrieveAllFiltered(String[] categories, double priceMin, double priceMax)
			throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> prodotti = new ArrayList<>();

		// Base della query
		StringBuilder sql = new StringBuilder("SELECT * FROM prodotto WHERE (prezzo BETWEEN ? AND ?)");

		// Se sono state selezionate delle categorie, aggiungiamo la clausola IN (?, ?,
		// ...) dinamicamente
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

			// 1. Settiamo sempre i prezzi come primi parametri
			ps.setDouble(1, priceMin);
			ps.setDouble(2, priceMax);

			// 2. Settiamo le categorie dinamicamente partendo dall'indice 3
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
				prodotto.setPerc_alcol(rs.getDouble("perc_alcol"));
				prodotto.setSottocategoria(rs.getString("sottocategoria"));
				prodotto.setNome_prodotto(rs.getString("nome_prodotto"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setImgPath(rs.getString("imgPath"));
				// ... setta gli altri campi del tuo bean ...

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

		// Cerchiamo nel DB usando LIKE (es. '%vodka%')
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
}
