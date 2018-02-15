package controller;

import exception.CameraException;
import exception.EliminaStrutturaException;
import exception.ModificaStrutturaException;
import exception.PrenotazioneException;
import exception.StrutturaException;
import hibernate.FactoryPrenotazione;

import java.util.ArrayList;
import java.util.List;

import bean.CameraBean;
import bean.CatalogoBean;
import bean.LineaPrenotazioneBean;
import bean.PrenotazioneBean;
import bean.ServizioBean;
import bean.StrutturaBean;
import bean.UtenteBean;
import entity.Camera;
import entity.CatalogoStrutture;
import entity.FactoryMethodServizio;
import entity.Gestore;
import entity.LineaPrenotazione;
import entity.Prenotazione;
import entity.Servizio;
import entity.Struttura;


/**
 * Controller Locazioni 
 * per il gestore
 *
 */
public class GestoreLocazioni {    
    /**
     *	Costruttore vuoto privato
     * 	per pattern Singleton
     */
    public GestoreLocazioni() 
    {
    }

     
    /**
     * @param tipologia
     * @param nome
     * @param descrizione
     * @param indirizzo
     * @param nazione
     * @param citta
     * prende l'istanza di struttura già creata e con le camere collegate
     * imposta gli attributi della struttura con i parametri ricevuti
     * collega il gestore con la struttura in entrambi i versi
     * @throws throws EliminaStrutturaException 
     */
    public void aggiungiStruttura(String tipologia, String nome, String descrizione, String indirizzo, String nazione,
			String citta,UtenteBean ub) throws StrutturaException
    {	
    	GestoreUtente controllerUtente = GestoreUtente.avviaControllerUtente();
    	Gestore g = (Gestore) controllerUtente.getUtenti().get(ub.getUsername());
    	g.inserisciLocazione(tipologia, nome, descrizione, indirizzo, nazione, citta);
    	controllerUtente.getUtenti().put(g.getUsername(), g);
    	CatalogoStrutture catalogo = CatalogoStrutture.prendiCatalogo();
    	catalogo.getStrutture().add(g.getStruttura());
    	g.setStruttura(null);
    }
    
    /**
     * @param tipologia
     * @param nome
     * @param descrizioneCamera
     * @param prezzoCamera
     * Qui andranno aggiunti i servizi in base ai quali si aggiornera il prezzo 
     * della camera
     */
    public void aggiungiCamera(CameraBean cb,UtenteBean ub)
    {
    	Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
    	Camera c = new Camera();
    	for (ServizioBean servizioBean: cb.getServizi())
    		c.aggiungiServizio(FactoryMethodServizio.creaServizio(c,servizioBean));
    	g.aggiungiCamera(c,cb.getTipologiaCamera()
    			,cb.getNomeCamera(),cb.getDescrizioneCamera(),cb.getPrezzoCamera());
    }
    
    public void caricaStruttureGestore(CatalogoBean catalogo,UtenteBean ub)
    {
    	catalogo.getStrutture().clear();
    	Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
    	if (g.getProprieta() == null)
    		return;
    	for (Struttura s: g.getProprieta())
    	{
    		StrutturaBean sb = new StrutturaBean();
    		GestoreEntitaBean.conversioneInStrutturaBean(s, sb);
    		for (Camera c : s.getCamere()) {
				CameraBean cb = new CameraBean();
				GestoreEntitaBean.conversioneInCameraBean(c,cb);
				if (c.getServizi() != null)
				{
					for (Servizio servizio: c.getServizi())
					{
						ServizioBean servizioBean = new ServizioBean();
						GestoreEntitaBean.convertiInServizioBean(servizio,servizioBean);
						cb.getServizi().add(servizioBean);
					}
				}
				sb.getCamere().add(cb);
			}
    		catalogo.getStrutture().add(sb);    	
    	}
    }
    
