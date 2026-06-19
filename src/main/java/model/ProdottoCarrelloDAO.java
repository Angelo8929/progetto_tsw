package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProdottoCarrelloDAO {

	// 1. DO SAVE (Create)
	public void doSave(ProdottoCarrelloBean prodottoCarrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO prodotto_carrello (id_prodotto, id_carrello, quantita, imgPath) VALUES (?, ?, ?, ?)";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, prodottoCarrello.getId_prodotto());
			ps.setInt(2, prodottoCarrello.getId_carrello());
			ps.setInt(3, prodottoCarrello.getQuantita());
			ps.setString(4, prodottoCarrello.getImgPath());

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

	// 2. DO UPDATE (Update)
	public void doUpdate(ProdottoCarrelloBean prodottoCarrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE prodotto_carrello SET id_prodotto = ?, id_carrello = ?, quantita = ?, imgPath = ? WHERE id = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, prodottoCarrello.getId_prodotto());
			ps.setInt(2, prodottoCarrello.getId_carrello());
			ps.setInt(3, prodottoCarrello.getQuantita());
			ps.setString(4, prodottoCarrello.getImgPath());
			ps.setInt(5, prodottoCarrello.getId());

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

	public void doUpdateQuantity(int idCarrello, int idProdotto, int nuovaQuantita) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE prodotto_carrello SET quantita = ? WHERE id_carrello = ? AND id_prodotto = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, nuovaQuantita);
			ps.setInt(2, idCarrello);
			ps.setInt(3, idProdotto);

			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
			ConnectionPool.releaseConnection(con);
		}
	}

	// 3. DO RETRIEVE BY KEY (Read singola)
	public ProdottoCarrelloBean doRetrieveByKey(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdottoCarrelloBean prodottoCarrello = null;

		String sql = "SELECT * FROM prodotto_carrello WHERE id = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				prodottoCarrello = new ProdottoCarrelloBean(); // Inizializzazione corretta
				prodottoCarrello.setId(rs.getInt("id"));
				prodottoCarrello.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoCarrello.setId_carrello(rs.getInt("id_carrello"));
				prodottoCarrello.setQuantita(rs.getInt("quantita"));
				prodottoCarrello.setImgPath(rs.getString("imgPath"));
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
		return prodottoCarrello;
	}

	// 4. DO RETRIEVE ALL (Read totale)
	public Collection<ProdottoCarrelloBean> doRetrieveAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<ProdottoCarrelloBean> lista = new ArrayList<>();

		String sql = "SELECT * FROM prodotto_carrello";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoCarrelloBean prodottoCarrello = new ProdottoCarrelloBean();
				prodottoCarrello.setId(rs.getInt("id"));
				prodottoCarrello.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoCarrello.setId_carrello(rs.getInt("id_carrello"));
				prodottoCarrello.setQuantita(rs.getInt("quantita"));
				prodottoCarrello.setImgPath(rs.getString("imgPath"));

				lista.add(prodottoCarrello);
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

	// 5. BONUS: DO RETRIEVE BY CARRELLO (Molto utile per recuperare il contenuto di
	// un carrello specifico)
	public List<ProdottoCarrelloBean> doRetrieveByCarrello(int idCarrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoCarrelloBean> lista = new ArrayList<>();

		String sql = "SELECT * FROM prodotto_carrello WHERE id_carrello = ?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, idCarrello);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProdottoCarrelloBean prodottoCarrello = new ProdottoCarrelloBean();
				prodottoCarrello.setId(rs.getInt("id"));
				prodottoCarrello.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoCarrello.setId_carrello(rs.getInt("id_carrello"));
				prodottoCarrello.setQuantita(rs.getInt("quantita"));
				prodottoCarrello.setImgPath(rs.getString("imgPath"));

				lista.add(prodottoCarrello);
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

	public ProdottoCarrelloBean doRetrieveByProdottoAndCarrello(int id_prodotto, int id_carrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdottoCarrelloBean prodottoCarrello = null;

		String sql = "select * from prodotto_carrello where id_prodotto=? and id_carrello=?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id_prodotto);
			ps.setInt(2, id_carrello);

			rs = ps.executeQuery();
			if (rs.next()) {
				prodottoCarrello = new ProdottoCarrelloBean();

				prodottoCarrello.setId(rs.getInt("id"));
				prodottoCarrello.setId_carrello(rs.getInt("id_carrello"));
				prodottoCarrello.setId_prodotto(rs.getInt("id_prodotto"));
				prodottoCarrello.setQuantita(rs.getInt("quantita"));
				prodottoCarrello.setImgPath(rs.getString("imgPath"));

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
		return prodottoCarrello;
	}

	public void doDeleteByProdottoAndCarrello(int id_prodotto, int id_carrello) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "delete from prodotto_carrello where id_prodotto=? and id_carrello=?";

		try {
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, id_prodotto);
			ps.setInt(2, id_carrello);

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
}