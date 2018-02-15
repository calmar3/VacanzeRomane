package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean di contenimento strutture
 * In questo Bean verranno poste i bean delle Strutture in seguito
 * ad una ricerca oppure le strutture relative ad un gestore
 */
public class CatalogoBean {

	/**
	 * Lista strutture
	 */
	private List<StrutturaBean> strutture;
	
	/**
	 * Costruttore di default
	 */
	public CatalogoBean() 
	{
		this.strutture = new ArrayList<StrutturaBean>();
	}

	/**
	 * GET-SET
	 */
	public List<StrutturaBean> getStrutture() {
		if (strutture == null)
			strutture = new ArrayList<StrutturaBean>();
		return strutture;
	}

	public void setStrutture(List<StrutturaBean> strutture) {
		this.strutture = strutture;
	}

}
