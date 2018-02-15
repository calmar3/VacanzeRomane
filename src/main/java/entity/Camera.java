package entity;



import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Marco
 * Annotazione per Hibernate
 * Camera entita del database
 * una camera sara identificata univocamente a livello di DOMINIO
 * dalla struttura e dal nome 
 */
@Entity
@Table(name = "Camera" , uniqueConstraints = @UniqueConstraint(columnNames = {"nomeCamera", "id_struttura"}))
public class Camera {

    


	/**
     * il nome della camera
     */
    @Column(name="nomeCamera")
    private String nomeCamera;
    private String descrizioneCamera;
    
    @OneToMany(mappedBy="camera" , cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Servizio> servizi;
    
    /**
     * ManyToone : piu camere relazionate con una struttura
     * tramite il campo id_struttura
     */
    @ManyToOne
    @JoinColumn(name = "id_struttura")
    private Struttura struttura;
    
    
    private String tipologia;
    private Double prezzo;
    
    
    /**
     * Chiave primaria dell'entita 
     * Id auto-generato
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @OneToMany(mappedBy="cameraLP" )
    private List<LineaPrenotazione> linee;
    
    @OneToMany(mappedBy="camera" ,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<DisponibilitaCamera> disponibilita;

    
    /**
     * Costruttore di default
     */
    public Camera() 
    {
    }

    /**
     * Costruttore con parametri
     * @param s
     * @param tipologia
     * @param nome
     * @param descrizioneCamera
     * @param prezzo
     */
    public Camera(Struttura s, String tipologia, String nome,String descrizioneCamera, String prezzo) 
    {
    	this.setStruttura(s);
    	this.setNomeCamera(nome);
    	this.setTipologia(tipologia);
    	this.setDescrizioneCamera(descrizioneCamera);
    	this.setPrezzoCamera(Double.parseDouble(prezzo));
    	this.disponibilita = new TreeSet<DisponibilitaCamera>();
    	this.servizi = new ArrayList<Servizio>();
	}

    
    /**
     * Aggiunge il servizio ricevuto per argomento alla lista di servizi disponibili per la camera
     * @param servizio
     */
    public void aggiungiServizio(Servizio servizio) 
    {
        if (this.getServizi() == null)
        	this.setServizi(new ArrayList<Servizio>());
        this.getServizi().add(servizio);
    }

    /**
     * Associa i parametri ricevuti per argomento
     * @param nome
     * @param prezzo
     * @param descrizione
     * @param tipologia
     * agli attributi della camera
     */
    public void associaDescrizione(String nome, String prezzo, String descrizione, String tipologia) 
    {
       this.descrizioneCamera = descrizione;
       this.nomeCamera = nome;
       this.prezzo = Double.parseDouble(prezzo);
       this.tipologia = tipologia;
    }
    
	/**
	 * Date per argomento due date di checkIn e checkOut
	 * verifica che la camera sia disponibile in quell'intervallo
	 * scorrendo la lista di disponibilita associata alla camera
	 * @param checkIn
	 * @param checkOut
	 * @return
	 */
	public boolean isDisponibile(Date checkIn, Date checkOut) {
		boolean disponibile = true;
		TreeSet<DisponibilitaCamera> dateNonDisponibili = new TreeSet<DisponibilitaCamera>();
		dateNonDisponibili.addAll(this.getDisponibilita());
		DisponibilitaCamera daControllare = new DisponibilitaCamera(checkIn, checkOut);
		Iterator<DisponibilitaCamera> i = dateNonDisponibili.iterator();
		if (this.getDisponibilita().size()==0)
			return true;
		DisponibilitaCamera dc = dateNonDisponibili.first();
		if (!dc.controlloPrimaSovrapposizione(daControllare))
			return true;
		dc = dateNonDisponibili.last();
		if (!dc.controlloUltimaSovrapposizione(daControllare))
			return true;
		while(i.hasNext() && disponibile) 
		{
			dc = i.next();
			if(dc.controlloSovrapposizioni(daControllare))
				disponibile = false;
	    }
		return disponibile;
	}
	
	/**
	 * Restituisce il numero massimo di persone ospitabili
	 */
	public int capacita() {
		if (this.tipologia.equals("Camera Singola"))
			return 1;
		else if (this.tipologia.equals("Camera Matrimoniale"))
			return 2;
		else 
			return 3;
	}
    
/**
 * Get-Set
 */
    
	public String getNomeCamera(){
		return nomeCamera;
	}

	public void setNomeCamera(String nomeCamera){
		this.nomeCamera = nomeCamera;
	}

	public String getTipologia(){
		return tipologia;
	}

	public void setTipologia(String tipologia){
		this.tipologia = tipologia;
	}

	public String getDescrizioneCamera(){
		return descrizioneCamera;
	}

	public void setDescrizioneCamera(String descrizioneCamera){
		this.descrizioneCamera = descrizioneCamera;
	}

	public Struttura getStruttura(){
		return struttura;
	}

	public void setStruttura(Struttura struttura){
		this.struttura = struttura;
	}
	
	public double getPrezzoCamera(){
		return prezzo;
	}
	
	public void setPrezzoCamera(double prezzo){
		this.prezzo = prezzo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Servizio> getServizi() {
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}

	public Set<DisponibilitaCamera> getDisponibilita() {
		if (this.disponibilita == null)
			this.disponibilita = new TreeSet<DisponibilitaCamera>();
		return disponibilita;
	}

	public void setDisponibilita(Set<DisponibilitaCamera> disponibilita) {
		this.disponibilita = disponibilita;
	}

	public List<LineaPrenotazione> getLinee() {
		return linee;
	}

	public void setLinee(List<LineaPrenotazione> linee) {
		this.linee = linee;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Camera other = (Camera) obj;
		if (descrizioneCamera == null) {
			if (other.descrizioneCamera != null)
				return false;
		} else if (!descrizioneCamera.equals(other.descrizioneCamera))
			return false;
		if (disponibilita == null) {
			if (other.disponibilita != null)
				return false;
		} else if (!disponibilita.equals(other.disponibilita))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (getLinee() == null) {
			if (other.getLinee() != null)
				return false;
		} else if (!getLinee().equals(other.getLinee()))
			return false;
		if (nomeCamera == null) {
			if (other.nomeCamera != null)
				return false;
		} else if (!nomeCamera.equals(other.nomeCamera))
			return false;
		if (prezzo == null) {
			if (other.prezzo != null)
				return false;
		} else if (!prezzo.equals(other.prezzo))
			return false;
		if (servizi == null) {
			if (other.servizi != null)
				return false;
		} else if (!servizi.equals(other.servizi))
			return false;
		if (struttura == null) {
			if (other.struttura != null)
				return false;
		} else if (!struttura.equals(other.struttura))
			return false;
		if (tipologia == null) {
			if (other.tipologia != null)
				return false;
		} else if (!tipologia.equals(other.tipologia))
			return false;
		return true;
	}
	
}