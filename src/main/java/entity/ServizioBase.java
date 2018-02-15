package entity;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  
@Table(name="ServizioBase")  
public class ServizioBase extends Servizio 
{
		   
	/**
	 * Costruttore di default
	 */
	public ServizioBase(){
	}
	
	/**
	 * Costruttore con parametri
	 * @param c
	 * @param nome
	 * @param descrizione
	 */
	public ServizioBase(Camera c, String nome,String descrizione)  
	{
		super.setCamera(c);
		super.setDescrizione(descrizione);
		super.setNome(nome);
	}

}