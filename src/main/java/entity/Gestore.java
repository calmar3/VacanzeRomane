package entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import exception.CameraException;
import exception.EliminaStrutturaException;
import exception.ModificaStrutturaException;
import exception.PrenotazioneException;
import exception.StrutturaException;
import hibernate.FactoryCamera;
import hibernate.FactoryPrenotazione;
import hibernate.FactoryStruttura;



/**
 * Entita Gestore
 * Verra inserita sul database 
 * nella tabella Utente sotto la discriminante tipologia="Gestore"
 */
@Entity
@Table(name = "Gestore")
public final class Gestore extends Utente {

   
   
	/**
     * L'annotazione oneToMany serve per realizzare una relazione bidirezionale di tipo
     * 1 a molti ( 1 affittuario molte strutture)
     * L'istanza sarà associata all'attributo gestore della classe Struttura
     */
	@OneToMany (mappedBy="gestore",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
   private List<Struttura> proprieta;
	
	@Transient
	private Struttura struttura;
		
	private String codiceContoCorrente;
   
   
    /**
     * costruttore di default
     */
    public Gestore() {
    }

    /**
     * Costruttore con parametri
     * @param nome
     * @param cognome
     * @param username
     * @param password
     * @param mail
     */
    public Gestore(String nome,String cognome, String username,String password, String mail,String codiceContoCorrente)
    {
    	this.setNome(nome);
    	this.setCognome(cognome);
    	this.setUsername(username);
    	this.setPassword(password);
    	this.setMail(mail);
    	this.setCodiceContoCorrente(codiceContoCorrente);
    }

    /**
     * @param citta 
     * @param nazione 
     * @param indirizzo 
     * @param descrizione 
     * @param nome 
     * @param tipologia 
     * @param struttura
     * Aggiunge la struttura alla lista delle proprieta
     * @throws StrutturaException 
     */
    public void inserisciLocazione(String tipologia, String nome, String descrizione, String indirizzo, String nazione, String citta) throws StrutturaException
    {
    	if (this.proprieta == null)
    		this.proprieta = new ArrayList<Struttura>();
    	this.getStruttura().associaDescrizioneStruttura(tipologia, nome, descrizione, indirizzo, nazione, citta);
    	this.getStruttura().setGestore(this);
    	FactoryStruttura fs = new FactoryStruttura();
    	fs.salva(this.struttura);
    	this.getProprieta().add(this.struttura);
    }
    
    
	public void modificaLocazione(int i, String tipologia, String nome, String descrizione, String indirizzo,
			String nazione, String citta) throws ModificaStrutturaException {
		
		this.struttura = this.proprieta.get(i).clone();
		this.struttura.associaDescrizioneStruttura(tipologia, nome, descrizione, indirizzo, nazione, citta);
		FactoryStruttura fs = new FactoryStruttura();
		fs.aggiornaStruttura(this.struttura);
		this.getProprieta().set(i, this.struttura);
	}
	
	public void modificaCamera(Integer indiceCamera, Integer indiceStruttura, String nomeCamera, String prezzoCamera,
			String descrizioneCamera, String tipologiaCamera) throws CameraException {
		this.struttura = this.getProprieta().get(indiceStruttura).clone();
		Camera c = this.getStruttura().getCamere().get(indiceCamera);
		c.associaDescrizione(nomeCamera, prezzoCamera, descrizioneCamera, tipologiaCamera);
		FactoryCamera fc = new FactoryCamera();
		fc.aggiornaCamera(c);
		this.struttura.getCamere().set(indiceCamera, c);
		this.getProprieta().set(indiceStruttura, this.struttura);
	}
	
	public void aggiungiCamera(Camera c, String tipologiaCamera, String nomeCamera, String descrizioneCamera,
			String prezzoCamera) {
		c.associaDescrizione(nomeCamera, prezzoCamera, descrizioneCamera, tipologiaCamera);
		this.getStruttura().inserisciCamera(c);
	}
	
	public void eliminaCamera(Integer indiceStruttura, Integer indiceCamera) throws PrenotazioneException, CameraException {
		FactoryPrenotazione fp = new FactoryPrenotazione();
		this.struttura = getProprieta().get(indiceStruttura).clone();
		Camera c = this.struttura.getCamere().get(indiceCamera);
		c.setLinee(fp.prendiLineeCamera(c));
		for (LineaPrenotazione lp: c.getLinee())
		{
			Prenotazione p = lp.getPrenotazione();
			p.eliminaLineePrenotazione(lp);
			lp.rimuoviServiziScelti();
			fp.eliminaLineaPrenotazione(lp);
			if(p.getLinee().size() == 0)
				fp.eliminaPrenotazione(p);
			else
				fp.aggiornaPrenotazione(p);
		}
		c.getLinee().clear();
		FactoryCamera fc = new FactoryCamera();
		fc.eliminaCamera(c);	
		this.getProprieta().get(indiceStruttura).getCamere().remove(indiceCamera);
	}

	public void eliminaLocazione() throws EliminaStrutturaException {
		this.getStruttura().getCamere().clear();
		FactoryStruttura fs = new FactoryStruttura();
    	fs.eliminaStruttura(this.getStruttura());
    	if (getProprieta().contains(this.getStruttura()))
    		getProprieta().remove(this.getStruttura());
		
	}


	/**
	 * Get-Set
	 */
	public List<Struttura> getProprieta() {
            return proprieta;
	}

	public void setProprieta(List<Struttura> proprieta) {
		this.proprieta =  proprieta;
	}


	public Struttura getStruttura() {
		if (this.struttura == null)
			struttura = new Struttura();
		return struttura;
	}

	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}
	
	public String getCodiceContoCorrente() 
	{
		return codiceContoCorrente;
	}

	public void setCodiceContoCorrente(String codiceContoCorrente) 
	{
		this.codiceContoCorrente = codiceContoCorrente;
	}
}