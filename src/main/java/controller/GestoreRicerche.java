package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.CameraBean;
import bean.CatalogoBean;
import bean.RicercaBean;
import bean.ServizioBean;
import bean.StrutturaBean;
import entity.Camera;
import entity.CatalogoStrutture;
import entity.Servizio;
import entity.Struttura;


public class GestoreRicerche 
{
	
	public GestoreRicerche() {}


	/**
	 * @param catalogo
	 * @param rb
	 * Il ricerca Bean dovrà essere utilizzato per prendere i parametri che non sono nulli ed effettuare 
	 * la ricerca
	 */
	
	public void ricercaStrutture(CatalogoBean catalogo, RicercaBean rb)
	{
		catalogo.getStrutture().clear();
		CatalogoStrutture catalogoDominio = CatalogoStrutture.prendiCatalogo();
		List<Struttura> strutture = new ArrayList<Struttura>();
		for (Struttura s : catalogoDominio.getStrutture())
			strutture.add(s.clone());
		if (rb!=null)
		{	        
			if (!rb.getLuogo().equals(""))
				strutture = this.ricercaPerCitta(strutture, rb.getLuogo());
			if (!rb.getTipologiaStruttura().equals(""))
				strutture = this.ricercaPerTipologiaStruttura(strutture, rb.getTipologiaStruttura());
			if (!rb.getTipologiaCamera().equals(""))
				strutture = this.ricercaPerTipologiaCamera(strutture, rb.getTipologiaCamera());
			if (!rb.getPrezzoMin().equals(""))
				strutture = this.ricercaPerPrezzoMinimoCamera(strutture,rb.getPrezzoMin());
			if (!rb.getPrezzoMax().equals(""))
				strutture = this.ricercaPerPrezzoMassimoCamera(strutture, rb.getPrezzoMax());
			if(!(rb.getDataCheckIn().equals("")) && !(rb.getDataCheckOut().equals("")))
				strutture = this.ricercaPerDisponibilita(strutture,rb.getDataCheckIn(),rb.getDataCheckOut());
			if (!(rb.getNumeroPersone().equals("")))
				strutture = this.ricercaPerNumeroPersone(strutture,rb.getNumeroPersone());
		}	
    	if (strutture == null)
    	{
    		strutture = new ArrayList<Struttura>();
    		return;
    	}
    	else if (strutture.size()==0)
    		return;
    	for (Struttura s: strutture)
    	{
    		StrutturaBean sb = new StrutturaBean();
    		for (Camera c : s.getCamere()) {
				CameraBean cameraBean = new CameraBean();
				GestoreEntitaBean.conversioneInCameraBean(c, cameraBean);
				for (Servizio servizio: c.getServizi())
				{
					ServizioBean servizioBean = new ServizioBean();
					GestoreEntitaBean.convertiInServizioBean(servizio,servizioBean);
					cameraBean.getServizi().add(servizioBean);
				}
				sb.getCamere().add(cameraBean);
			}
    		GestoreEntitaBean.conversioneInStrutturaBean(s, sb);
    		catalogo.getStrutture().add(sb);
    	}
	}
	
	private List<Struttura> ricercaPerNumeroPersone(List<Struttura> strutture, String numeroPersone) {
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		double num= Double.parseDouble(numeroPersone);
		for(Struttura s: strutture)
		{
			int capacitaStruttura = 0;
			for (Camera c: s.getCamere())	
				capacitaStruttura += c.capacita();
			if (capacitaStruttura>=num)
				nuoveStrutture.add(s);
		}
		return nuoveStrutture;
	}


	private List<Struttura> ricercaPerDisponibilita(List<Struttura> strutture, String dataCheckIn,
			String dataCheckOut) {
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date checkIn = new Date();
		Date checkOut = new Date();
		try {
			checkIn = formatter.parse(dataCheckIn);
			checkOut = formatter.parse(dataCheckOut);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for(Struttura s: strutture)
		{
			List<Camera> camere = new ArrayList<Camera>();
			for (Camera c: s.getCamere())	
			{
				if (c.isDisponibile(checkIn,checkOut))
					camere.add(c);
			}
			if (camere.size()>0)
			{
				s.setCamere(camere);
				nuoveStrutture.add(s);
			}
		}
		return nuoveStrutture;
	}


	private List<Struttura> ricercaPerPrezzoMassimoCamera(List<Struttura> strutture, String prezzoMax) 
	{
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		double max= Double.parseDouble(prezzoMax);
		for(Struttura s: strutture)
		{
			List<Camera> camere = new ArrayList<Camera>();
			for (Camera c: s.getCamere())	
			{
				if ( c.getPrezzoCamera()<=max)
					camere.add(c);
			}
			if (camere.size()>0)
			{
				s.setCamere(camere);
				nuoveStrutture.add(s);
			}
		}
		return nuoveStrutture;
	}


	private List<Struttura> ricercaPerPrezzoMinimoCamera(List<Struttura> strutture, String prezzoMin) 
	{
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		double min= Double.parseDouble(prezzoMin);
		for(Struttura s: strutture)
		{
			List<Camera> camere = new ArrayList<Camera>();
			for (Camera c: s.getCamere())	
			{
				if ( c.getPrezzoCamera()>=min)
					camere.add(c);
			}
			if (camere.size()>0)
			{
				s.setCamere(camere);
				nuoveStrutture.add(s);
			}
		}
		return nuoveStrutture;
	}


	private List<Struttura> ricercaPerTipologiaCamera(List<Struttura> strutture, String tipologiaCamera) {
		
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		for(Struttura s: strutture)
		{
			List<Camera> camere = new ArrayList<Camera>();
			for (Camera c: s.getCamere())	
				if (c.getTipologia().equals(tipologiaCamera))
					camere.add(c);
			if (camere.size()>0)
			{
				s.setCamere(camere);
				nuoveStrutture.add(s);
			}
		}
		return nuoveStrutture;
		
	}


	private List<Struttura> ricercaPerCitta(List<Struttura> strutture,String citta)
	{
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		for (Struttura s : strutture)
			if (this.verificaSimilitudine(s.getCitta(),citta))
				nuoveStrutture.add(s);
		return nuoveStrutture;

	}
	
	private boolean verificaSimilitudine(String cittaStruttura, String cittaDaCercare) {
		int differenze = 0;
		String luogoOriginario = new String(cittaStruttura);
		luogoOriginario = luogoOriginario.toLowerCase();
		String luogoDaVerificare = new String(cittaDaCercare);
		luogoDaVerificare = luogoDaVerificare.toLowerCase();
		for (int i=0;i<luogoOriginario.length();i++)
		{
			if (i>=luogoDaVerificare.length())
				break;
			if (luogoOriginario.charAt(i) != luogoDaVerificare.charAt(i))
				differenze ++;
			if (differenze > 2)
				return false;
		}
		int lunghezza = Math.abs(cittaStruttura.length()-cittaDaCercare.length());
		if (differenze==2 && lunghezza>0)
			return false;
		else if (differenze ==1 && lunghezza >1)
			return false;
		return true;
	}


	private List<Struttura> ricercaPerTipologiaStruttura(List<Struttura> strutture,String tipologia)
	{
		List<Struttura> nuoveStrutture = new ArrayList<Struttura>();
		for (Struttura s : strutture)
			if (s.getTipologia().equals(tipologia))
				nuoveStrutture.add(s);
		return nuoveStrutture;
	}
	


}
