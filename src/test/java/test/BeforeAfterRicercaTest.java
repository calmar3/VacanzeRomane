package test;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import entity.Camera;
import entity.CatalogoStrutture;
import entity.DisponibilitaCamera;
import entity.Struttura;

public class BeforeAfterRicercaTest extends RicercaTest
{
	@BeforeClass
	public static void configuraAmbienteRicerca() 
	{
		List<Struttura> strutture = new ArrayList<Struttura>();
		Struttura s1 = new Struttura("Hotel", "1", "x", "x", "Italia", "Roma");
		Struttura s2 = new Struttura("B&B", "2", "x", "x", "Italia", "Napoli");
		Struttura s3 = new Struttura("Agriturismo", "3", "x", "x", "Italia", "Milano");
		
		//Camera 11
		Camera c11 = new Camera(s1, "Camera Singola", "1_1", "x", "40");
		DisponibilitaCamera dc11_1 = new DisponibilitaCamera("17/03/2016","20/03/2016");
		DisponibilitaCamera dc11_2 = new DisponibilitaCamera("23/03/2016", "25/03/2016");
		Set<DisponibilitaCamera> dc11 = new TreeSet<DisponibilitaCamera>();
		dc11.add(dc11_1);
		dc11.add(dc11_2);
		c11.setDisponibilita(dc11);
		
		//Camera 12
		Camera c12 = new Camera(s1, "Camera Matrimoniale", "1_2", "x", "60");
		DisponibilitaCamera dc12_1 = new DisponibilitaCamera("20/03/2016","23/03/2016");
		Set<DisponibilitaCamera> dc12 = new TreeSet<DisponibilitaCamera>();
		dc12.add(dc12_1);
		c12.setDisponibilita(dc12);
		
		//Camera 13
		Camera c13 = new Camera(s1, "Camera Matrimoniale", "1_3", "x", "80");
		DisponibilitaCamera dc13_1 = new DisponibilitaCamera("19/03/2016","24/03/2016");
		Set<DisponibilitaCamera> dc13 = new TreeSet<DisponibilitaCamera>();
		dc13.add(dc13_1);
		c13.setDisponibilita(dc13);
		
		//Camera 21
		Camera c21 = new Camera(s2, "Camera Singola", "2_1", "x", "25");
		DisponibilitaCamera dc21_1 = new DisponibilitaCamera("15/03/2016","20/03/2016");
		Set<DisponibilitaCamera> dc21 = new TreeSet<DisponibilitaCamera>();
		dc21.add(dc21_1);
		c21.setDisponibilita(dc21);
		
		//Camera 22
		Camera c22 = new Camera(s2, "Camera Matrimoniale", "2_2", "x", "40");
		DisponibilitaCamera dc22_1 = new DisponibilitaCamera("23/03/2016","29/03/2016");
		Set<DisponibilitaCamera> dc22 = new TreeSet<DisponibilitaCamera>();
		dc22.add(dc22_1);
		c22.setDisponibilita(dc22);
		
		//Camera 23
		Camera c23 = new Camera(s2, "Camera Tripla", "2_3", "x", "55");
		DisponibilitaCamera dc23_1 = new DisponibilitaCamera("19/03/2016","21/03/2016");
		Set<DisponibilitaCamera> dc23 = new TreeSet<DisponibilitaCamera>();
		dc23.add(dc23_1);
		c23.setDisponibilita(dc23);
		
		//Camera 31
		Camera c31 = new Camera(s3, "Camera Singola", "3_1", "x", "30");
		DisponibilitaCamera dc31_1 = new DisponibilitaCamera("24/03/2016","26/03/2016");
		Set<DisponibilitaCamera> dc31 = new TreeSet<DisponibilitaCamera>();
		dc31.add(dc31_1);
		c31.setDisponibilita(dc31);
		
		//Camera 32
		Camera c32 = new Camera(s3, "Camera Tripla", "3_2", "x", "45");
		DisponibilitaCamera dc32_1 = new DisponibilitaCamera("25/03/2016","26/03/2016");
		Set<DisponibilitaCamera> dc32 = new TreeSet<DisponibilitaCamera>();
		dc32.add(dc32_1);
		c32.setDisponibilita(dc32);
		
		//Camera 33
		Camera c33 = new Camera(s3, "Camera Tripla", "3_3", "x", "60");
		DisponibilitaCamera dc33_1 = new DisponibilitaCamera("14/03/2016","17/03/2016");
		Set<DisponibilitaCamera> dc33 = new TreeSet<DisponibilitaCamera>();
		dc33.add(dc33_1);
		c33.setDisponibilita(dc33);
		
		
		s1.inserisciCamera(c11);
		s1.inserisciCamera(c12);
		s1.inserisciCamera(c13);
		s2.inserisciCamera(c21);
		s2.inserisciCamera(c22);
		s2.inserisciCamera(c23);
		s3.inserisciCamera(c31);
		s3.inserisciCamera(c32);
		s3.inserisciCamera(c33);
		strutture.add(s1);
		strutture.add(s2);
		strutture.add(s3);
		CatalogoStrutture catalogo = CatalogoStrutture.prendiCatalogo();
		catalogo.setStrutture(strutture);
	}
	
	@AfterClass
	public static void rilascia()
	{
		CatalogoStrutture catalogo = CatalogoStrutture.prendiCatalogo();
		catalogo.getStrutture().clear();
	}

}
