package controller;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UnsupportedLookAndFeelException;

import bean.CatalogoBean;
import bean.UtenteBean;
import entity.*;
import exception.CameraException;
import exception.EliminaStrutturaException;
import exception.PrenotazioneException;
import exception.UserException;
import laptop.entity.Amministratore;
import laptop.exception.DeserializzazioneException;
import laptop.exception.PasswordException;
import laptop.exception.SerializzazioneException;
import laptop.exception.UtenteException;
import hibernate.FactoryUtente;
import laptop.util.PersistenzaFile;


/**
 * Controller per la gestione degli utenti
 * Si occupa della creazione rimozione e modifica di un account Utente
 *
 * */
public class GestoreUtente
{
	
	private static GestoreUtente controllerUtente;
    private HashMap<String,Utente> utenti;    

  /**
   * costruttore privato per il pattern Singleton
   */
    private GestoreUtente() 
    {
    }

    /**
     * Restituisce l'istanza del controller
     */
    public synchronized static GestoreUtente avviaControllerUtente()
    {
	   if (controllerUtente == null)
		   controllerUtente = new GestoreUtente();
	   return controllerUtente;   
    }
   

 
    /**
     * Riceve per parametro il bean con i dati della registrazione
     * esegue un controllo sui dati 
     * crea l'istanza dell'utente dopo aver convalidato i dati
     * salva l'istanza sul database
     * restituisce un valore booleano a conferma dell'avvenuto login
     * @throws UserException 
     */
    public synchronized boolean inserisciCredenziali(UtenteBean ub) throws UserException
    {
    	FactoryUtente fu = new FactoryUtente();
    	Utente u = fu.autentica(ub.getUsername(),ub.getPassword());
    	if (u == null)
    		return false;
    	else
    		this.getUtenti().put(u.getUsername(), u);
    	GestoreEntitaBean.conversioneInUtenteBean(this.getUtenti().get(u.getUsername()),ub);
        return true;
    }
    
    public synchronized void logout(UtenteBean ub)
    {
    	getUtenti().remove(ub.getUsername());
    	ub.setLog(false);
    }
    
	
    public synchronized boolean convalidaRegistrazione(String nome, String cognome,String username, String password, String mail, String tipologia, String codiceContoCorrente) throws UserException
    {
    	
    	if (tipologia.equals("Affittuario"))
    	{
    		if(!controllaCredenziali(nome,cognome,username,password,mail,null))
    			return false;
	    	this.getUtenti().put(username, (new Affittuario(nome, cognome, username, password, mail)));
    	}
    	else 
    	{
    		if(!controllaCredenziali(nome,cognome,username,password,mail,codiceContoCorrente))
    			return false;
    		this.getUtenti().put(username, (new Gestore(nome, cognome, username, password, mail, codiceContoCorrente)));
    	}
    	FactoryUtente fu = new FactoryUtente();
		fu.salva(this.getUtenti().get(username));
		return true;
    }
    
	
	
	public synchronized boolean controllaCredenziali(String nome, String cognome, String username, String password, String mail, String codiceContoCorrente)
	{
		if(nome.matches(".*\\d+.*") || cognome.matches(".*\\d+.*"))	// Il nome o il cognome contengono dei numeri
			return false;
		if(password.length() < 5)	// La password deve essere composta da almeno 5 caratteri alfanumerici
			return false;
		if (codiceContoCorrente!=null)
			if(!codiceContoCorrente.matches(".*\\d+.*"))	// Il codice del conto corrente non deve contenere caratteri
				return false;
		return true;
	}
	
    /**
     * Metodo per autenticare l'Amministratore
     */
    public Amministratore convalidaLogin(String username, String password) throws DeserializzazioneException, SerializzazioneException, PasswordException, UtenteException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
    	getUtenti().put(username, new Amministratore(username,password));
    	String usernameInserito = getUtenti().get(username).getUsername();
    	String passwordInserita = getUtenti().get(username).getPassword();
    	String percorsoFileLogin = "src/main/java/laptop/save/login.txt";
    	if(controllaCredenziali(percorsoFileLogin,usernameInserito, passwordInserita))
			return (Amministratore)getUtenti().get(username);
    	return null;
    }
    

	/**
	 * Metodo per controllare se le credenziali inserite dall'amministratore sono valide
	 * Deserializzo il file login.txt
	 */
	public boolean controllaCredenziali(String fileLogin, String usernameInserito, String passwordInserita) throws DeserializzazioneException,  PasswordException, UtenteException, ClassNotFoundException
    {
    	File login = new File(fileLogin);
    	if(!login.exists())
    	{
    		System.out.println("Nessun file login presente!");
    	}
    	PersistenzaFile u = new PersistenzaFile(fileLogin);
    	Utente admin = (Amministratore ) u.deserializza();
    	this.utenti.put(admin.getUsername(),admin);
    	if(usernameInserito.equals(admin.getUsername()) && passwordInserita.equals(admin.getPassword()))
    		return true;
    	else
    	{
    		if(!usernameInserito.equals(admin.getUsername()))
    				throw new UtenteException();
    		if(!passwordInserita.equals(admin.getPassword()))
    				throw new PasswordException();
    	}
    	return false;
    }


 
    public synchronized void modificaAccount(UtenteBean ub) throws UserException 
    {
			GestoreEntitaBean.conversioneInUtente(ub,this.getUtenti().get(ub.getUsername()));
			FactoryUtente fu = new FactoryUtente();
			fu.aggiornaUtente(this.getUtenti().get(ub.getUsername()));
    }

    public synchronized boolean eliminaAccount(UtenteBean ub,CatalogoBean catalogo) throws EliminaStrutturaException, CameraException, PrenotazioneException, UserException 
    {
    	Utente u = this.utenti.get(ub.getUsername());
    	if (u instanceof Gestore)
    	{
    		GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
    		for (int i=0;i<catalogo.getStrutture().size();i++)
    			controllerLocazioni.rimuoviStruttura(0,ub,catalogo);
    	}
    	else if (u instanceof Affittuario)
    	{
    		GestorePrenotazioni controllerPrenotazioni = new GestorePrenotazioni();
    		for (int i=0;i<ub.getPrenotazioni().size();i++)
    		{
    			for (int j=0;j<ub.getPrenotazioni().get(0).getLinee().size();j++)
    				controllerPrenotazioni.eliminaLineaPrenotazione(0, 0, ub);
    		}
    	}
    	FactoryUtente fu = new FactoryUtente();
    	fu.eliminaUtente(this.getUtenti().get(ub.getUsername()));
    	this.utenti.remove(ub.getUsername());
    	return true;
    }




	public synchronized Map<String,Utente> getUtenti() {
		if (this.utenti == null)
			utenti = new HashMap<String, Utente>();
		return utenti;
	}

	public synchronized void setUtenti(HashMap<String,Utente> utenti) {
		this.utenti = utenti;
	}


}