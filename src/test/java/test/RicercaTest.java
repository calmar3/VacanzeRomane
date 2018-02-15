package test;

import static org.junit.Assert.*;

import org.junit.Test;

import bean.CatalogoBean;
import bean.RicercaBean;
import bean.StrutturaBean;
import controller.GestoreRicerche;

public class RicercaTest 
{
	protected static RicercaBean rb; 
	protected static CatalogoBean cb = new CatalogoBean();
	
	@Test
	public void testCitta()
	{
		rb = new RicercaBean();
		rb.setLuogo("Roma");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==1 && numeroCamere == 3));
		
	}
	
	@Test
	public void testTipologiaStruttura()
	{
		rb = new RicercaBean();
		rb.setTipologiaStruttura("Hotel");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==1 && numeroCamere == 3));
	}
	
	@Test
	public void testDate()
	{
		rb = new RicercaBean();
		rb.setDataCheckIn("20/03/2016");
		rb.setDataCheckOut("23/03/2016");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==3 && numeroCamere == 6));
	}
	
	@Test
	public void testTipologiaCamera()
	{
		rb = new RicercaBean();
		rb.setTipologiaCamera("Camera Singola");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==3 && numeroCamere == 3));
	}
	
	@Test
	public void testPrezzoMinimo()
	{
		rb = new RicercaBean();
		rb.setPrezzoMin("55");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==3 && numeroCamere == 4));
	}
	
	@Test
	public void testPrezzoMassimo()
	{
		rb = new RicercaBean();
		rb.setPrezzoMax("50");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==3 && numeroCamere == 5));
	}
	
	@Test
	public void testPrezzoMinimoMassimo()
	{
		rb = new RicercaBean();
		rb.setPrezzoMin("40");
		rb.setPrezzoMax("60");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==3 && numeroCamere == 6));
	}
	
	@Test
	public void testNumeroPersone() 
	{
		rb = new RicercaBean();
		rb.setNumeroPersone("6");
		GestoreRicerche controllerRicerche = new GestoreRicerche();
		controllerRicerche.ricercaStrutture(cb, rb);
		int numeroCamere = verificaNumeroCamere(cb);
		assertTrue((cb.getStrutture().size()==2 && numeroCamere == 6));
	}
	
	public int verificaNumeroCamere(CatalogoBean cb)
	{
		int numeroCamere = 0;
		for (StrutturaBean sb : cb.getStrutture())
			numeroCamere += sb.getCamere().size();
		return numeroCamere;
	}

}
