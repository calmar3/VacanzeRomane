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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import entity.LineaPrenotazione;
import entity.Prenotazione;
import laptop.control.GestoreMonitoraggio;

public class InterfacciaVisualizzaPrenotazioni
{
	public JPanel pannelloPrincipale = new JPanel();
	public JPanel pannelloPrenotazioni = new JPanel();
	public JTable tabellaPrenotazioni;
	public JScrollPane scrollPrenotazioni;
	
	public JLabel titoloPrenotazioni = new JLabel();
	public JLabel sfondoPrenotazioni = new JLabel(new ImageIcon("src/main/java/laptop/immagini/background5.jpg"));
	
	public JButton returnButton = new JButton("Indietro");
	public ReturnBackListener returnBackListener = new ReturnBackListener();
	
	public InterfacciaVisualizzaPrenotazioni()
	{

	}

	public void disegnaPrenotazioni() 
	{
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
		

		pannelloPrenotazioni.setLayout(null);
		pannelloPrenotazioni.setSize(Interfaccia.getInterfaccia().getWidth()-100,Interfaccia.getInterfaccia().getHeight()-300);
		pannelloPrenotazioni.setLocation(50,110);  
		pannelloPrenotazioni.setOpaque(true);
		pannelloPrenotazioni.setBackground(new Color(255,255,255,55));
		
		titoloPrenotazioni.setFont(new Font("Calibri", Font.BOLD, 17));
		titoloPrenotazioni.setLocation(10, 15);
		titoloPrenotazioni.setSize(pannelloPrincipale.getWidth()-100, 25);
		titoloPrenotazioni.setHorizontalAlignment(JLabel.CENTER);
		titoloPrenotazioni.setVerticalAlignment(JLabel.CENTER);
		titoloPrenotazioni.setForeground(Color.white);
		titoloPrenotazioni.setText("Prenotazioni registrate nella piattaforma: ");
		
		inizializzaTabellaPrenotazioni();
		
		scrollPrenotazioni.setSize(pannelloPrenotazioni.getWidth(),pannelloPrenotazioni.getHeight()-40);
		scrollPrenotazioni.setLocation(0, 40);
		scrollPrenotazioni.setForeground(new Color(255,255,255,10));
		
		returnButton.setBorderPainted(false);
		returnButton.setMargin (new Insets (0, 0, 0, 0));
		returnButton.setContentAreaFilled(true);
		returnButton.setToolTipText("Indietro");
		returnButton.setBounds(400, 490, 75, 30);
		returnButton.addActionListener(returnBackListener);
		returnButton.setLocation(5, 5);
		
		pannelloPrenotazioni.add(titoloPrenotazioni);
		pannelloPrenotazioni.add(scrollPrenotazioni);
		pannelloPrincipale.add(returnButton);
		pannelloPrincipale.add(pannelloPrenotazioni);
		pannelloPrincipale.add(sfondoPrenotazioni);
		sfondoPrenotazioni.setBounds(0, 0, Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		
		c.add(pannelloPrincipale);
		Interfaccia.getInterfaccia().setVisible(true);
		
	}
	
	public void inizializzaTabellaPrenotazioni()
	{
		// Tabella per locazioni
		tabellaPrenotazioni = new JTable(0,0);
		DefaultTableModel modelTabellaPrenotazioni = (DefaultTableModel)tabellaPrenotazioni.getModel();
		scrollPrenotazioni = new JScrollPane(tabellaPrenotazioni);
		
		modelTabellaPrenotazioni.addColumn("Effettuata da");
		modelTabellaPrenotazioni.addColumn("Struttura Prenotata");
		modelTabellaPrenotazioni.addColumn("Data Partenza");
		modelTabellaPrenotazioni.addColumn("Data Arrivo");
		modelTabellaPrenotazioni.addColumn("Proprietario");
		String rowData = "";
		List<Prenotazione> prenotazioni = GestoreMonitoraggio.getPrenotazioni();
		for(int j=0;j<prenotazioni.size();j++)
		{
			List<LineaPrenotazione> linee = prenotazioni.get(j).getLinee();
			for(int k=0;k<linee.size();k++)
			{
				for(int i=0;i<modelTabellaPrenotazioni.getColumnCount();i++)
				{
					if(modelTabellaPrenotazioni.getColumnName(i).equals("Proprietario"))
					{
						rowData = prenotazioni.get(j).getAffittuario().getNome()+" "+prenotazioni.get(j).getAffittuario().getCognome();
						modelTabellaPrenotazioni.setValueAt(rowData, j, i);
					}
					if(modelTabellaPrenotazioni.getColumnName(i).equals("Struttura Prenotata"))
					{
						rowData = prenotazioni.get(j).getStrutturaPrenotata().getNome()+" , "+prenotazioni.get(j).getStrutturaPrenotata().getTipologia();
						modelTabellaPrenotazioni.insertRow(j, new Object[]{rowData});
					}
					if(modelTabellaPrenotazioni.getColumnName(i).equals("Data Partenza"))
					{
						rowData = (linee.get(k).getDataCheckIn()).toString();
						modelTabellaPrenotazioni.setValueAt(rowData, j, i);
					}
					if(modelTabellaPrenotazioni.getColumnName(i).equals("Data Arrivo"))
					{
						rowData = (linee.get(k).getDataCheckOut()).toString();
						modelTabellaPrenotazioni.setValueAt(rowData, j, i);
					}
					if(modelTabellaPrenotazioni.getColumnName(i).equals("Costo"))
					{
						rowData = (linee.get(k).getImporto()).toString();
						modelTabellaPrenotazioni.setValueAt(rowData, j, i);
					}
				}
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
}
	
		
