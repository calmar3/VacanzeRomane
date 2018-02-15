package hibernate;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import entity.Struttura;
import exception.EliminaStrutturaException;
import exception.ModificaStrutturaException;
import exception.StrutturaException;

public class FactoryStruttura 
{

	public void salva(Struttura struttura) throws StrutturaException 
	{
		try
		{
			Session s = GestoreRisorseJDBC.getSessione();
			s.beginTransaction();
			s.saveOrUpdate(struttura);
			s.getTransaction().commit();
			s.close();
		}
		catch(ConstraintViolationException cve)
		{
			throw new StrutturaException("Struttura già presente");
		}
	}

	public void aggiornaStruttura(Struttura daAggiornare) throws ModificaStrutturaException 
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
			throw new ModificaStrutturaException("Errore nella modifica della struttura");
		}
	}

	public void eliminaStruttura(Struttura daEliminare) throws EliminaStrutturaException 
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
			throw new EliminaStrutturaException("Problema nell'eliminazione della struttura");
		}
	}
	

	@SuppressWarnings("unchecked")
	public ArrayList<Struttura> selezionaTutto() 
	{
		try
		{
			Session session = GestoreRisorseJDBC.getSessione();
			session.beginTransaction();
			Query query= session.createQuery("from Struttura");
			List<Struttura> listaStrutture=  query.list();
			session.getTransaction().commit();
			session.close();
			if (listaStrutture!=null)
				return (ArrayList<Struttura>) listaStrutture;
			return null;
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
}
