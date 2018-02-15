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

import entity.Struttura;
import laptop.control.GestoreMonitoraggio;

public class InterfacciaVisualizzaLocazioni 
{
	public JPanel pannelloPrincipale = new JPanel();
	public JPanel pannelloLocazioni = new JPanel();
	public JLabel titoloLocazioni = new JLabel();
	public JTable tabellaLocazioni;
	JScrollPane scrollLocazioni;

	public JLabel sfondoLocazioni = new JLabel(new ImageIcon("src/main/java/laptop/immagini/background5.jpg"));
	GestoreMonitoraggio controllerMonitoraggio = GestoreMonitoraggio.avviaControllerMonitoraggio();
	
	public JButton returnButton = new JButton("Indietro");
	private ReturnBackListener returnBackListener = new ReturnBackListener();
	
	public InterfacciaVisualizzaLocazioni()
	{

	}
	
	
	private class ReturnBackListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			    InterfacciaAmministratore.getInterfacciaAdmin().disegnaAmministratore();

		}	
	}
	
	public void inizializzaTabellaLocazioni()
	{
		// Tabella per locazioni
		tabellaLocazioni = new JTable(0,0);
		DefaultTableModel modelTabellaLocazioni = (DefaultTableModel)tabellaLocazioni.getModel();
		scrollLocazioni = new JScrollPane(tabellaLocazioni);
		
		modelTabellaLocazioni.addColumn("Nome");
		modelTabellaLocazioni.addColumn("Tipologia");
		modelTabellaLocazioni.addColumn("Nazione");
		modelTabellaLocazioni.addColumn("Citta");
		modelTabellaLocazioni.addColumn("Proprietario");
		String rowData = "";
		List<Struttura> strutture = GestoreMonitoraggio.getStrutture();
		for(int j=0;j<strutture.size();j++)
		{
			for(int i=0;i<modelTabellaLocazioni.getColumnCount();i++)
			{
				if(modelTabellaLocazioni.getColumnName(i).equals("Nome"))
				{
					rowData = strutture.get(j).getNome();
					modelTabellaLocazioni.insertRow(j, new Object[]{rowData});
				}
				if(modelTabellaLocazioni.getColumnName(i).equals("Tipologia"))
				{
					rowData = strutture.get(j).getTipologia();
					modelTabellaLocazioni.setValueAt(rowData, j, i);
				}
				if(modelTabellaLocazioni.getColumnName(i).equals("Nazione"))
				{
					rowData = strutture.get(j).getNazione();
					modelTabellaLocazioni.setValueAt(rowData, j, i);
				}
				if(modelTabellaLocazioni.getColumnName(i).equals("Citta"))
				{
					rowData = strutture.get(j).getCitta();
					modelTabellaLocazioni.setValueAt(rowData, j, i);
				}
				if(modelTabellaLocazioni.getColumnName(i).equals("Proprietario"))
				{
					rowData = strutture.get(j).getGestore().getNome()+" "+strutture.get(j).getGestore().getCognome();
					modelTabellaLocazioni.setValueAt(rowData, j, i);
				}
			}
		}
	}

	public void disegnaLocazioni() {
		Interfaccia.pulisciInterfaccia();
		
		Interfaccia.getInterfaccia().setLayout(null);
		Interfaccia.getInterfaccia().setSize(950,700);
		Interfaccia.getInterfaccia().setLocation(215,10);
		Interfaccia.getInterfaccia().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Interfaccia.getInterfaccia().setResizable(false);
		
		Container c = Interfaccia.getInterfaccia().getContentPane();
		
		inizializzaTabellaLocazioni();
		
		pannelloPrincipale.setLayout(null);
		pannelloPrincipale.setSize(Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		pannelloPrincipale.setLocation(0, 0);
		
		
		pannelloLocazioni.setLayout(null);
		pannelloLocazioni.setSize(Interfaccia.getInterfaccia().getWidth()-100,Interfaccia.getInterfaccia().getHeight()-300);
		pannelloLocazioni.setLocation(50,110);  
		pannelloLocazioni.setOpaque(true);
		pannelloLocazioni.setBackground(new Color(255,255,255,55));
		
		titoloLocazioni.setFont(new Font("Calibri", Font.BOLD, 17));
		titoloLocazioni.setLocation(10, 15);
		titoloLocazioni.setSize(pannelloLocazioni.getWidth(), 25);
		titoloLocazioni.setHorizontalAlignment(JLabel.CENTER);
		titoloLocazioni.setVerticalAlignment(JLabel.CENTER);
		titoloLocazioni.setForeground(Color.white);
		titoloLocazioni.setText("Locazioni presenti nella piattaforma: ");
		
		scrollLocazioni.setSize(pannelloLocazioni.getWidth(),pannelloLocazioni.getHeight()-40);
		scrollLocazioni.setLocation(0, 40);
		scrollLocazioni.setForeground(new Color(255,255,255,10));
		
		returnButton.setBorderPainted(false);
		returnButton.setMargin (new Insets (0, 0, 0, 0));
		returnButton.setContentAreaFilled(true);
		returnButton.setToolTipText("Aggiorna il log");
		returnButton.setBounds(400, 490, 75, 30);
		returnButton.addActionListener(returnBackListener);
		returnButton.setLocation(5, 5);
	
		pannelloLocazioni.add(titoloLocazioni);
		pannelloLocazioni.add(scrollLocazioni);
		pannelloPrincipale.add(returnButton);
		pannelloPrincipale.add(pannelloLocazioni);
		pannelloPrincipale.add(sfondoLocazioni);
		sfondoLocazioni.setBounds(0, 0, Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		
		c.add(pannelloPrincipale);
		
		Interfaccia.getInterfaccia().setVisible(true);
		
	}
}
