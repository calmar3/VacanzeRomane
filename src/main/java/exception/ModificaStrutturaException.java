package exception;

import org.hibernate.HibernateException;

public class ModificaStrutturaException extends StrutturaException
{

	private static final long serialVersionUID = 1L;
	
	public ModificaStrutturaException(String error)
	{
		super(error);
	}

	public ModificaStrutturaException(String error, HibernateException e)
	{
		super("+++"+error+" Causa: "+e);
	}
}
