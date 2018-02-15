
package laptop.boundary;

// Importa Java Swing/Awt
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;

import controller.GestoreUtente;
import laptop.entity.Amministratore;
import laptop.exception.DeserializzazioneException;
import laptop.exception.PasswordException;
import laptop.exception.SerializzazioneException;
import laptop.exception.UtenteException;

public class InterfacciaLogin
{
	
	
	public JPanel pannelloPrincipale = new JPanel();	
	public JPanel pannelloSuperiore = new JPanel();
	public JPanel pannelloLogin = new JPanel();		
	public JLabel titoloPrincipale = new JLabel();
	public JLabel titoloLogin = new JLabel();
	public JLabel usernameUtenteL = new JLabel();
	public JLabel passwordUtenteL = new JLabel();
	public JLabel sfondo = new JLabel(new ImageIcon("src/main/java/laptop/immagini/background5.jpg"));
	public JTextField usernameUtente = new JTextField("", 20);
	public JPasswordField passwordUtente = new JPasswordField("",8);
	private JButton loginButton = new JButton("Accedi");
	private LoginListener ascoltatoreLogin = new LoginListener();
			

	public InterfacciaLogin()
	{}
	
	public void disegnaLogin()
	{
		Interfaccia.getInterfaccia().setSize(950,700);
		Interfaccia.getInterfaccia().setLocation(215,10);
		Interfaccia.getInterfaccia().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Interfaccia.getInterfaccia().setResizable(false);
		
		Container c = Interfaccia.getInterfaccia().getContentPane();  
		pannelloPrincipale.setLayout(null);
		pannelloPrincipale.setSize(Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		pannelloPrincipale.setLocation(0, 0);
		
		             
		pannelloSuperiore.setLayout(null);
		pannelloSuperiore.setSize(Interfaccia.getInterfaccia().getWidth(),100);
		pannelloSuperiore.setLocation(5,5);  
		pannelloSuperiore.setOpaque(false);
		        
		titoloPrincipale.setFont(new Font("Calibri", Font.ITALIC, 20));
		titoloPrincipale.setLocation(5, 35);
		titoloPrincipale.setSize(pannelloSuperiore.getWidth(), 25);
		titoloPrincipale.setHorizontalAlignment(JLabel.CENTER);
		titoloPrincipale.setVerticalAlignment(JLabel.CENTER);
		titoloPrincipale.setText("Benvenuto sulla piattaforma amministrativa di Vacanze Romane!");
		titoloPrincipale.setForeground(Color.white);
		
		pannelloSuperiore.add(titoloPrincipale);    
		   
		// Campi per il Login
		pannelloLogin.setLayout(null);
		pannelloLogin.setSize(Interfaccia.getInterfaccia().getWidth()-100,Interfaccia.getInterfaccia().getHeight()-300);
		pannelloLogin.setLocation(30, 100);
		pannelloLogin.setOpaque(false);

		titoloLogin.setFont(new Font("Calibri",0,18));
		titoloLogin.setLocation(25, 100);
		titoloLogin.setSize(pannelloLogin.getWidth(),25);
		titoloLogin.setHorizontalAlignment(JLabel.CENTER);
		titoloLogin.setVerticalAlignment(JLabel.CENTER);
		titoloLogin.setText("Inserisci le tue credenziali per accedere");
		titoloLogin.setForeground(Color.white);
		
		// Label
		usernameUtenteL.setFont(new Font("Calibri",Font.BOLD,17));
		usernameUtenteL.setLocation(150,150);
		usernameUtenteL.setSize(100,50);
		usernameUtenteL.setText("Username: ");
		usernameUtenteL.setForeground(Color.white);
		
		passwordUtenteL.setFont(new Font("Calibri",Font.BOLD,17));
		passwordUtenteL.setLocation(150, 200);
		passwordUtenteL.setSize(100,50);
		passwordUtenteL.setText("Password: ");
		passwordUtenteL.setForeground(Color.white);
		
		// Campi 
		usernameUtente.setFont(new Font("Calibri",0,20));
		usernameUtente.setLocation(250, 159);
		usernameUtente.setSize(400, 26);
		
		passwordUtente.setFont(new Font("Calibri",0,20));
		passwordUtente.setLocation(250, 209);
		passwordUtente.setSize(400,26);
		
		// Bottone login
		loginButton.setBorderPainted(false);
		loginButton.setMargin (new Insets (0, 0, 0, 0));
		loginButton.setContentAreaFilled(false);
		loginButton.setOpaque(false);
		loginButton.setToolTipText("Accedi all'area amministrativa");
		loginButton.setForeground(Color.white);

        loginButton.setBounds(415, 260, 50, 30);
        loginButton.addActionListener(ascoltatoreLogin);
		
		pannelloLogin.add(titoloLogin);
		pannelloLogin.add(usernameUtenteL);
		pannelloLogin.add(passwordUtenteL);
	    pannelloLogin.add(usernameUtente);
	    pannelloLogin.add(passwordUtente);
	    pannelloLogin.add(loginButton);
	    
	    
		
		pannelloPrincipale.add(pannelloSuperiore);
		pannelloPrincipale.add(pannelloLogin);
		pannelloPrincipale.add(sfondo);
		
		c.add(pannelloPrincipale);
		
		sfondo.setBounds(0, 0, Interfaccia.getInterfaccia().getWidth(), Interfaccia.getInterfaccia().getHeight());
		Interfaccia.getInterfaccia().setVisible(true);	
	}
	
	private class LoginListener implements ActionListener
	{
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				GestoreUtente controllerUtente = GestoreUtente.avviaControllerUtente();
				Amministratore admin = controllerUtente.convalidaLogin(usernameUtente.getText(),passwordUtente.getText());
				InterfacciaAmministratore iAdmin = InterfacciaAmministratore.getInterfacciaAdmin();
				iAdmin.setAdmin(admin);
				iAdmin.disegnaAmministratore();
			}
			catch(PasswordException e)
			{
				JOptionPane.showMessageDialog(null, " Password Errata. Riprova", "Password", JOptionPane.ERROR_MESSAGE);
			}
			catch (UtenteException e)
			{
				JOptionPane.showMessageDialog(null,"Utente inesistente. Riprova", "Utente", JOptionPane.ERROR_MESSAGE);
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (DeserializzazioneException e) 
			{
				e.printStackTrace();
			} 
			catch (SerializzazioneException e) 
			{
				e.printStackTrace();
			} 
			catch (InstantiationException e) 
			{
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			} 
			catch (UnsupportedLookAndFeelException e) 
			{
				e.printStackTrace();
			}
		}
	}
}