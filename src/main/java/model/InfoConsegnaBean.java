package model;

public class InfoConsegnaBean {
	int id_consegna;
	String via;
	int civico;
	String citta;

	String destinatario;
	String id_utente;

	public InfoConsegnaBean() {
	}

	public int getId_consegna() {
		return id_consegna;
	}

	public void setId_consegna(int id_consegna) {
		this.id_consegna = id_consegna;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public int getCivico() {
		return civico;
	}

	public void setCivico(int civico) {
		this.civico = civico;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getId_utente() {
		return id_utente;
	}

	public void setId_utente(String id_utente) {
		this.id_utente = id_utente;
	};

}
