package laptop.exception;

public class LogoutException extends Exception 
{
	private static final long serialVersionUID = 1L;

	public LogoutException()
	{
		
	}
	
	public LogoutException(String error)
	{
		super(error);
	}
	
	public String toString()
	{
		return "Errore nel logout";
	}
}

