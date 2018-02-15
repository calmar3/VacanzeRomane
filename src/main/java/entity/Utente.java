package entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
/**
 * Entita Utente
 * Essendo una classe astratta non viene mai istanziata ma gli viene associata una tabella Utente
 * entro cui verranno salvate le istanze delle classi che ereditano da Utente
 * Le sottoclassi saranno distinte dalla colonna discriminante 'tipologia'
 * che conterra il nome della sottoclasse cui appartiene l'istanza in considerazione
 */
@Entity
@Table(name = "Utente")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipologia")
public abstract class Utente {

	 
    /**
     * id per la tabella
     * chiave primaria auto-generata
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected Integer id;
    
       
    /**
     * L'annotazione column con attributo name
     * specifica il nome del campo entro la tabella
     */
    @Column(name = "nome")
	protected String nome;
   

	@Column(name = "cognome")
	protected String cognome;
    
	
    /**
     * il campo username deve essere unico nella tabella
     */
    @Column(name = "username",unique=true)
	protected String username;
    
    @Column(name = "password")
	protected String password;
    
    @Column(name = "mail")
	protected String mail;

	
/**
 * Get-Set
 */
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}