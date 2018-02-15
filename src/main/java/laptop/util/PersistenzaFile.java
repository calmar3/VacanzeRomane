package laptop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import laptop.exception.DeserializzazioneException;
import laptop.exception.SerializzazioneException;

public class PersistenzaFile 
{
	String percorsoFile;
	
	public PersistenzaFile(String percorso)
	{
		this.percorsoFile = percorso;
	}

	public Object deserializza() throws DeserializzazioneException, ClassNotFoundException
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(percorsoFile));
			ObjectInputStream ois = new ObjectInputStream(file);
			Object objUsername = (Object) ois.readObject();
			ois.close (); 
			return objUsername;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new DeserializzazioneException("Oggetto non caricabile!");
		}
	}
	
	public void serializza(Object obj) throws SerializzazioneException
	{
		try 
		{
			FileOutputStream   f = new FileOutputStream (new File(percorsoFile));
			ObjectOutputStream s = new ObjectOutputStream (f);
			s.writeObject(obj); 
			s.flush();
			s.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new SerializzazioneException
			("Errore file durante serializzazione. ");
		}
	}
	

}
