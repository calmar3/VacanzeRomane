package hibernate;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import entity.Camera;
import entity.LineaPrenotazione;
import entity.Prenotazione;
import entity.Struttura;
import exception.LineaPrenotazioneException;
import exception.PrenotazioneException;


public class FactoryPrenotazione 
{

		public void salva(Prenotazione p) throws PrenotazioneException 
		{
			try
			{
				Session s = GestoreRisorseJDBC.getSessione();
				s.beginTransaction();
				s.saveOrUpdate(p);
				s.getTransaction().commit();
				s.close();
			}
			catch(HibernateException e)
			{
				throw new PrenotazioneException("Errore nella conferma della prenotazione. Riprova");
			}
		}

		public void aggiornaPrenotazione(Prenotazione daAggiornare) throws PrenotazioneException 
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
				throw new PrenotazioneException("Errore nell'aggiornamento della prenotazione");
			}
		}

		public void eliminaPrenotazione(Prenotazione daEliminare) throws PrenotazioneException 
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
				throw new PrenotazioneException("Errore nell'eliminazione della prenotazione");
			}
		}
		


		@SuppressWarnings("unchecked")
		public  List<String> ottieniCodici() 
		{ 
			try
			{
				Session session = GestoreRisorseJDBC.getSessione();
				session.beginTransaction();
				Criteria criteria  = session.createCriteria(Prenotazione.class);
				criteria.setProjection(Projections.property("codicePin"));
				List<String> listaCodici=  criteria.list();
				session.getTransaction().commit();
				session.close();
				if (listaCodici!=null)
					return listaCodici;
				return null;
			}
			catch(HibernateException e)
			{
				e.printStackTrace();
				return null;
			}
		}


		public void eliminaLineaPrenotazione(LineaPrenotazione daEliminare) throws LineaPrenotazioneException 
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
				throw new LineaPrenotazioneException("Errore nell'eliminazione della linea prenotazione");
			}
		}


		@SuppressWarnings("unchecked")
		public List<LineaPrenotazione> prendiLineeCamera(Camera c) 
		{
			try
			{
				Session session = GestoreRisorseJDBC.getSessione();
				session.beginTransaction();
				Query query= session.createQuery("from LineaPrenotazione where cameraLP_id=:cameraLP_id");
				query.setParameter("cameraLP_id",c);
				List<LineaPrenotazione> lineeprenotazioni = query.list();
				session.getTransaction().commit();
				session.close();
				if (lineeprenotazioni!=null)
					return lineeprenotazioni;
				return null;
			}
			catch(HibernateException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public List<Prenotazione> selezionePrenotazioniStruttura(Struttura s)
		{
			try
			{
				Session session = GestoreRisorseJDBC.getSessione();
				session.beginTransaction();
				Query query= session.createQuery("from Prenotazione where id_struttura=:id_struttura");
				query.setParameter("id_struttura",s);
				List<Prenotazione> prenotazioni = query.list();
				session.getTransaction().commit();
				session.close();
				if (prenotazioni!=null)
					return prenotazioni;
				return null;
			}
			catch(HibernateException e)
			{
				e.printStackTrace();
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public List<Prenotazione> selezionaTuttePrenotazioni() {
			try
			{
				Session session = GestoreRisorseJDBC.getSessione();
				session.beginTransaction();
				Query query= session.createQuery("from Prenotazione");
				List<Prenotazione> prenotazioni = query.list();
				session.getTransaction().commit();
				session.close();
				if (prenotazioni!=null)
					return prenotazioni;
				return null;
			}
			catch(HibernateException e)
			{
				e.printStackTrace();
				return null;
			}
		}

	}

