package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Composta principalmente da una data di check in ed una di check out
 * Associata ad una Camera
 */
@Entity
@Table(name = "DisponibilitaCamera" , uniqueConstraints = @UniqueConstraint(columnNames = {"checkIn", "checkOut","id_camera"}))
public class DisponibilitaCamera implements Comparable<DisponibilitaCamera>
{
	@ManyToOne
    @JoinColumn(name = "id_camera")
	private Camera camera;
	
    @Temporal(TemporalType.DATE)
	private Date checkIn;
    
    @Temporal(TemporalType.DATE)
	private Date checkOut;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
	
	
	/**
	 * Costruttore di default
	 */
	public DisponibilitaCamera()
	{}
	
	/**
	 * Costruttore con parametri
	 * @param cI
	 * @param cO
	 */
	public DisponibilitaCamera(Date cI, Date cO)
	{
		this.checkIn = cI;
		this.checkOut = cO;
	}
	
	public DisponibilitaCamera(String CI, String CO)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.checkIn = new Date();
		this.checkOut = new Date();
		try {
			this.checkIn = formatter.parse(CI);
			this.checkOut = formatter.parse(CO);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.id = 0;
	}
	/**
	 * Controlla che la disponibilita che si intende aggiungere alla lista
	 * non si sovrapponga a questa istanza di disponibilita
	 * @param daControllare
	 * @return
	 */
	public boolean controlloSovrapposizioni(DisponibilitaCamera daControllare) 
	{
		if ( 	(this.checkIn.before(daControllare.checkIn) || this.checkIn.equals(daControllare.checkIn))
				&& (this.checkOut.after(daControllare.checkIn))	) 
			return true;
		else if (	(this.checkIn.before(daControllare.checkOut) )
				&& (this.checkOut.after(daControllare.checkOut) || this.checkOut.equals(daControllare.checkOut) ))
			return true;
		else
			return false;
	}

	/**
	 * Verifica che la data di check-out della prenotazione che si intende effettuare sia
	 * precedente alla data di check-in di questa prenotazione
	 * @param daControllare
	 * @return
	 */
	public boolean controlloPrimaSovrapposizione(DisponibilitaCamera daControllare) {
		if (this.checkIn.after(daControllare.getDataPartenza()) || this.checkIn.equals(daControllare.getDataPartenza()))
			return false;
		return true;
	}

	/**
	 * Verifica che la data di check-in della prenotazione che si intende effettuare
	 * sia successiva alla data di check-out di questa prenotazione
	 * @param daControllare
	 * @return
	 */
	public boolean controlloUltimaSovrapposizione(DisponibilitaCamera daControllare) {
		if (this.checkOut.before(daControllare.getDataArrivo()) || this.checkOut.equals(daControllare.getDataArrivo()))
			return false;
		return true;
	}

	
	
	/**
	 * GET - SET 
	 */
	public Date getDataArrivo()
	{
		return this.checkIn;
	}
	
	public Date getDataPartenza(){
		return this.checkOut;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * metodo per effettuare la comparazione utile nei treeset
	 */
	public int compareTo(DisponibilitaCamera o) {
		if(this.getDataArrivo().before(o.getDataArrivo()))
			return -1;
		else if (this.getDataArrivo().equals(o.getDataArrivo()) && this.getId().equals(o.getId()))
			return 0;
		else return 1;
	}

}
