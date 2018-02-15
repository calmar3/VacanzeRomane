package laptop.control;

import hibernate.FactoryPrenotazione;
import hibernate.FactoryStruttura;
import hibernate.FactoryUtente;
import laptop.exception.FileNonTrovatoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Prenotazione;
import entity.Struttura;
import entity.Utente;


public class GestoreMonitoraggio 
{
	private static GestoreMonitoraggio controllerMonitoraggio;
	private static List<Utente> utenti = new ArrayList<Utente>();
	private static List<Struttura> strutture = new ArrayList<Struttura>();
	private static List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
	
	
	private GestoreMonitoraggio()
	{
		
	}
	
	public synchronized static GestoreMonitoraggio avviaControllerMonitoraggio()
	{
		if(controllerMonitoraggio == null)
			controllerMonitoraggio = new GestoreMonitoraggio();
		return controllerMonitoraggio;
	}
	
	public long numeroUtentiRegistrati()
	{
		return utenti.size();
	}
	
	public void utentiRegistrati()
	{
		if (utenti != null)
			utenti.clear();
		FactoryUtente fu = new FactoryUtente();
		utenti = fu.selezionaUtentiRegistrati();

	}
	
	public void strutturePresenti()
	{
		if (strutture != null)
			strutture.clear();
		FactoryStruttura fs = new FactoryStruttura();
		setStrutture(fs.selezionaTutto());
	}
	
	public void prenotazioniEffettuate()
	{
		if(prenotazioni != null)
			prenotazioni.clear();
		FactoryPrenotazione fp = new FactoryPrenotazione();
		setPrenotazioni(fp.selezionaTuttePrenotazioni());
	}
	

	public String caricaLog(String percorso) throws FileNonTrovatoException, IOException
	{
		String contenutoLog = new String();
		FileReader fileReader;
		File fileLog = new File(percorso);
		try 
		{			
			fileReader = new FileReader(fileLog);
			BufferedReader bufferedReader = new BufferedReader(fileReader); 
			String s;
			while((s = bufferedReader.readLine()) != null) 
			{
				contenutoLog = contenutoLog + s +"\n";
			} 
			fileReader.close();
			return contenutoLog;
		} 
		catch(FileNotFoundException f)
		{
			throw new FileNonTrovatoException("Nessun file presente");
		}
	}

	/**
	 * @return the utenti
	 */
	public static List<Utente> getUtenti() {
		return utenti;
	}

	/**
	 * @param utenti the utenti to set
	 */
	public static void setUtenti(List<Utente> utenti) {
		GestoreMonitoraggio.utenti = utenti;
	}

	/**
	 * @return the strutture
	 */
	public static List<Struttura> getStrutture() {
		return strutture;
	}

	/**
	 * @param strutture the strutture to set
	 */
	public static void setStrutture(List<Struttura> strutture) {
		GestoreMonitoraggio.strutture = strutture;
	}

	public static List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public static void setPrenotazioni(List<Prenotazione> prenotazioni) {
		GestoreMonitoraggio.prenotazioni = prenotazioni;
	}

	
}
