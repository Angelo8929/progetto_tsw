package model;

public class ProdottoBean {

	public int id_prodotto;
	public String nome_prodotto;
	public double perc_alcol;
	public String effervescenza;
	public double prezzo;
	public int iva;
	public String descrizione;
	public String categoria;
	public String imgPath;
	int disponibilita;

	public int getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(int id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public String getNome_prodotto() {
		return nome_prodotto;
	}

	public void setNome_prodotto(String nome_prodotto) {
		this.nome_prodotto = nome_prodotto;
	}

	public double getPerc_alcol() {
		return perc_alcol;
	}

	public void setPerc_alcol(double perc_alcol) {
		this.perc_alcol = perc_alcol;
	}

	public String getEffervescenza() {
		return effervescenza;
	}

	public void setEffervescenza(String effervescenza) {
		this.effervescenza = effervescenza;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponiblita) {
		this.disponibilita = disponiblita;
	}

}
