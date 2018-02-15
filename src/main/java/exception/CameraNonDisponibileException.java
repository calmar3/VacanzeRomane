package exception;

public class CameraNonDisponibileException extends PrenotazioneException
{
	private static final long serialVersionUID = 1L;
	
	public CameraNonDisponibileException(String error)
	{
		super(error);
	}
	
	public CameraNonDisponibileException()
	{
		super("Camera non disponibile nel periodo scelto");
	}
}
