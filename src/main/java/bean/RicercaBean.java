package bean;


import controller.GestoreRicerche;

/**
 * Bean per la ricerca
 * Contiene i criteri utili per la ricerca di locazioni
 *
 */
public class RicercaBean {

	private String dataCheckIn;
	private String dataCheckOut;
	private String tipologiaStruttura,luogo,prezzoMin,prezzoMax,tipologiaCamera,numeroPersone;

	/**
	 * Costruttore di default
	 */
	public RicercaBean() {
		this.tipologiaStruttura="";
		this.prezzoMax="";
		this.prezzoMin="";
		this.luogo="";
		this.dataCheckIn="";
		this.dataCheckOut="";
		this.tipologiaCamera="";
		this.setNumeroPersone("");
	}

	/**
	 * Metodo per effettuare una ricerca tra le strutture presenti nel catalogo
	 * Riceve per argomento un CatalogoBean
	 * @param cb
	 * in cui vengono caricate le strutture da mostrare all'utente dopo una ricerca
	 * Verifica inoltre che non sia vuoti tutti i campi della ricerca
	 * Nel caso in cui tutti i campi sono stringa vuota viene effettuata la ricerca senza criteri
	 */
	public void effettuaRicerca(CatalogoBean cb)
	{
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		if (this.luogo.equals("") && this.prezzoMax.equals("") && this.prezzoMin.equals("")	&& this.tipologiaStruttura.equals("") 
				&& this.dataCheckIn==null && this.dataCheckOut==null && this.tipologiaCamera.equals(""))
			controllerRicerche.ricercaStrutture(cb,null);
		else
			controllerRicerche.ricercaStrutture(cb,this);
	}

	
	/**
	 * GET - SET
	 */
	public String getDataCheckIn() {
		return dataCheckIn;
	}

	public void setDataCheckIn(String dataCheckIn) {
		this.dataCheckIn = dataCheckIn;
	}

	public String getDataCheckOut() {
		return dataCheckOut;
	}

	public void setDataCheckOut(String dataCheckOut) {
		this.dataCheckOut = dataCheckOut;
	}


	public String getTipologiaStruttura() {
		return tipologiaStruttura;
	}

	public void setTipologiaStruttura(String tipologiaStruttura) {
		this.tipologiaStruttura = tipologiaStruttura;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getPrezzoMin() {
		return prezzoMin;
	}

	public void setPrezzoMin(String prezzoMin) {
		this.prezzoMin = prezzoMin;
	}

	public String getPrezzoMax() {
		return prezzoMax;
	}

	public void setPrezzoMax(String prezzoMax) {
		this.prezzoMax = prezzoMax;
	}


	public String getTipologiaCamera() {
		return tipologiaCamera;
	}

	public void setTipologiaCamera(String tipologiaCamera) {
		this.tipologiaCamera = tipologiaCamera;
	}

	public String getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(String numeroPersone) {
		this.numeroPersone = numeroPersone;
	}
}
