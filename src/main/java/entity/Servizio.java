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
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Servizio")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipologia")
public abstract class Servizio 
{
	/**
	 * Chiave primaria auto-generata
	 */
	@Id
	@Column (name="id_servizio")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column (name="nome")
	private String nome;
	
	@Column (name="descrizione")
	private String descrizione;
	
    @ManyToOne
    @JoinColumn(name = "id_camera")
	protected Camera camera;
    	
	
	/**
	 * Metodo che rivela se l'istanza contenuta in un oggetto di tipo Servizio
	 * sia di tipo Premium oppure Base
	 */
	public boolean isPremium()
	{
		if (this instanceof ServizioPremium)
			return true;
		else
			return false;
	}
    
	/**
	 * GET - SET
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	

	
}