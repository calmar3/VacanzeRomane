package controller;


import java.util.ArrayList;
import java.util.List;

import bean.CameraBean;
import bean.LineaPrenotazioneBean;
import bean.PagamentoBean;
import bean.PrenotazioneBean;
import bean.ServizioBean;
import bean.StrutturaBean;
import bean.UtenteBean;
import entity.Affittuario;
import entity.Camera;
import entity.CatalogoStrutture;
import entity.DisponibilitaCamera;
import entity.LineaPrenotazione;
import entity.Prenotazione;
import entity.Servizio;
import entity.Struttura;
import exception.CameraException;
import exception.LineaPrenotazioneException;
import exception.PrenotazioneException;
import hibernate.FactoryCamera;


public class GestorePrenotazioni 
{		
	    
		// Costruttore
	    public GestorePrenotazioni() 
	    {
	    }
	    
		
	    public boolean prenotaCamera(StrutturaBean sb,UtenteBean ub,PrenotazioneBean pb, CameraBean cb,LineaPrenotazioneBean lpb) throws LineaPrenotazioneException
		{
	    	if (lpb.getCheckIn().equals("") && lpb.getCheckOut().equals(""))
	    		return false;
		    Affittuario a = (Affittuario) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
		    CatalogoStrutture catalogoStrutture = CatalogoStrutture.prendiCatalogo();
			Struttura struttura = catalogoStrutture.cercaDaNome(sb.getNome());
			Camera c = null;
			if (struttura!=null)
				c = struttura.selezionaCamera(cb.getNomeCamera());
			List<Servizio> serviziScelti = new ArrayList<Servizio>();
			for(Servizio servizio: c.getServizi())
			{
				ServizioBean servizioBean = new ServizioBean();
				GestoreEntitaBean.convertiInServizioBean(servizio, servizioBean);
				if(lpb.getServiziScelti().contains(servizioBean))
					serviziScelti.add(servizio);
			}
			Double prezzoLinea = 0.0;
			if (c!=null)
				prezzoLinea = a.prenotaCamera(struttura,c,lpb.getCheckIn(),lpb.getCheckOut(),serviziScelti);
			GestoreUtente.avviaControllerUtente().getUtenti().put(ub.getUsername(), a);
			lpb.setCamera(cb);
			lpb.setImporto(Double.toString(prezzoLinea));
			Double prezzo = Double.parseDouble(lpb.getImporto()) + Double.parseDouble(pb.getImporto());
			pb.setImporto(Double.toString(prezzo));
			pb.getLinee().add(lpb);
			return true;
		}
		
		public synchronized static boolean confermaPrenotazione(PagamentoBean pagamentoBean,UtenteBean ub) throws PrenotazioneException, CameraException
		{
	    	Affittuario a = (Affittuario) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
			a.getPrenotazione().effettuaPagamento(pagamentoBean);
			a.effettuaPrenotazione();
			a.setPrenotazione(null);
			return true;
		}

		public void caricaPrenotazioniUtente(UtenteBean ub)
		{
			ub.getPrenotazioni().clear();
			Affittuario affittuario = (Affittuario) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
			for(Prenotazione prenotazione: affittuario.getPrenotazioni())
			{
				PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
				GestoreEntitaBean.conversioneInPrenotazioneBean(prenotazione, prenotazioneBean);
				for (LineaPrenotazione linea : prenotazione.getLinee()) {
					LineaPrenotazioneBean lineaBean = new LineaPrenotazioneBean();
					GestoreEntitaBean.conversioneInLineaPrenotazioneBean(linea, lineaBean);
					prenotazioneBean.getLinee().add(lineaBean);
				}
				ub.getPrenotazioni().add(prenotazioneBean);
			}
		}

		public boolean eliminaLineaPrenotazione(int indicePrenotazione, int indiceLinea,UtenteBean ub) throws PrenotazioneException, CameraException
		{
			Affittuario affittuario = (Affittuario) GestoreUtente.avviaControllerUtente().getUtenti().get(ub.getUsername());
			affittuario.eliminaLineaPrenotazione(indicePrenotazione, indiceLinea);
			return true;
		}

		public synchronized static void rimuoviDisponibilita(LineaPrenotazione lineaDaRimuovere) throws CameraException 
		{
			Camera c = lineaDaRimuovere.getCameraLP();
			for (DisponibilitaCamera dc: c.getDisponibilita())
			{
				if(dc.getDataArrivo().equals(lineaDaRimuovere.getDataCheckIn())
						&& dc.getDataPartenza().equals(lineaDaRimuovere.getDataCheckOut()))
				{
					c.getDisponibilita().remove(dc);
					FactoryCamera fc = new FactoryCamera();
					fc.eliminaDisponibilita(dc);
					fc.aggiornaCamera(c);
					CatalogoStrutture.modificaCamera(c.getStruttura(),c);
				}
			}
			
		}

		public synchronized static boolean aggiungiDisponibilita(Prenotazione prenotazione) throws CameraException
		{
			for (LineaPrenotazione lp: prenotazione.getLinee())
			{
				if (lp.getCameraLP().isDisponibile(lp.getDataCheckIn(),lp.getDataCheckOut()))
				{
					DisponibilitaCamera dc = new DisponibilitaCamera(lp.getDataCheckIn(),lp.getDataCheckOut());
					dc.setCamera(lp.getCameraLP());
					lp.getCameraLP().getDisponibilita().add(dc);
					FactoryCamera fc = new FactoryCamera();
					fc.aggiornaCamera(lp.getCameraLP());
					CatalogoStrutture.modificaCamera(prenotazione.getStrutturaPrenotata(), lp.getCameraLP());
				}	
				else 
					return false;
			}
			return true;
		}

	    

}

