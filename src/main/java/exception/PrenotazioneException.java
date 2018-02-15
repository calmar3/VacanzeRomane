package exception;

public class PrenotazioneException extends Exception
{

	private static final long serialVersionUID = 1L;

	public PrenotazioneException(String error)
	{
		super(error);
	}
}
