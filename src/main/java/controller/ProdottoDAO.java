package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.ProdottoBean;

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
}
