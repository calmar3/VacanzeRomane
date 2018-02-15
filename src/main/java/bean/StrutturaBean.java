package bean;
import java.util.ArrayList;
import java.util.List;
import controller.GestoreLocazioni;
import exception.ModificaStrutturaException;
import exception.StrutturaException;


/**
 * Bean relativo ad una struttura
 */
public class StrutturaBean {
   
    /**
     * Informazioni struttura
     */
    private String tipologia;
    private String nome;
    private String descrizione;
    private String indirizzo;
    private String nazione;
    private String citta;
    
    /**
     * Bean camere associate alla struttura
     */
    private List<CameraBean> camere;
	
	/**
	 * Costruttore di default
	 */
	public StrutturaBean(){
		this.setNome("");
		this.setDescrizione("");
		this.setIndirizzo("");
		this.setNazione("");
		this.setCitta("");
		this.setTipologia("");
		this.setCamere(new ArrayList<CameraBean>());
	}
	
	/**
	 * Aggiunta cameraBean alla lista di camere della struttura
	 */
	public void aggiungiCamera(CameraBean c)
	{
		if (this.getCamere() == null)
			this.setCamere(new ArrayList<CameraBean>());
		this.getCamere().add(c);
	}


	/**
	 * Verifica che i campi della struttura non siano stringhe vuote
	 * Richiama il controller delle Locazioni per associare una nuova struttura ad un 
	 * utente (gestore)
	 * @param ub
	 * @return
	 * @throws StrutturaException
	 */
	public boolean inserisciStruttura(UtenteBean ub) throws StrutturaException
	{
		if(this.nome.equals("") || this.nazione.equals("") || this.citta.equals("") || this.descrizione.equals("") || this.indirizzo.equals("") || this.tipologia.equals(""))
			return false;
		GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
		controllerLocazioni.aggiungiStruttura(this.tipologia,this.nome,this.descrizione,this.indirizzo,this.nazione,this.citta,ub);
		return true;
	}
	
	/**
	 * Veririca che i nuovi parametri della struttura non siano stringhe vuote
	 * e richiama il controller delle Locazioni per modificare una struttura associata ad
	 * un utente (gestore)
	 * @param i
	 * @param ub
	 * @param catalogo
	 * @return
	 * @throws ModificaStrutturaException
	 */
	public boolean modificaStruttura(int i ,UtenteBean ub , CatalogoBean catalogo) throws ModificaStrutturaException
	{	
		if((this.nome.equals("")) && (this.tipologia.equals("")) && (this.descrizione.equals("")) && (this.indirizzo.equals("")) && (this.nazione.equals("")))
				return false;
		else
		{
			GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
			controllerLocazioni.modificaStruttura(i, ub, catalogo, this);
			return true;
		}
	}
	
	
	/**
	 * GET - SET 
	 */
	public String getTipologia() 
	{
		return tipologia;
	}

	public void setTipologia(String tipologia) 
	{
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

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public List<CameraBean> getCamere() {
		if (camere==null)
			camere = new ArrayList <CameraBean>();
		return camere;
	}
	
	public void setCamere(List<CameraBean> camere) {
		this.camere = camere;
	}
	


	


}
