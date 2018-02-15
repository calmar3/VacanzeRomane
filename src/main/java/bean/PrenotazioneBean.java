package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean relativo ad una prenotazione
 * Contiene una lista di linee prenotazione ed il nome della struttura
 * che si vuole prenotare
 */
public class PrenotazioneBean {

	private List<LineaPrenotazioneBean> linee;
	private String importo;
	private String pin;
	private String nomeStruttura;
	
	/**
	 * Costruttore di default
	 */
	public PrenotazioneBean() {
		this.linee = new ArrayList<LineaPrenotazioneBean>();
		this.setImporto("0");
		this.setPin("");
		this.setNomeStruttura("");
	}

	/**
	 * GET - SET
	 */
	public List<LineaPrenotazioneBean> getLinee() {
		if (this.linee==null)
			linee = new ArrayList<LineaPrenotazioneBean>();
		return linee;
	}

	public void setLinee(List<LineaPrenotazioneBean> linee) {
		this.linee = linee;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getNomeStruttura() {
		return nomeStruttura;
	}

	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}


}
