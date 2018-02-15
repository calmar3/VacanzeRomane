package bean;




import java.util.ArrayList;
import java.util.List;

import controller.GestoreUtente;
import exception.LoginException;
import exception.RegistrazioneException;
import exception.UserException;


/**
 * Bean associato ad un utente 
 * Contiene un campo tipologia per distinguere se l'utente sia 
 * Affittuario o Gestore
 */
public class UtenteBean 
{

	/**
	 * Dati per utente registrato
	 */
	private String username;
	private String password;
	private String nome;
	private String cognome;
	private String mail;
	
	/**
	 * Solo il gestore deve inserirlo
	 */
	private String codiceContoCorrente;

	/**
	 *  Attributi per veririfare se l'utente abbia effettuato il log
	 *  nel sistema e sotto quale ruolo
	 */
	private boolean log;
	private String tipologia;
	
	
	/**
	 * Prenotazioni associate ad un affittuario
	 */
	private List<PrenotazioneBean> prenotazioni;

	
	/**
	 * Costruttore di default
	 */
	public UtenteBean()
	{
		this.username = "";
		this.password = "";
		this.nome = "";
		this.cognome = "";
		this.tipologia = "";
		this.mail = "";
		this.log = false;
		this.setCodiceContoCorrente("");
	}
	
	/**
	 * Verifica che le credenziali di accesso non siano stringhe vuote
	 * Invoca il controller degli utenti per convalidare il Login
	 * Genera eccezione nel caso in cui il Login non avvenga con successo
	 * @return
	 * @throws LoginException
	 * @throws UserException
	 */
	public boolean convalida() throws LoginException, UserException 
	{
		this.log = false;
		if(this.username.equals("") || this.password.equals(""))
			return this.log;
		GestoreUtente Controller = GestoreUtente.avviaControllerUtente();
		this.log = Controller.inserisciCredenziali(this);
		if(log == false)
			throw new LoginException("Utente inesistente");
		return  log;
	}
	
	
	/**
	 * Verifica che le nuove credenziali non siano stringhe vuote
	 * Invoca il controller degli utenti per modificare l'account dell'utente
	 * @return
	 * @throws UserException
	 */
	public boolean modifica() throws UserException
	{
		if (this.username.equals("") || this.password.equals("") || this.nome.equals("") || this.cognome.equals("") || this.mail.equals("") ) 
		{	
			return false;
		}
		GestoreUtente Controller = GestoreUtente.avviaControllerUtente();
		Controller.modificaAccount(this);
		return  (Controller.getUtenti().get(username)!=null);
	}

	
	/**
	 * Verifica che i parametri per la registrazione non siano stringhe vuote
	 * Invoca il controller per gli utenti al fine di effettuare una nuova registrazione
	 * Genera eccezione nel caso in cui la registrazione non avvenga con successo
	 * @return
	 * @throws UserException
	 * @throws RegistrazioneException
	 */
	public boolean registra() throws UserException, RegistrazioneException 
	{
		this.log = false;
		GestoreUtente Controller = GestoreUtente.avviaControllerUtente();
		if(!Controller.convalidaRegistrazione(this.nome, this.cognome, this.username, this.password , this.mail,this.tipologia,this.codiceContoCorrente))
			{
				throw new RegistrazioneException();
			}
		this.log = (Controller.getUtenti().get(username)!=null);
		return  log;
	}
	
	
	/**
	 * GET - SET
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}
	

	public List<PrenotazioneBean> getPrenotazioni() {
		if (prenotazioni == null)
			prenotazioni = new ArrayList<PrenotazioneBean>();
		return prenotazioni;
	}

	public void setPrenotazioni(List<PrenotazioneBean> prenotazioni) {
		this.prenotazioni = prenotazioni;
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
