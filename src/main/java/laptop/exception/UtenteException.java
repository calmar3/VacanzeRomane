package laptop.exception;

public class UtenteException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UtenteException()
	{}
	
	public UtenteException(String error)
	{
		super(error);
	}
	
	public String toString()
	{
		return "Utente Errato";
	}
}
