package laptop.boundary;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import entity.Utente;
import laptop.control.GestoreMonitoraggio;
import laptop.entity.Amministratore;
import laptop.start.Avvio;

public class InterfacciaAmministratore 
{
	
	public JPanel pannelloAmministratore = new JPanel();
	public JPanel pannelloStatistiche = new JPanel();
	public JPanel pannelloLateraleBottoni = new JPanel();
	
	public JLabel titoloAmministratore = new JLabel();
	public JLabel areaStatisticheL = new JLabel();
	public JLabel numeroRegistratiL = new JLabel();
	public JLabel sfondoAmministratore = new JLabel(new ImageIcon("src/main/java/laptop/immagini/background5.jpg"));
	
	// Tabella per utenti
	public JTable tabellaUtenti;
	public JScrollPane scrollUtenti;
		
	// Bottoni
	public JButton visualizzaPrenotazioniButton = new JButton("Visualizza Prenotazioni");
	public JButton visualizzaLocazioniButton = new JButton("Visualizza Locazioni");
	public JButton logButton = new JButton("Visualizza Log");
	public JButton logoutButton = new JButton("Logout");
	//public JButton eliminaAccountButton = new JButton("X");
	GestoreMonitoraggio controllerMonitoraggio = GestoreMonitoraggio.avviaControllerMonitoraggio();
	
	// Ascoltatori
	private LogoutListener ascoltatoreLogout = new LogoutListener();
	private LogListener ascoltatoreLog = new LogListener();
	private PrenotazioniListener ascoltatorePrenotazioni = new PrenotazioniListener();
	private LocazioniListener ascoltatoreLocazioni = new LocazioniListener();
	private Amministratore admin;
	private static InterfacciaAmministratore iAdmin;

	
	
	private InterfacciaAmministratore()
	{
	}
	
	public synchronized static InterfacciaAmministratore getInterfacciaAdmin()
	{
		if(iAdmin == null)
			iAdmin = new InterfacciaAmministratore();
		return iAdmin;
	}
	
	
	
	public void inizializzaTabellaUtenti()
	{	
		// Tabella per utenti
		tabellaUtenti = new JTable(0,0);
		DefaultTableModel modelTabellaUtenti = (DefaultTableModel)tabellaUtenti.getModel();
		scrollUtenti = new JScrollPane(tabellaUtenti);
		modelTabellaUtenti.addColumn("Nome");
		modelTabellaUtenti.addColumn("Cognome");
		modelTabellaUtenti.addColumn("Username");
		modelTabellaUtenti.addColumn("Mail");
		modelTabellaUtenti.addColumn("Operazioni");
		String rowData = "";
		List<Utente> utenti = GestoreMonitoraggio.getUtenti();
		for(int j=0;j<utenti.size();j++)
		{			
			for(int i=0;i<modelTabellaUtenti.getColumnCount();i++)
				{
					if(modelTabellaUtenti.getColumnName(i).equals("Nome"))
					{
						rowData = utenti.get(j).getNome();
						modelTabellaUtenti.insertRow(j, new Object[]{rowData});
					}
					if(modelTabellaUtenti.getColumnName(i).equals("Cognome"))
					{
						rowData = utenti.get(j).getCognome();
						modelTabellaUtenti.setValueAt(rowData,j,i);
					}
					if(modelTabellaUtenti.getColumnName(i).equals("Username"))
					{
						rowData = utenti.get(j).getUsername();
						modelTabellaUtenti.setValueAt(rowData,j,i);
					}
					if(modelTabellaUtenti.getColumnName(i).equals("Mail"))
					{
						rowData = utenti.get(j).getMail();
						modelTabellaUtenti.setValueAt(rowData,j,i);
					}
				}
		}
	}
	
