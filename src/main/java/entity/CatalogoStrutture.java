package entity;

import java.util.*;

import hibernate.FactoryStruttura;

/**
 * In questa classe vengono caricate tutte le strutture in seguito ad un caricamento
 * come ad esempio una ricerca oppure le strutture relative ad un gestore
 */
public class CatalogoStrutture {
	
    /**
     * Costruttore vuoto e privato 
     * per pattern Singleton
     */
    private CatalogoStrutture() {
    }

    private static  CatalogoStrutture catalogo;
    private List<Struttura> strutture;


    
    /**
     * restituisce l'unica istanza di catalogo
     */
    public synchronized static CatalogoStrutture prendiCatalogo()
    {
        if (catalogo==null)
        	catalogo = new CatalogoStrutture();
        return catalogo;
    }

    /**
     * Carica tutte le strutture dal database
     */
    public synchronized void riempiCatalogo()
    {
    	FactoryStruttura fs = new FactoryStruttura();
    	List<Struttura> s = fs.selezionaTutto();
    	if (s!=null)
    		this.strutture = s;
    }
	
	/**
	 * Restituisce una struttura cercandola nella lista per nome
	 * @param nomeStruttura
	 * @return
	 */
	public synchronized Struttura cercaDaNome(String nomeStruttura)
	{
		for (Struttura s : this.strutture)
			if (s.getNome().equals(nomeStruttura))
					return s.clone();
		return null;
	}
	
	/**
	 * Sostituisce una struttura modificata nella lista
	 * @param daModificare
	 */
	public synchronized  void sostituisci(Struttura daModificare) 
	{
		for (int i=0;i<this.strutture.size();i++)
			if (this.strutture.get(i).getId()==daModificare.getId())
				this.strutture.set(i, daModificare);		
	}

	/**
	 * Rimuove la camera di una struttura nella lista
	 * @param s
	 * @param c
	 */
	public synchronized void rimuoviCamera(Struttura s, Camera c) {
		s.getCamere().remove(c);
		if (s.getCamere().size()==0)
			strutture.remove(s);
		
	}
	
	/**
	 * Aggiorna la camera di una struttura nella lista
	 * @param s
	 * @param daModificare
	 */
	public synchronized static void modificaCamera(Struttura s, Camera daModificare) 
	{
		for (int i=0;i<s.getCamere().size();i++)
			if (s.getCamere().get(i).getId() == daModificare.getId())
				s.getCamere().set(i, daModificare);	
	}

	/**
	 * Get-Set
	 */
	public synchronized List<Struttura> getStrutture() {
		if (this.strutture == null)
			riempiCatalogo();
		return this.strutture;
	}

	public synchronized void setStrutture(List<Struttura> s) {
		if (this.strutture == null)
			this.strutture = new ArrayList<Struttura>();
		this.strutture.clear();
		this.strutture.addAll(s);
	}


}