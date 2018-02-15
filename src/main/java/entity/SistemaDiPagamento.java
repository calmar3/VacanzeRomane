package entity;

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.Random;




public class SistemaDiPagamento 
{
	public SistemaDiPagamento()
	{
		
	}
	
	public boolean convalidaPagamento()
	{
		Random numeroRandom = new Random();
		double probabilitaPagamento = numeroRandom.nextDouble();
		if(probabilitaPagamento < 0.1)
			return false;
		return true;
	}
	
	public boolean convalidaRimborso()
	{
		Random numeroRandom = new Random();
		double probabilitaPagamento = numeroRandom.nextDouble();
		if(probabilitaPagamento < 0.3)
			return false;
		return true;
	}
	

	
	// Bonifico
	public boolean convalidaBonifico(double importo, String iban, String swift,Gestore g) 
	{
		if(!controlloIban(iban))
			return false;
		if(!controlloSwift(swift))
			return false;
		if(!convalidaPagamento())
			return false;
		return true;
	}
	
	public boolean controlloIban(String iban) 
	{   
		if(iban.length() != 27)
			return false;
		iban = iban.toUpperCase();   
		StringCharacterIterator iterator = new StringCharacterIterator(iban);   
		StringBuilder ibanBuilder = new StringBuilder();   
		for(char ch = iterator.first(); ch != CharacterIterator.DONE; ch = iterator.next()) 
		{ 
			if(ch >= 'A' && ch <= 'Z') 
			{ 
				int valorePerIban = ch - 55; 
				ibanBuilder.append(valorePerIban); 
			} 
			else 
			{
				ibanBuilder.append(ch); 
			}
		}   
		BigDecimal ibanConvertito = new BigDecimal(ibanBuilder.toString());   
		BigDecimal resto = ibanConvertito.remainder(BigDecimal.valueOf(97));   
		if(resto.intValue() == 1) 
		  return true;
		else
			return false; 
		
	}
	
	public boolean controlloSwift(String swift)
	{
		if(swift.matches(".*\\d+.*"))	// Espressione regolare che verifica se c'è un numero all'interno della stringa
			return false;
		if(swift.length() >= 8 && swift.length() <= 11)
			return true;
		return false;
	}

	// Carta Di Credito
	
	public boolean convalidaCarta(double importo, String circuito, String numeroCarta, String codice, Date dataScadenza, Gestore g)
	{
		if(!controlloNumeroCartaDiCredito(numeroCarta))
			return false;
		if(!controlloDataScadenza(dataScadenza))
			return false;
		if(!convalidaPagamento())
			return false;
		return true;
	}
	
	public boolean controlloNumeroCartaDiCredito(String numeroCarta)
	{
		if(numeroCarta.length() != 16)
			return false;
		return true;
		/* Il codice qui sotto è commentato perchè serve per l'inserimento di una carta di credito reale
		 * Quindi è meglio commentarlo per i test  
		char[] tempNumeroCarta = numeroCarta.toCharArray();
		int[] nCarta = new int[16];
		int sum = 0;
		for(int i=0;i<16;i++)
		{
			try
			{
				nCarta[i] = Integer.parseInt(String.valueOf(tempNumeroCarta[i]));
			}
			catch(NumberFormatException e)
			{
				return false;
			}
			sum += nCarta[i];
		}
	    if(sum%10==0)
	        return true;
	    else
	        return false; */
	}
	
	public boolean controlloDataScadenza(Date dataScadenza)
	{
		Date dataAttuale = new Date();
		if(dataScadenza.before(dataAttuale))
			return false;
		return true;
	}

	public boolean accreditaSuCarta(String numeroCarta,Date scadenzaCarta, double importoDaRimborsare, String codiceContoCorrente) 
	{
		if(!controlloNumeroCartaDiCredito(numeroCarta))
			return false;
		if(!controlloDataScadenza(scadenzaCarta))	// La carta potrebbe essere scaduta nel frattempo
			return false;
		if(!convalidaRimborso())
			return false;
		return true;
		
	}

	public boolean accreditaSuConto(String iban, double importoDaRimborsare, String codiceContoCorrente)
	{
		if(!controlloIban(iban))
			return false;
		if(!convalidaRimborso())
			return false;
		return true;
	}
	
	
	
}
