package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class GestoreRisorseJDBC {

	private static Configuration configurazione;
	private static ServiceRegistry registroServizio;
	private static SessionFactory sessioneFactory;

	private static void initHibernate() {
		// load hibernate configuration
		configurazione = new Configuration();
		configurazione.configure();

		// use JNDI to bind Hibernate configuration and datasource
		registroServizio = new StandardServiceRegistryBuilder().applySettings(
				configurazione.getProperties()).build();

		/*
		 * Retrieve the one session factory that will manage sessions,
		 * connections and transaction
		 */
		sessioneFactory = configurazione.buildSessionFactory(registroServizio);
	}

	public static Session getSessione() {
		if (sessioneFactory == null)
			initHibernate();
		return sessioneFactory.openSession();
	}

}