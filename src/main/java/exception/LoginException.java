package exception;

import org.hibernate.HibernateException;

public class LoginException extends UserException
{
	private static final long serialVersionUID = 1L;

	
	public LoginException()
	{
		super("Errore nel login");
	}
	
	public LoginException(String error)
	{
		super(error);
	}
	
	public LoginException(HibernateException e)
	{
		super("Errore nel login. Causa: "+e);
	}
}
