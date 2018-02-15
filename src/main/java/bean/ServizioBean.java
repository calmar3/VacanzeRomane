package bean;

/**
 * Bean associato ad un servizio
 * */
public class ServizioBean 
{
	/**
	 * Attributi Servizio
	 */
	private String nomeServizio;
	private String prezzoServizio;
	private String descrizioneServizio;
	private String tipologiaServizio; // 0 per base -- 1 per premium
	
	/**
	 * Costruttore di default
	 */
	public ServizioBean()
	{
		this.setNomeServizio("");
		this.setPrezzoServizio("");
		this.setDescrizioneServizio("");
		this.setTipologiaServizio("");
	}
	
	/**
	 * GET - SET 
	 */
	public String getNomeServizio() 
	{
		return nomeServizio;
	}
	
	public void setNomeServizio(String nomeServizio) 
	{
		this.nomeServizio = nomeServizio;
	}
	
	public String getPrezzoServizio() 
	{
		return prezzoServizio;
	}
	
	public void setPrezzoServizio(String prezzoServizio) 
	{
		this.prezzoServizio = prezzoServizio;
	}
	
	public String getDescrizioneServizio() 
	{
		return descrizioneServizio;
	}
	
	public void setDescrizioneServizio(String descrizioneServizio) 
	{
		this.descrizioneServizio = descrizioneServizio;
	}
	
	public String getTipologiaServizio() 
	{
		return tipologiaServizio;
	}
	
	public void setTipologiaServizio(String tipologiaServizio) 
	{
		this.tipologiaServizio = tipologiaServizio;
	}
	

	/**
	 * Metodo equal per uguaglianza tra oggetti
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServizioBean other = (ServizioBean) obj;
		if (descrizioneServizio == null) {
			if (other.descrizioneServizio != null)
				return false;
		} else if (!descrizioneServizio.equals(other.descrizioneServizio))
			return false;
		if (nomeServizio == null) {
			if (other.nomeServizio != null)
				return false;
		} else if (!nomeServizio.equals(other.nomeServizio))
			return false;
		if (prezzoServizio == null) {
			if (other.prezzoServizio != null)
				return false;
		} else if (!prezzoServizio.equals(other.prezzoServizio))
			return false;
		if (tipologiaServizio == null) {
			if (other.tipologiaServizio != null)
				return false;
		} else if (!tipologiaServizio.equals(other.tipologiaServizio))
			return false;
		return true;
	}
	
}
