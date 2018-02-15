package entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import controller.GestorePrenotazioni;
import exception.CameraException;
import exception.CameraNonDisponibileException;
import exception.LineaPrenotazioneException;
import exception.PrenotazioneException;
import hibernate.FactoryPrenotazione;


/**
 * Annotazione per Hibernate
 * Affittuario entita del database
 */
@Entity
@Table(name = "Affittuario")
public class Affittuario extends Utente {    
    
    /**
     * L'annotazione oneToMany serve per realizzare una relazione bidirezionale di tipo
     * 1 a molti ( 1 affittuario molte prenotazione)
     * L'istanza sarà associata all'attributo affittuario della classe Prenotazione
     */
    @OneToMany (mappedBy="affittuario",cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Prenotazione> prenotazioni;
    
    @Transient
    private Prenotazione prenotazione;
    
    public Affittuario() {
    	prenotazioni = new ArrayList<Prenotazione>();
    }

    
	/**
	 * @param nome
	 * @param cognome
	 * @param username
	 * @param password
	 * @param mail
	 */
	public Affittuario(String nome, String cognome, String username, String password, String mail) 
	{
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.mail = mail;
	}

    /**
     * aggiungi prenotazione alle prenotazioni 
     * dell'affittuario
     * @throws CameraException 
     * @throws PrenotazioneException 
     */
    public void effettuaPrenotazione() throws CameraException, PrenotazioneException 
    {
    	getPrenotazione().generaPin();
    	this.prenotazione.setAffittuario(this);
    	if (!GestorePrenotazioni.aggiungiDisponibilita(getPrenotazione()))
			throw new CameraNonDisponibileException("La camera non è disponibile nel periodo scelto");
    	FactoryPrenotazione fp = new FactoryPrenotazione();
    	fp.salva(getPrenotazione());
    	this.prenotazioni.add(prenotazione);
    } 
    
    public void eliminaLineaPrenotazione(Integer indicePrenotazione,Integer indiceLinea) throws CameraException, PrenotazioneException
    {
		this.prenotazione = getPrenotazioni().get(indicePrenotazione);
		LineaPrenotazione lineaDaRimuovere= this.prenotazione.getLinee().get(indiceLinea);
		GestorePrenotazioni.rimuoviDisponibilita(lineaDaRimuovere);
		lineaDaRimuovere.rimuoviServiziScelti();
		FactoryPrenotazione fp = new FactoryPrenotazione();
		fp.eliminaLineaPrenotazione(lineaDaRimuovere);
		this.prenotazione.eliminaLineePrenotazione(lineaDaRimuovere);
		if(this.prenotazione.getLinee().size() == 0)
		{
			fp.eliminaPrenotazione(this.prenotazione);
			getPrenotazioni().remove(indicePrenotazione);
		}
		else
		{
			fp.aggiornaPrenotazione(this.prenotazione);
			getPrenotazioni().get(indicePrenotazione).getLinee().remove(indiceLinea);
		}
    }
    
    public double prenotaCamera(Struttura struttura, Camera c, String checkIn, String checkOut, List<Servizio> serviziScelti) throws LineaPrenotazioneException
    {
    	double prezzoLinea = getPrenotazione().aggiungiLineaPrenotazione(c, checkIn, checkOut, serviziScelti);
    	getPrenotazione().setStrutturaPrenotata(struttura);
    	return prezzoLinea;
    }
    
    
    

/**
 * Get-Set
 */
	public List<Prenotazione> getPrenotazioni() {
            return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}


	public Prenotazione getPrenotazione() {
		if (this.prenotazione==null)
			prenotazione = new Prenotazione();
		return prenotazione;
	}


	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}


}