package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import entity.SistemaDiPagamento;

/**
 * Entita PagamentoBonifico in Pagamento
 * Il nome della classe costituisce la colonna discriminante
 */
@Entity  
@Table(name="PagamentoBonifico")  
public class PagamentoBonifico extends Pagamento 
{
	/**
	 * Colonne riservate alle istanze di PagamentoBonifico nella tabella
	 * Pagamento
	 */
	@Column(name="iban")
	private String Iban;
	
	@Column(name="swift")
	private String swift;
    
	
    /**
     * costruttore con parametri
     * @param pb
     * @param p
     */
    public PagamentoBonifico(String iban,String swift,Prenotazione p) {
    	this.Iban = iban;
    	this.swift = swift;
    	super.setPrenotazione(p);
    	super.setImporto(p.getImporto());
    	this.autorizzaPagamento();
    }
    /**
     * Costruttore di default
     */
    public PagamentoBonifico()
    {
    	
    }
    
	/* (non-Javadoc)
	 * @see dominio.Pagamento#autorizzaPagamento()
	 * implementa il metodo ereditato dalla classe che estende
	 * effettuando un pagamento con le credenziali relative ad un bonifico
	 */
	@Override
	protected boolean autorizzaPagamento() 
	{
		SistemaDiPagamento sistemaPagamento = new SistemaDiPagamento();
		if(!(sistemaPagamento.convalidaBonifico(super.getImporto(),this.getIban(),this.getSwift(),super.getPrenotazione().getStrutturaPrenotata().getGestore())))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see dominio.Pagamento#autorizzaRimborso(double, dominio.Gestore)
	 * implementa il metodo ereditato dalla classe che estende
	 * effettuando un rimborso con le credenziali del bonifico
	 */
	@Override
	protected boolean autorizzaRimborso(double importoDaRimborsare,Gestore g)
	{
		if (importoDaRimborsare < super.importo)
			super.importo -= importoDaRimborsare; 
		SistemaDiPagamento sistemaPagamento = new SistemaDiPagamento();
		if(sistemaPagamento.accreditaSuConto(this.Iban,importoDaRimborsare,g.getCodiceContoCorrente()))
			return true;
		return false;
		
	}
    
	/**
	 * Get-Set
	 */
	public String getIban() 
	{
		return Iban;
	}

	public void setIban(String iban) 
	{
		Iban = iban;
	}

	public String getSwift() 
	{
		return swift;
	}

	public void setSwift(String swift) 
	{
		this.swift = swift;
	}



}