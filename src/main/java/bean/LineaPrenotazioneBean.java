package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean relativo ad una linea prenotazione
 *
 */
public class LineaPrenotazioneBean {

	
	/**
	 * Camera associata alla linea prenotazione
	 */
	private CameraBean camera;
	
	/**
	 * Servizi scelti fra quelli associati alla camera
	 */
	private List<ServizioBean> serviziScelti;
	
	/**
	 * Attributi da mostrare di una linea prenotazione
	 */
	private String checkIn, checkOut;
	private String importo;

	
	/**
	 * Costruttore di default
	 */
	public LineaPrenotazioneBean() {
		this.setCamera(new CameraBean());
		this.setCheckIn("");
		this.setCheckOut("");
		this.setImporto("0");
		this.serviziScelti = new ArrayList<ServizioBean>();
	}
	
	/**
	 * Metodo che a partire dagli indici dei serviziScelti nel form
	 * preleva i corrispondenti servizi disponibili per la Camera
	 * aggiungendoli alla linea prenotazione
	 * @param serviziCamera
	 * @param serviziSelezionati
	 */
	public void aggiungiServiziScelti(List<ServizioBean> serviziCamera,String[] serviziSelezionati) 
	{
		double prezzoServizi = Double.parseDouble(this.getImporto());
		for (int i=0;i<serviziCamera.size();i++)
		{
			if (serviziCamera.get(i).getTipologiaServizio().equals("0"))
				this.serviziScelti.add(serviziCamera.get(i));
			else if (serviziSelezionati!=null)
			{
				for(int j=0;j<serviziSelezionati.length;j++)
					{
						if(serviziCamera.get(i).getNomeServizio().equals(serviziSelezionati[j]))
						{
							this.serviziScelti.add(serviziCamera.get(i));
							prezzoServizi += Double.parseDouble(serviziCamera.get(i).getPrezzoServizio());
						}
					}
			}
		}
		this.setImporto(Double.toString(prezzoServizi));
	}

	/**
	 * GET - SET
	 */
	public CameraBean getCamera() {
		return camera;
	}

	public void setCamera(CameraBean camera) {
		this.camera = camera;
	}



	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public List<ServizioBean> getServiziScelti() {
		return serviziScelti;
	}
	
	public void setServiziScelti(List<ServizioBean> serviziScelti) 
	{
			this.serviziScelti = serviziScelti;
	}
	

	




}
