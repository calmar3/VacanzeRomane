package laptop.boundary;

import javax.swing.JFrame;


public class Interfaccia extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static Interfaccia interfaccia;

	private Interfaccia(String titolo)
	{
		super(titolo);
	}
	
	public static void pulisciInterfaccia()
	{
		interfaccia.getContentPane().removeAll();
		interfaccia.getContentPane().validate();
		interfaccia.getContentPane().repaint();
	}

	/**
	 * @return the interfaccia
	 */
	public static Interfaccia getInterfaccia() {
		if (interfaccia == null)
			interfaccia = new Interfaccia ("Amministrazione - VacanzeRomane");
		return interfaccia;
	}

	/**
	 * @param interfaccia the interfaccia to set
	 */
	public static void setInterfaccia(Interfaccia interfaccia) {
		Interfaccia.interfaccia = interfaccia;
	}

}
