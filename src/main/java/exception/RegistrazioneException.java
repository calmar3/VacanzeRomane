package exception;

public class RegistrazioneException extends UserException
{
	private static final long serialVersionUID = 1L;

	public RegistrazioneException()
	{
		super("Errore nella registrazione");
	}
	
}
