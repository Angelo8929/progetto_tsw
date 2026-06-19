package model;

public class OrdineBean {
	public int id_ordine;
	public String data_ordine;

	public long costo_totale;
	public int num_prodotti;
	public String email_utente;
	public int id_consegna;

	public int getId_ordine() {
		return id_ordine;
	}

	public void setId_ordine(int id_ordine) {
		this.id_ordine = id_ordine;
	}

	public String getData_ordine() {
		return data_ordine;
	}

	public void setData_ordine(String data_ordine) {
		this.data_ordine = data_ordine;
	}

	public long getCosto_totale() {
		return costo_totale;
	}

	public void setCosto_totale(long costo_totale) {
		this.costo_totale = costo_totale;
	}

	public int getNum_prodotti() {
		return num_prodotti;
	}

	public void setNum_prodotti(int num_prodotti) {
		this.num_prodotti = num_prodotti;
	}

	public String getEmail_utente() {
		return email_utente;
	}

	public void setEmail_utente(String email_utente) {
		this.email_utente = email_utente;
	}

	public int getId_consegna() {
		return id_consegna;
	}

	public void setId_consegna(int id_consegna) {
		this.id_consegna = id_consegna;
	}
}
