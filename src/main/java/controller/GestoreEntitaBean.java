package controller;



import bean.CameraBean;
import bean.LineaPrenotazioneBean;
import bean.PrenotazioneBean;
import bean.ServizioBean;
import bean.StrutturaBean;
import bean.UtenteBean;
import entity.Affittuario;
import entity.Camera;
import entity.Gestore;
import entity.LineaPrenotazione;
import entity.Prenotazione;
import entity.Servizio;
import entity.ServizioPremium;
import entity.Struttura;
import entity.Utente;

/**
 * Classe astratta con soli metodi statici
 * che si occupano di convertire un oggetto di un tipo appartenente al dominio
 * nel relativo bean dello stesso tipo e vice-versa
 */
public abstract class GestoreEntitaBean {

	public synchronized static void conversioneInUtenteBean(Utente u, UtenteBean ub)
	{
		ub.setCognome(u.getCognome());
		ub.setNome(u.getNome());
		ub.setMail(u.getMail());
		if (u instanceof Affittuario)
			ub.setTipologia("Affittuario");
		else
		{
			Gestore g = (Gestore) u;
			ub.setTipologia("Gestore");
			ub.setCodiceContoCorrente(g.getCodiceContoCorrente());
		}
	}
	
	public synchronized static void conversioneInStrutturaBean(Struttura s, StrutturaBean sb)
	{
		sb.setCitta(s.getCitta());
		sb.setDescrizione(s.getDescrizione());
		sb.setIndirizzo(s.getIndirizzo());
		sb.setNazione(s.getNazione());
		sb.setNome(s.getNome());
		sb.setTipologia(s.getTipologia());
	}

	public synchronized static void conversioneInCameraBean(Camera c, CameraBean cb) {
		cb.setDescrizioneCamera(c.getDescrizioneCamera());
		cb.setNomeCamera(c.getNomeCamera());
		cb.setTipologiaCamera(c.getTipologia());
		cb.setPrezzoCamera(Double.toString(c.getPrezzoCamera()));	
	}
	
	public synchronized static void conversioneInLineaPrenotazioneBean(LineaPrenotazione linea, LineaPrenotazioneBean lineaBean) {
		GestoreEntitaBean.conversioneInCameraBean(linea.getCameraLP(), lineaBean.getCamera());
			lineaBean.setCheckIn(linea.getDataCheckIn().toString());
			lineaBean.setCheckOut(linea.getDataCheckOut().toString());
			lineaBean.setImporto(Double.toString(linea.getImporto()));	
	}
	
	public synchronized static void conversioneInPrenotazioneBean(Prenotazione prenotazione,PrenotazioneBean prenotazioneBean) {
		prenotazioneBean.setImporto(Double.toString(prenotazione.getImporto()));
		prenotazioneBean.setPin(prenotazione.getCodicePin());
	}

	public synchronized static void conversioneInUtente(UtenteBean ub, Utente utente) {
		if (!ub.equals(utente.getNome()))
			utente.setNome(ub.getNome());
		if (!ub.getCognome().equals(utente.getCognome()))
			utente.setCognome(ub.getCognome());
		if (!ub.getMail().equals(utente.getMail()))
			utente.setMail(ub.getMail());
		if (!ub.getPassword().equals(utente.getPassword()))
			utente.setPassword(ub.getPassword());
		if (!ub.getUsername().equals(utente.getUsername()))
			utente.setUsername(ub.getUsername());
	}

	public synchronized static void convertiInServizioBean(Servizio servizio, ServizioBean servizioBean) {
		servizioBean.setNomeServizio(servizio.getNome());
		servizioBean.setDescrizioneServizio(servizio.getDescrizione());
		if (servizio.isPremium())
		{	
			ServizioPremium sp = (ServizioPremium) servizio;
			servizioBean.setPrezzoServizio(Double.toString(sp.getPrezzo()));
			servizioBean.setTipologiaServizio("1");
		}
		else
			servizioBean.setTipologiaServizio("0");		
	}

}
