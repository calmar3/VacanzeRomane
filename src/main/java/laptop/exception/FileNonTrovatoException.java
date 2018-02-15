package laptop.exception;

public class FileNonTrovatoException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public FileNonTrovatoException()
	{
		
	}
	
	public FileNonTrovatoException(String error)
	{
		super(error);
	}
	
}
