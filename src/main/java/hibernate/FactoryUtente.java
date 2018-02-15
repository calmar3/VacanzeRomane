package hibernate;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import entity.Utente;
import exception.UserException;


public class FactoryUtente 
{

	public void salva(Utente u) throws UserException
	{
		try 
		{
			Session s = GestoreRisorseJDBC.getSessione();
			s.beginTransaction();
			s.saveOrUpdate(u);
			s.getTransaction().commit();
			s.close();
		}
		catch (ConstraintViolationException cve) 
		{
			throw new UserException("Utente già presente",cve);
		}
	}

	public void aggiornaUtente(Utente daAggiornare) throws UserException 
	{
		try 
		{
			Session s = GestoreRisorseJDBC.getSessione();
			s.beginTransaction();
			s.update(daAggiornare);
			s.getTransaction().commit();
			s.close();
		}
		catch (HibernateException e) 
		{
			throw new UserException("Errore nella modifice dell'account dell'Utente");
		}
	}

	public void eliminaUtente(Utente daEliminare) throws UserException 
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
			throw new UserException("Errore nell'eliminazione dell'account");
		}
	}
	

	public Utente autentica(String username, String password) throws UserException
	{
		try
		{
			Session session = GestoreRisorseJDBC.getSessione();
			session.beginTransaction();
	        Query query= session.createQuery("from Utente where username=:username");
	        query.setParameter("username", username);
	        Utente u = (Utente) query.uniqueResult();
	        session.getTransaction().commit();
	        session.close();
	        if (u!=null)
	        {	
	        	if (password.equals(u.getPassword()))
	        		return u;
	        }
	    	return null;
		}
		catch(HibernateException e)
		{
			throw new UserException(e);
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Utente> selezionaUtentiRegistrati() 
	{
		Session session = GestoreRisorseJDBC.getSessione();
		session.beginTransaction();
		Query query= session.createQuery("from Utente");
		List<Utente> listaUtenti =  query.list();
		session.getTransaction().commit();
		session.close();
		if (listaUtenti != null)
			return (List<Utente>) listaUtenti;
		else 
			throw new HibernateException("Nessun Utente");
	}

}
