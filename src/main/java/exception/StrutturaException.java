package exception;

import org.hibernate.exception.ConstraintViolationException;

public class StrutturaException extends Exception
{

	private static final long serialVersionUID = 1L;

	
	public StrutturaException(String error)
	{
		super(error);
	}
	
	public StrutturaException(String error, ConstraintViolationException causa)
	{
		super("+++"+error+" Causa: "+causa);
	}
	
}
