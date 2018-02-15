package laptop.exception;

public class PasswordException extends Exception
{
	private static final long serialVersionUID = 1L;

	public PasswordException()
	{}
	
	public PasswordException(String error)
	{
		super(error);
	}
	
	public String toString()
	{
		return "Password Errata";
	}
}
