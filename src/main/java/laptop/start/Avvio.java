

package laptop.start;

import laptop.boundary.InterfacciaLogin;
import laptop.control.LoaderThread;

public abstract class Avvio 
{

	private static LoaderThread ld = new LoaderThread();
	
	public static void main(String[] args)
	{
		getLd().start();
		InterfacciaLogin iLogin= new InterfacciaLogin();
		iLogin.disegnaLogin();
	}

	/**
	 * @return the ld
	 */
	public static LoaderThread getLd() {
		return ld;
	}

	/**
	 * @param ld the ld to set
	 */
	public static void setLd(LoaderThread ld) {
		Avvio.ld = ld;
	}


}
