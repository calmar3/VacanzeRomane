package laptop.boundary;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import laptop.control.GestoreMonitoraggio;
import laptop.exception.FileNonTrovatoException;

public class InterfacciaVisualizzaLog
{

	public String[] logPresenti = {"HibernateQueryLog", "HibernateDebugLog", "HibernateInfoLog", "HibernateTraceLog"};
	
	public JPanel pannelloPrincipale = new JPanel();
	private JPanel pannelloLog = new JPanel();
	public JLabel titoloPrincipale = new JLabel();
	public JLabel titoloLog = new JLabel();
	
	private JTextArea areaLog;
	private JScrollPane scrollLog;
	public JButton viewLogButton = new JButton("Ok");
	public JButton returnButton = new JButton("Indietro");
	public JComboBox<String> logBox = new JComboBox<String>();

	private ViewLogListener viewLog = new ViewLogListener();
	
	public JLabel sfondoLog = new JLabel(new ImageIcon("src/main/java/laptop/immagini/background5.jpg"));

	private ReturnBackListener returnBack = new ReturnBackListener();

	
	public InterfacciaVisualizzaLog()
	{

	}
	
	public void inizializzaComboLog()
	{
		for(int i=0; i<logPresenti.length;i++)
		{
			logBox.addItem(logPresenti[i]);
		}
	}
	
	private class ViewLogListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String contenutoLog, percorsoFileLog;
			int returnValue;
			GestoreMonitoraggio controllerMonitoraggio = GestoreMonitoraggio.avviaControllerMonitoraggio();
			try
			{
				if(logBox.getSelectedItem().equals("HibernateQueryLog"))
				{
					percorsoFileLog = "src/log/hibernateQueryLog.log";
					returnValue = JOptionPane.showConfirmDialog(null, "Hai selezionato il file di log "+percorsoFileLog+" .Va bene? ");
					if(returnValue == JOptionPane.YES_OPTION)
						{
							titoloLog.setText("Log: "+logBox.getSelectedItem());
							contenutoLog = controllerMonitoraggio.caricaLog(percorsoFileLog);
							areaLog.setText(contenutoLog);
						}
				}
				if(logBox.getSelectedItem().equals("HibernateDebugLog"))
				{
					percorsoFileLog = "src/log/hibernateDebugLog.log";
					returnValue = JOptionPane.showConfirmDialog(null, "Hai selezionato il file di log "+percorsoFileLog+" .Va bene? ");
					if(returnValue == JOptionPane.YES_OPTION)
						{
							titoloLog.setText("Log: "+logBox.getSelectedItem());
							contenutoLog = controllerMonitoraggio.caricaLog(percorsoFileLog);
							areaLog.setText(contenutoLog);
						}
				}
				if(logBox.getSelectedItem().equals("HibernateInfoLog"))
				{
					percorsoFileLog = "src/log/hibernateInfoLog.log";
					returnValue = JOptionPane.showConfirmDialog(null, "Hai selezionato il file di log "+percorsoFileLog+" .Va bene? ");
					if(returnValue == JOptionPane.YES_OPTION)
						{
							titoloLog.setText("Log: "+logBox.getSelectedItem());
							contenutoLog = controllerMonitoraggio.caricaLog(percorsoFileLog);
							areaLog.setText(contenutoLog);
						}
				}
				if(logBox.getSelectedItem().equals("HibernateTraceLog"))
				{
					percorsoFileLog = "src/log/hibernateTraceLog.log";
					returnValue = JOptionPane.showConfirmDialog(null, "Hai selezionato il file di log '"+percorsoFileLog+"' .Va bene? ");
					if(returnValue == JOptionPane.YES_OPTION)
						{
							titoloLog.setText("Log: "+logBox.getSelectedItem());
							contenutoLog = controllerMonitoraggio.caricaLog(percorsoFileLog);
							areaLog.setText(contenutoLog);
							
						}
				}
			}
			catch(FileNonTrovatoException f)
			{
				JOptionPane.showMessageDialog(null, "File di log non presente", "File Log", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	
	
	private class ReturnBackListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			    InterfacciaAmministratore.getInterfacciaAdmin().disegnaAmministratore();

		}	
	}

