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

/**
 * Entita struttura
 * Nel database va in tabella Struttura
 */
@Entity
@Table(name = "Struttura")
public class Struttura implements  Cloneable{

    
    /**
     * Chiave primaria delle istanze nella tabella
     * Generato automaticamente dal database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @Column (name="tipologia")
    private String tipologia;

    @Column(name="nome",unique = true)
    private String nome;

    @Column (name="descrizione")
    private String descrizione;
    
    @Column (name="indirizzo")
    private String indirizzo;

    @Column (name="nazione")
    private String nazione;

    @Column (name="citta")
    private String citta;
    
    @OneToMany(mappedBy="strutturaPrenotata" , cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;

    
    /**
     * Una struttura puo possedere piu camere
     * pertanto la chiave primaria della struttura 
     * sara mappata dall'attributo struttura nella classe Camera
     */
    @OneToMany(mappedBy="struttura" , cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Camera> camere;
    
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "id_gestore")
	private Gestore gestore;
    
    /**
     * Costruttore di default
     */
    public Struttura() {
    }


    /**
     * Costruttore con parametri
     * @param tipologia
     * @param nome
     * @param descrizione
     * @param indirizzo
     * @param nazione
     * @param citta
     */
    public Struttura(String tipologia, String nome, String descrizione, String indirizzo, String nazione,
			String citta) {
    	setCitta(citta);
    	setDescrizione(descrizione);
    	setIndirizzo(indirizzo);
    	setNazione(nazione);
    	setNome(nome);
    	setTipologia(tipologia);
	}

    /**
     * @param camera
     * qui la camera prenderà una lista di servizi
     * grazie ai quali aggiornera il proprio prezzo
     */
    public void inserisciCamera(Camera camera) 
    {
		if (this.camere == null)
			this.camere = new ArrayList<Camera>();
		this.camere.add(camera);
		camera.setStruttura(this);
    }

    /**
     * restituisce un'istanza di una camera a partire dal nome
     * @param nomeCamera
     * @return
     */
    public Camera selezionaCamera(String nomeCamera) {
    	for (Camera c: this.camere)
    		if(c.getNomeCamera().equals(nomeCamera))
    			return c;
    	return null;
    }

	/**
	 * Associa ai propri attributi i valori dei parametri 
	 * @param tipologia
	 * @param nome
	 * @param descrizione
	 * @param indirizzo
	 * @param nazione
	 * @param citta
	 */
	public void associaDescrizioneStruttura(String tipologia, String nome, String descrizione, String indirizzo, String nazione,
			String citta) {
    	this.setTipologia(tipologia);
    	this.setNome(nome);
    	this.setDescrizione(descrizione);
    	this.setIndirizzo(indirizzo);
    	this.setNazione(nazione);
    	this.setCitta(citta);
		
	}

	
/**
 * Get-Set
 */
	public String getTipologia() {
		return tipologia;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Struttura other = (Struttura) obj;
		if (camere == null) {
			if (other.camere != null)
				return false;
		} else if (!camere.equals(other.camere))
			return false;
		if (citta == null) {
			if (other.citta != null)
				return false;
		} else if (!citta.equals(other.citta))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (gestore == null) {
			if (other.gestore != null)
				return false;
		} else if (!gestore.equals(other.gestore))
			return false;
		if (id != other.id)
			return false;
		if (indirizzo == null) {
			if (other.indirizzo != null)
				return false;
		} else if (!indirizzo.equals(other.indirizzo))
			return false;
		if (nazione == null) {
			if (other.nazione != null)
				return false;
		} else if (!nazione.equals(other.nazione))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipologia == null) {
			if (other.tipologia != null)
				return false;
		} else if (!tipologia.equals(other.tipologia))
			return false;
		return true;
	}


	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

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

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public List<Camera> getCamere() {
            return camere;
	}

	public void setCamere(List<Camera> camere) {
		this.camere = camere;
	}


	public Gestore getGestore() {
		return gestore;
	}


	public void setGestore(Gestore gestore) {
		this.gestore = gestore;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
	
	@Override
    public Struttura clone() {
        try {
			return (Struttura) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        return null;
	}

	public List<Prenotazione> getPrenotazioni() {
		if (this.prenotazioni==null)
			this.prenotazioni = new ArrayList<Prenotazione>();
			HashSet<Prenotazione> hs = new HashSet<Prenotazione>();
            hs.addAll(this.prenotazioni);
            prenotazioni.clear();
            prenotazioni.addAll(hs);
            return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

}