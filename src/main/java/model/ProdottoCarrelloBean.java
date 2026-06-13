package model;

public class ProdottoCarrelloBean {
	public int id;
	public int id_prodotto;
	public int id_carrello;
	public int quantita;
	public String imgPath;

	public ProdottoCarrelloBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(int id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public int getId_carrello() {
		return id_carrello;
	}

	public void setId_carrello(int id_carrello) {
		this.id_carrello = id_carrello;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
