package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import entity.Camera;
import entity.DisponibilitaCamera;
import exception.CameraException;


public class FactoryCamera 
{

	public void aggiornaCamera(Camera daAggiornare) throws CameraException 
	{
		try
		{
			Session s = GestoreRisorseJDBC.getSessione();
			s.beginTransaction();
			s.update(daAggiornare);
			s.getTransaction().commit();
			s.close();
		}
		catch(HibernateException e)
		{
			throw new CameraException("Errore nell'aggiornamento della camera");
		}
	}

	public void eliminaCamera(Camera daEliminare) throws CameraException 
	{
		try
		{
			Session s = GestoreRisorseJDBC.getSessione();
			s.beginTransaction();
			s.delete(daEliminare);
			s.getTransaction().commit();
			s.close();
		}
		catch(HibernateException e)
		{
			throw new CameraException("Errore nell'eliminazione della camera");
		}
	}
	
	public void eliminaDisponibilita(DisponibilitaCamera daEliminare) 
	{
		Session s = GestoreRisorseJDBC.getSessione();
		s.beginTransaction();
		s.delete(daEliminare);
		s.getTransaction().commit();
		s.close();
	}

		

}