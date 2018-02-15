package exception;

import org.hibernate.HibernateException;

public class LineaPrenotazioneException extends PrenotazioneException
{
	private static final long serialVersionUID = 1L;
	
	public LineaPrenotazioneException(String error) 
	{
		super(error);
	}

	public LineaPrenotazioneException()
	{
		super("Errore nella Linea Prenotazione");
	}
	
	public LineaPrenotazioneException(String error, HibernateException e)
	{
		super(error+" Causa: "+e);
	}
	
}
