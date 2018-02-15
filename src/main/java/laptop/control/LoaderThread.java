package laptop.control;

public class LoaderThread extends Thread {

	public LoaderThread(){}
	
	@Override
	public void run()
	{
		synchronized(this){
			GestoreMonitoraggio controllerMonitoraggio = GestoreMonitoraggio.avviaControllerMonitoraggio();
			controllerMonitoraggio.utentiRegistrati();
			controllerMonitoraggio.strutturePresenti();
			controllerMonitoraggio.prenotazioniEffettuate();
			notify();
		}

	}
}