	private class LogoutListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			int logout = JOptionPane.showConfirmDialog(null,"Sei sicuro di voler effettuare il logout?");
			if(logout == JOptionPane.YES_OPTION)
				{

						Interfaccia.pulisciInterfaccia();
					    InterfacciaLogin iLogin = new InterfacciaLogin();
					    iLogin.disegnaLogin();
				}
		}
	}
	
	private class LogListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				InterfacciaVisualizzaLog iLog = new InterfacciaVisualizzaLog();
				iLog.disegnaLog();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private class PrenotazioniListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				InterfacciaVisualizzaPrenotazioni iPrenotazioni= new InterfacciaVisualizzaPrenotazioni();
				iPrenotazioni.disegnaPrenotazioni();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private class LocazioniListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				InterfacciaVisualizzaLocazioni iLocazioni = new InterfacciaVisualizzaLocazioni();
				iLocazioni.disegnaLocazioni();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void disegnaAmministratore() {
		Interfaccia.pulisciInterfaccia();
		Interfaccia.getInterfaccia().setSize(950,700);
		Interfaccia.getInterfaccia().setLocation(215,10);
		Interfaccia.getInterfaccia().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Interfaccia.getInterfaccia().setResizable(false);
		Container c = Interfaccia.getInterfaccia().getContentPane();  
		
		pannelloAmministratore.setLayout(null);
		pannelloAmministratore.setSize(Interfaccia.getInterfaccia().getWidth(),Interfaccia.getInterfaccia().getHeight());
		pannelloAmministratore.setLocation(0, 0);
		
		        
		titoloAmministratore.setFont(new Font("Calibri", Font.BOLD, 17));
		titoloAmministratore.setLocation(10, 15);
		titoloAmministratore.setSize(pannelloAmministratore.getWidth(), 25);
		titoloAmministratore.setHorizontalAlignment(JLabel.LEFT);
		titoloAmministratore.setVerticalAlignment(JLabel.CENTER);
		titoloAmministratore.setBackground(Color.white);
		titoloAmministratore.setText("Benvenuto "+getAdmin().getUsername());
		
		pannelloAmministratore.add(titoloAmministratore);
		
		pannelloLateraleBottoni.setLayout(null);
		pannelloLateraleBottoni.setSize(200,Interfaccia.getInterfaccia().getHeight()-200);
		pannelloLateraleBottoni.setLocation(5,110);  
		pannelloLateraleBottoni.setOpaque(true);
		pannelloLateraleBottoni.setBackground(new Color(255,255,255,55));
		
		visualizzaPrenotazioniButton.setBorderPainted(false);
		visualizzaPrenotazioniButton.setMargin (new Insets (0, 0, 0, 0));
		visualizzaPrenotazioniButton.setContentAreaFilled(true);
		visualizzaPrenotazioniButton.setToolTipText("Visualizza le prenotazioni nella piattaforma");
		visualizzaPrenotazioniButton.setBounds(10, 60, 170, 30);
        visualizzaPrenotazioniButton.addActionListener(ascoltatorePrenotazioni);
        
        visualizzaLocazioniButton.setBorderPainted(false);
		visualizzaLocazioniButton.setMargin (new Insets (0, 0, 0, 0));
		visualizzaLocazioniButton.setContentAreaFilled(true);
		visualizzaLocazioniButton.setToolTipText("Visualizza le locazioni nella piattaforma");
		visualizzaLocazioniButton.setBounds(10, 120, 170, 30);
        visualizzaLocazioniButton.addActionListener(ascoltatoreLocazioni);
		
		logButton.setBorderPainted(false);
		logButton.setMargin (new Insets (0, 0, 0, 0));
		logButton.setContentAreaFilled(true);
		logButton.setToolTipText("Visualizza il log");
		logButton.setBounds(10, 180, 170, 30);
        logButton.addActionListener(ascoltatoreLog);
		
		logoutButton.setBorderPainted(false);
		logoutButton.setMargin (new Insets (0, 0, 0, 0));
		logoutButton.setContentAreaFilled(true);
		logoutButton.setBounds(10, 240, 100, 30);
        logoutButton.addActionListener(ascoltatoreLogout);
		
		pannelloLateraleBottoni.add(visualizzaPrenotazioniButton);
		pannelloLateraleBottoni.add(visualizzaLocazioniButton);
		pannelloLateraleBottoni.add(logButton);
		pannelloLateraleBottoni.add(logoutButton);
		pannelloAmministratore.add(pannelloLateraleBottoni);

		// Area statistiche
		
		areaStatisticheL.setFont(new Font("Calibri",Font.ITALIC,17));
		areaStatisticheL.setLocation(525,75);
		areaStatisticheL.setSize(200,20);
		areaStatisticheL.setText("Statistiche Del Sistema: ");
		
		numeroRegistratiL.setFont(new Font("Calibri",Font.ITALIC,15));
		numeroRegistratiL.setLocation(100,10);
		numeroRegistratiL.setSize(300,15);
		numeroRegistratiL.setBackground(Color.white);
		
		
	    synchronized(Avvio.getLd()){
	        	if (Avvio.getLd().isAlive()){
	            try{
	                Avvio.getLd().wait();
	            }catch(InterruptedException e){
	                e.printStackTrace();
	            }
	        }
		}
		numeroRegistratiL.setText("Numero di utenti registrati nel sistema: "+GestoreMonitoraggio.getUtenti().size());

		inizializzaTabellaUtenti();
		
		scrollUtenti.setLocation(5, 40);
		scrollUtenti.setSize(675,100);
		
		
		pannelloStatistiche.add(scrollUtenti);
		pannelloStatistiche.setLayout(null);
		pannelloStatistiche.setSize(675,Interfaccia.getInterfaccia().getHeight()-200);
		pannelloStatistiche.setLocation(250,110);  
		pannelloStatistiche.setOpaque(true);
		pannelloStatistiche.setBackground(new Color(255,255,255,55));
		
		pannelloStatistiche.add(numeroRegistratiL);
		
		pannelloAmministratore.add(areaStatisticheL);
		pannelloAmministratore.add(pannelloStatistiche);
		pannelloAmministratore.add(sfondoAmministratore);
		sfondoAmministratore.setBounds(0, 0, Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		c.add(pannelloAmministratore);
		Interfaccia.getInterfaccia().setVisible(true);
		
	}

	/**
	 * @return the admin
	 */
	public Amministratore getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Amministratore admin) {
		this.admin = admin;
	}

}
