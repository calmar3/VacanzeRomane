package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import exception.LineaPrenotazioneException;

@Entity
@Table(name = "LineaPrenotazione")
public class LineaPrenotazione {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea")
	private Integer idLP;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dataCheckIn")
    private Date dataCheckIn;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Camera cameraLP;
    
    
    @Column(name="importo")
    private Double importo;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "linea_servizio", 
               joinColumns = { @JoinColumn(name = "id_linea") }, 
               inverseJoinColumns = { @JoinColumn(name = "id_servizio") })
    private List<Servizio> serviziScelti;
    
    @Temporal(TemporalType.DATE)
    @Column(name="dataCheckOut")
    private Date dataCheckOut;
    
    @ManyToOne
    @JoinColumn(name = "id_prenotazione")
    private Prenotazione prenotazione;
    /**
     * Default constructor
     */
    public LineaPrenotazione() {
    }
    


    /**
     * Costruttore con parametri
     * @param c
     * @param p
     * @param serviziScelti 
     * @param lpb
     * @throws LineaPrenotazioneException 
     */
    public LineaPrenotazione(Camera c, Prenotazione p,String checkIn,String CheckOut, List<Servizio> servizi) throws LineaPrenotazioneException {
    	this.serviziScelti = new ArrayList<Servizio>();
    	this.cameraLP = c;
    	
    	this.prenotazione = p;

    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.dataCheckIn = new Date();
		this.dataCheckOut = new Date();
		try {
			this.dataCheckIn = formatter.parse(checkIn);
			this.dataCheckOut = formatter.parse(CheckOut);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dataCheckIn.after(dataCheckOut))
			throw new LineaPrenotazioneException("Check-in dopo Check-out?");
		final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

		int diffInDays = (int) ((this.dataCheckOut.getTime() - this.dataCheckIn.getTime())/ DAY_IN_MILLIS );
		this.setImporto(c.getPrezzoCamera()*diffInDays);
		this.aggiungiServiziScelti(servizi);
	}
    
    /**
     * Aggiunge i servizi scelti modificando il proprio prezzo
     * @param servizi
     */
    private void aggiungiServiziScelti(List<Servizio> servizi)
    {
    	for (Servizio s: servizi)
    	{
    		if (s.isPremium())
    		{
    			ServizioPremium sp = (ServizioPremium) s;
    			this.importo += sp.getPrezzo();
    		}
    		this.getServiziScelti().add(s);
    	}
    }

	/**
	 * rimuove i servizi scelti
	 */
	public void rimuoviServiziScelti() {
		this.serviziScelti.clear();
		
	} 
    
    
    
    /**
     * Get-Set
     */
	public Camera getCameraLP() {
		return this.cameraLP;
	}

	public void setCameraLP(Camera c) {
		this.cameraLP = c;
	}

	public Date getDataCheckIn() {
		return dataCheckIn;
	}

	public void setDataCheckIn(Date dataCheckIn) {
		this.dataCheckIn = dataCheckIn;
	}

	public Date getDataCheckOut() {
		return dataCheckOut;
	}

	public void setDataCheckOut(Date dataCheckOut) {
		this.dataCheckOut = dataCheckOut;
	}

	public Prenotazione getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public Integer getIdLP() {
		return idLP;
	}

	public void setIdLP(Integer idLP) {
		this.idLP = idLP;
	}

	public Double getImporto() {
		return importo;
	}
	
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	
	public List<Servizio> getServiziScelti() {
		return serviziScelti;
	}

	public void setServiziScelti(List<Servizio> serviziScelti) {
		this.serviziScelti = serviziScelti;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaPrenotazione other = (LineaPrenotazione) obj;
		if (cameraLP == null) {
			if (other.cameraLP != null)
				return false;
		} else if (!cameraLP.equals(other.cameraLP))
			return false;
		if (dataCheckIn == null) {
			if (other.dataCheckIn != null)
				return false;
		} else if (!dataCheckIn.equals(other.dataCheckIn))
			return false;
		if (dataCheckOut == null) {
			if (other.dataCheckOut != null)
				return false;
		} else if (!dataCheckOut.equals(other.dataCheckOut))
			return false;
		if (idLP == null) {
			if (other.idLP != null)
				return false;
		} else if (!idLP.equals(other.idLP))
			return false;
		if (importo == null) {
			if (other.importo != null)
				return false;
		} else if (!importo.equals(other.importo))
			return false;
		if (prenotazione == null) {
			if (other.prenotazione != null)
				return false;
		} else if (!prenotazione.equals(other.prenotazione))
			return false;
		if (serviziScelti == null) {
			if (other.serviziScelti != null)
				return false;
		} else if (!serviziScelti.equals(other.serviziScelti))
			return false;
		return true;
	}
	


}