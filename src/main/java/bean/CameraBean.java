package bean;


import java.util.*;
import controller.GestoreLocazioni;


/**
 * Bean per visualizzare i dati relativi
 * ad una camera 
 */
public class CameraBean
{

	   
    /**
     * Attributi camera visibili nel Bean
     */
    private String tipologiaCamera;
    private String nomeCamera;
    private String descrizioneCamera;
    private String prezzoCamera;
    private List<ServizioBean> servizi;
	
	/**
	 * Costruttore di default
	 */
	public CameraBean()
	{
		this.setNomeCamera("");
		this.setTipologiaCamera("");
		this.setDescrizioneCamera("");
		this.setPrezzoCamera("");
		this.setServizi(new ArrayList<ServizioBean>());
	}
	
	
	/**
	 * Verifica che i campi presi dal form non siano vuoti
	 * Nel caso in cui non lo siano chiama il metodo del controller per le locazioni
	 * per aggiungere un camera in una struttura di un utente(gestore)
	 * grazie ai parametri
	 * @param sb
	 * @param ub
	 * @return
	 */
	public boolean inserisciCamera(StrutturaBean sb,UtenteBean ub)
	{
		if(this.nomeCamera.equals("") || this.tipologiaCamera.equals("") || this.descrizioneCamera.equals("")
				|| this.prezzoCamera.equals(""))
			return false;
		GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
		controllerLocazioni.aggiungiCamera(this,ub);
		sb.aggiungiCamera(this);
		return true;
	}
	
	
	/**
	 * Metodo per aggiungere il parametro
	 * @param servizio
	 * ai servizi associati alla camera
	 */
	public void aggiungiServizio(ServizioBean servizio)
	{
		this.servizi.add(servizio);
	}


	
	/**
	 * GET-SET
	 */
	public String getTipologiaCamera() 
	{
		return tipologiaCamera;
	}

	public void setTipologiaCamera(String tipologiaCamera)
	{
		this.tipologiaCamera = tipologiaCamera;
	}

	public String getNomeCamera() 
	{
		return nomeCamera;
	}

	public void setNomeCamera(String nomeCamera) 
	{
		this.nomeCamera = nomeCamera;
	}

	public String getDescrizioneCamera() 
	{
		return descrizioneCamera;
	}

	public void setDescrizioneCamera(String descrizioneCamera) 
	{
		this.descrizioneCamera = descrizioneCamera;
	}

	public String getPrezzoCamera()
	{
		return prezzoCamera;
	}
	
	public void setPrezzoCamera(String prezzoCamera)
	{
		this.prezzoCamera = prezzoCamera;
	}

	public List<ServizioBean> getServizi() 
	{
		if(servizi == null)
			servizi = new ArrayList<ServizioBean>();
		return servizi;
	}


	public void setServizi(List<ServizioBean> servizi)
	{
		this.servizi = servizi;
	}



}