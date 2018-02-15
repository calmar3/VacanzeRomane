package entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity  
@Table(name="ServizioPremium")  
public class ServizioPremium extends Servizio 
{
	@Column (name="prezzo")
	private double prezzo;
	
	/**
	 * Costruttore di default
	 */
	public ServizioPremium(){
	}
	
	/**
	 * Costruttore con parametri
	 * @param c
	 * @param nome
	 * @param descrizione
	 * @param prezzo
	 */
	public ServizioPremium(Camera c, String nome,String descrizione,String prezzo)  
	{
		super.setCamera(c);
		super.setDescrizione(descrizione);
		super.setNome(nome);
		this.prezzo = Double.parseDouble(prezzo);
	}

	/**
	 * GET - SET
	 */
	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

}