	public void rimuoviStruttura(int i,UtenteBean ub,CatalogoBean catalogo) throws EliminaStrutturaException, PrenotazioneException, CameraException
    {
		Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		g.setStruttura(g.getProprieta().get(i).clone());
		for (int indice=0;indice<g.getStruttura().getCamere().size();)
			this.rimuoviCamera(i, indice, catalogo, ub);
		g.eliminaLocazione();
    	CatalogoStrutture catalogoDominio = CatalogoStrutture.prendiCatalogo();
    	catalogoDominio.getStrutture().remove(g.getStruttura());
    	g.setStruttura(null);
    	GestoreUtente.avviaControllerUtente().getUtenti().put(ub.getUsername(), g);
    }
	

	public void modificaStruttura(int i ,UtenteBean ub , CatalogoBean catalogo,StrutturaBean modificata) throws ModificaStrutturaException
	{
		Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		g.modificaLocazione(i,modificata.getTipologia(), modificata.getNome(), modificata.getDescrizione(),
				modificata.getIndirizzo(), modificata.getNazione(), modificata.getCitta());
    	CatalogoStrutture catalogoDominio = CatalogoStrutture.prendiCatalogo();
		catalogoDominio.sostituisci(g.getStruttura());	
		g.setStruttura(null);
	}

	public boolean modificaCamera(Integer indice,Integer indiceStruttura,
			StrutturaBean struttura,UtenteBean ub,CatalogoBean catalogo,CameraBean camera) throws CameraException
	{
		Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		g.modificaCamera(indice, indiceStruttura, camera.getNomeCamera(),camera.getPrezzoCamera(),camera.getDescrizioneCamera(),
				camera.getTipologiaCamera());
		catalogo.getStrutture().get(indiceStruttura).getCamere().set(indice, camera);
		CatalogoStrutture.modificaCamera(g.getStruttura(),g.getStruttura().getCamere().get(indice));
		g.setStruttura(null);
		return true;
	}
	
	public boolean rimuoviCamera(Integer indiceStruttura,Integer indiceCamera,CatalogoBean catalogo,UtenteBean ub) throws PrenotazioneException, CameraException
	{
		catalogo.getStrutture().get((int)indiceStruttura).getCamere().remove((int)indiceCamera);
		Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		g.eliminaCamera(indiceStruttura,indiceCamera);
		CatalogoStrutture catalogoDominio =	CatalogoStrutture.prendiCatalogo();
		catalogoDominio.rimuoviCamera(g.getStruttura(),g.getStruttura().getCamere().get(indiceCamera));
		if ( catalogo.getStrutture().get(indiceStruttura).getCamere().size() == 0 )
			catalogo.getStrutture().remove(indiceStruttura);
		return true;
	}

	public void impostaStruttura(Integer i,UtenteBean ub)
	{
		Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		g.setStruttura(g.getProprieta().get(i));
	}
	
	public void caricaPrenotazioniStrutture(UtenteBean ub)
	{
		Gestore g = (Gestore) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		if (g.getProprieta().size()==0 )
			return;
		FactoryPrenotazione fp = new FactoryPrenotazione();
		List<Prenotazione> prenotazioniStruttura = new ArrayList<Prenotazione>();
		for(Struttura s: g.getProprieta())
			prenotazioniStruttura.addAll(fp.selezionePrenotazioniStruttura(s));
		ub.getPrenotazioni().clear();
		for(Prenotazione prenotazione: prenotazioniStruttura)
		{
			PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
			GestoreEntitaBean.conversioneInPrenotazioneBean(prenotazione, prenotazioneBean);
			prenotazioneBean.setNomeStruttura(prenotazione.getStrutturaPrenotata().getNome());
			for (LineaPrenotazione linea : prenotazione.getLinee()) 
			{
				LineaPrenotazioneBean lineaBean = new LineaPrenotazioneBean();
				GestoreEntitaBean.conversioneInLineaPrenotazioneBean(linea, lineaBean);
				for (Servizio s: linea.getServiziScelti())
				{
					ServizioBean servizioBean = new ServizioBean();
					GestoreEntitaBean.convertiInServizioBean(s, servizioBean);
					lineaBean.getServiziScelti().add(servizioBean);
				}
				prenotazioneBean.getLinee().add(lineaBean);
			}
			ub.getPrenotazioni().add(prenotazioneBean);
		}
	}
}
