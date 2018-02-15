package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entity.SistemaDiPagamento;


/**
 * Entita PagamentoCartaCredito in Pagamento
 * Il nome della classe costituisce la colonna discriminante
 */
@Entity  
@Table(name="PagamentoCartaCredito")  
public class PagamentoCartaCredito extends Pagamento {

	
	
	/**
	 * Colonne nella tabella Pagamento
	 * riservate alle istanze di PagamentoCartaCredito
	 */
	@Column(name="circuito")
	private String circuito;
	
	@Column(name="numero_carta")
	private String numeroCarta;
	
	@Column(name="codice")
	private String codice;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="scadenza")
	private Date scadenzaCarta;
	

    /**
     * Costruttore di default
     */
    public PagamentoCartaCredito() {

    }
    
    /**
     * Costruttore con parametri
     * @param circuito
     * @param numeroCarta
     * @param dataScadenza 
     * @param codice 
     * @param Prenotazione da associare contenente l'importo 
     */
    public PagamentoCartaCredito(String circuito, String numeroCarta, String dataScadenza, String codice, Prenotazione p)
    {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.scadenzaCarta = new Date();
		try {
			this.scadenzaCarta = formatter.parse(dataScadenza);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	this.circuito = circuito;
    	this.numeroCarta = numeroCarta;
    	this.codice = codice;
    	super.setPrenotazione(p);
    	super.setImporto(p.getImporto());
    	this.autorizzaPagamento();
    }

	/* (non-Javadoc)
	 * @see dominio.Pagamento#autorizzaPagamento()
	 *  implementa il metodo ereditato dalla classe che estende
	 * effettuando un pagamento con le credenziali relative ad una carta di credito
	 */
	@Override
	protected boolean autorizzaPagamento() 
	{
		SistemaDiPagamento sistemaPagamento = new SistemaDiPagamento();
		if(!sistemaPagamento.convalidaCarta(super.getImporto(),this.circuito,this.numeroCarta,this.codice,this.scadenzaCarta,super.getPrenotazione().getStrutturaPrenotata().getGestore()))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see dominio.Pagamento#autorizzaRimborso(double, dominio.Gestore)
	 *  *implementa il metodo ereditato dalla classe che estende
	 * effettuando un pagamento con le credenziali relative ad una carta di credito
	 */
	@Override
	protected boolean autorizzaRimborso(double importoDaRimborsare, Gestore g)
	{
		if (importoDaRimborsare < super.importo)
			super.importo -= importoDaRimborsare; 
		SistemaDiPagamento sistemaPagamento = new SistemaDiPagamento();
		if(sistemaPagamento.accreditaSuCarta(this.numeroCarta,this.scadenzaCarta,importoDaRimborsare,g.getCodiceContoCorrente()))
			return true;
		return false;
	}
    
	/**
	 * Get-Set
	 */
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

	public Date getScadenzaCarta() {
		return scadenzaCarta;
	}

	public void setScadenzaCarta(Date scadenzaCarta) {
		this.scadenzaCarta = scadenzaCarta;
	}




}