	public void disegnaLog() {
		Interfaccia.pulisciInterfaccia();
		
		Interfaccia.getInterfaccia().setLayout(null);
		Interfaccia.getInterfaccia().setSize(950,700);
		Interfaccia.getInterfaccia().setLocation(215,10);
		Interfaccia.getInterfaccia().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Interfaccia.getInterfaccia().setResizable(false);
		Container c = Interfaccia.getInterfaccia().getContentPane();
		
		pannelloPrincipale.setLayout(null);
		pannelloPrincipale.setSize(Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		pannelloPrincipale.setLocation(0, 0);
		        
		titoloPrincipale.setFont(new Font("Calibri", Font.BOLD, 17));
		titoloPrincipale.setLocation(0, 25);
		titoloPrincipale.setSize(pannelloPrincipale.getWidth(), 25);
		titoloPrincipale.setHorizontalAlignment(JLabel.CENTER);
		titoloPrincipale.setVerticalAlignment(JLabel.CENTER);
		titoloPrincipale.setForeground(Color.white);
		titoloPrincipale.setText("SEZIONE DEI LOG");
		
		pannelloPrincipale.add(titoloPrincipale);
		
		
		// Log 
		pannelloLog.setLayout(null);
		pannelloLog.setSize(Interfaccia.getInterfaccia().getWidth(), 800);
		pannelloLog.setLocation(5, 120);
		pannelloLog.setOpaque(false);
		areaLog = new JTextArea(pannelloLog.getWidth()-100, 100);
		areaLog.setFont(new Font("Arial", 1, 12));
		areaLog.isOptimizedDrawingEnabled();
		areaLog.setEditable(false);
		areaLog.setLineWrap(true);
		scrollLog = new JScrollPane(areaLog);
		scrollLog.setBounds(40,50,pannelloLog.getWidth()-100,375);
		logBox.setSize(150,30);
		logBox.setLocation(370, 445);
		
		inizializzaComboLog();
		viewLogButton.setBorderPainted(false);
		viewLogButton.setMargin (new Insets (0, 0, 0, 0));
		viewLogButton.setContentAreaFilled(true);
		viewLogButton.setToolTipText("Aggiorna il log");
		viewLogButton.setBounds(400, 490, 75, 30);
		viewLogButton.addActionListener(viewLog);
		
		returnButton.setBorderPainted(false);
		returnButton.setMargin (new Insets (0, 0, 0, 0));
		returnButton.setContentAreaFilled(true);
		returnButton.setToolTipText("Aggiorna il log");
		returnButton.setBounds(400, 490, 75, 30);
		returnButton.addActionListener(returnBack );
		
		titoloLog.setFont(new Font("Calibri", Font.BOLD, 14));
		titoloLog.setLocation(0, 0);
		titoloLog.setSize(pannelloLog.getWidth(), 25);
		titoloLog.setHorizontalAlignment(JLabel.CENTER);
		titoloLog.setVerticalAlignment(JLabel.CENTER);
		titoloLog.setForeground(Color.white);
		
		pannelloLog.add(titoloLog);
		pannelloLog.add(viewLogButton);
		pannelloLog.add(logBox);
		pannelloLog.add(scrollLog);
		
		returnButton.setLocation(5, 5);
		pannelloPrincipale.add(pannelloLog);
		pannelloPrincipale.add(returnButton);
		pannelloPrincipale.add(sfondoLog);
		sfondoLog.setBounds(0, 0, Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		
		c.add(pannelloPrincipale);
		
		Interfaccia.getInterfaccia().setVisible(true);
		
	}
	
	
}
