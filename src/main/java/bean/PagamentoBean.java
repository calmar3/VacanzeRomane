package bean;


import controller.GestorePrenotazioni;
import exception.CameraException;
import exception.PrenotazioneException;

/**
 * Bean predisposto per il prelievo dei dati relativi ad un pagamento
 *
 */
public class PagamentoBean {

	/**
	 * Dati per pagamento con bonifico
	 */
	private String iban,swift;
	/**
	 * Dati per pagamento con carta
	 */
	private String scadenzaCarta,tipologia,circuito,numeroCarta,codice;
	
	/**
	 * Costruttore di default
	 */
	public PagamentoBean() {
		this.iban = "";
		this.swift = "";
		this.tipologia = "";
		this.circuito = "";
		this.numeroCarta = "";
		this.codice = "";
		this.scadenzaCarta = "";
	}
	
	/**
	 * Metodo per confermare una prenotazione grazie ai dati del pagamento
	 * Viene controllato che i campi non siano vuoti
	 * Genera eccezione nel caso in cui la camera non sia prenotabile
	 * oppure se il pagamento non va a buon fine
	 * @param ub
	 * @return
	 * @throws PrenotazioneException
	 * @throws CameraException
	 */
	public boolean confermaPrenotazione(UtenteBean ub) throws PrenotazioneException, CameraException
	{
		boolean pagato = false;
		if (this.tipologia.equals("Bonifico") && !this.iban.equals("") && !this.swift.equals(""))
			pagato = GestorePrenotazioni.confermaPrenotazione(this,ub);
		else if (this.tipologia.equals("Carta di credito") && 
				!this.circuito.equals("") && !this.numeroCarta.equals("") && !this.codice.equals("") && !this.scadenzaCarta.equals(""))
			pagato = GestorePrenotazioni.confermaPrenotazione(this,ub);		
		return pagato;
		
	}
	
	
	
	/**
	 * GET-SET
	 */
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getSwift() {
		return swift;
	}
	public void setSwift(String swift) {
		this.swift = swift;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public String getNumeroCarta() {
		return numeroCarta;
	}
	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getScadenzaCarta() {
		return scadenzaCarta;
	}
	public void setScadenzaCarta(String scadenzaCarta) {
		this.scadenzaCarta = scadenzaCarta;
	}
	
}
