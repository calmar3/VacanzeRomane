package exception;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

public class UserException extends Exception
{

	private static final long serialVersionUID = 1L;
	
	public UserException(HibernateException e)
	{
		super("Error :"+e);
	}
	
	public UserException(String error)
	{
		super(error);
	}
	
	public UserException(String error,ConstraintViolationException causa)
	{
		super("+++"+error+" Causa: "+causa);
	}
	
}
