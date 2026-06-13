package model;

public class ProdottoOrdineBean {
	public int id;
	public String nome_prodotto;
	public int id_prodotto;
	public int id_ordine;
	public float prezzo;
	public int quantità;
	public int iva;

	public ProdottoOrdineBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome_prodotto() {
		return nome_prodotto;
	}

	public void setNome_prodotto(String nome_prodotto) {
		this.nome_prodotto = nome_prodotto;
	}

	public int getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(int id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public int getId_ordine() {
		return id_ordine;
	}

	public void setId_ordine(int id_ordine) {
		this.id_ordine = id_ordine;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	};

}
