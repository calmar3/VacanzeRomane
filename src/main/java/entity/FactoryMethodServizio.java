package entity;


import bean.ServizioBean;

/**
 * Restituisce un oggetto di tipo Servizio istanziato da una sua sottoclasse
 * (ServizioBase o ServizioPremium)
 *
 */
public abstract class FactoryMethodServizio 
{
   
    public static Servizio creaServizio(Camera c, ServizioBean sb) 
    {
    	if (sb.getTipologiaServizio().equals("0"))
    		return new ServizioBase(c,sb.getNomeServizio(),sb.getDescrizioneServizio());
    	else if (sb.getTipologiaServizio().equals("1"))
    		return new ServizioPremium(c,sb.getNomeServizio(),sb.getDescrizioneServizio(),
    				sb.getPrezzoServizio());
		return null;

    }
    
    

}