package exception;

import org.hibernate.HibernateException;

public class EliminaStrutturaException extends StrutturaException
{
	private static final long serialVersionUID = 1L;
	
	public EliminaStrutturaException(String error)
	{
		super(error);
	}
	
	public EliminaStrutturaException(String error, HibernateException e)
	{
		super("+++"+error+" Causa: "+e);
	}

}
