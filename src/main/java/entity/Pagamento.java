package entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Non ci saranno istanze della classe Pagamento
 * Per cui la tabella contiene le istanze delle classi che ereditano da Pagamento
 * Saranno discriminate dalla colonna metodo
 */
@Entity
@Table(name = "Pagamento")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="metodo")
public abstract class Pagamento {

	
	/**
	 * Chiave primaria auto-generata
	 */
	@Id
	@Column (name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
	
	@Column (name="importo")
	protected Double importo;
	
	/**
	 * Ad un pagamento corrisponde una prenotazione
	 */
	@OneToOne
	@JoinColumn(name="id_prenotazione")
	protected Prenotazione prenotazione;
	
	
    /**
     * 
     */
    /**
     * metodo astratto per consentire di effettuare il pagamento
     */
    protected abstract boolean autorizzaPagamento();
    
    /**
     * Metodo per il rimborso
     * @param importoDaRimborsare
     * @param g
     * @return
     */
    protected abstract boolean autorizzaRimborso(double importoDaRimborsare,Gestore g);



	/**
	 * Get-Set
	 */
	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Prenotazione getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}
}