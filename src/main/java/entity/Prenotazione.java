package entity;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import bean.PagamentoBean;
import exception.LineaPrenotazioneException;
import hibernate.FactoryPrenotazione;


@Entity
@Table(name = "Prenotazione")
public class Prenotazione{

    /**
     * Default constructor
     */
    @OneToMany(mappedBy="prenotazione" , cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<LineaPrenotazione> linee;
	
	@Id
	@Column (name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column (name="pin")
    private String codicePin;
	
	@Column(name="importo")
    private double importo = 0;
	
	@OneToOne (mappedBy="prenotazione", cascade = CascadeType.ALL)
	@JoinColumn(name="id_pagamento")
    private Pagamento pagamento; 
	
    @ManyToOne
    @JoinColumn(name = "id_affittuario")
	private Affittuario affittuario;
    
    @ManyToOne
    @JoinColumn(name = "id_struttura")
    private Struttura strutturaPrenotata;



    /**
     * Costruttore di default
     */
    public Prenotazione() {
    }    
    

    /**
     * @param c
     * @param checkIn
     * @param checkOut
     * @param serviziScelti
     * @return
     * @throws LineaPrenotazioneException
     */
    public double aggiungiLineaPrenotazione(Camera c,String checkIn,String checkOut,List<Servizio> serviziScelti) throws LineaPrenotazioneException {
    	if (this.getLinee() == null || this.getLinee().isEmpty())
    		this.setLinee(new ArrayList<LineaPrenotazione>());
    	LineaPrenotazione lp = new LineaPrenotazione(c,this,checkIn,checkOut,serviziScelti);
    	this.importo += lp.getImporto();
    	this.getLinee().add(lp);
    	return lp.getImporto();
    }

    /**
     * Associa una istanza di pagamento al suo attributo
     * servendosi del factory method
     * @param pb
     */
    public void effettuaPagamento(PagamentoBean pb) {
       if (pb.getTipologia().equals("Bonifico"))
    	   this.setPagamento(FactoryMethodPagamento.effettuaPagamento(0, pb,this));
       else 
    	   this.setPagamento(FactoryMethodPagamento.effettuaPagamento(1,pb,this));
    }

    
	/**
	 * metodo per generare un codice pin a 4 cifre
	 */
	public void generaPin() {
		boolean generato = false;
		String codice = new String();
		while (!generato)
		{
			int pin = new Random().nextInt(10000);
			codice = Integer.toString(pin);
			if (codice.length() == 1)
				codice = "000" + codice;
			else if (codice.length() == 2)
				codice = "00" + codice;
			else if (codice.length() == 3)
				codice = "0" + codice;
			generato = this.verificaCodice(codice);
		}
		if (codice != null || codice.length()==4)
			this.setCodicePin(codice);
	}

	/**
	 * metodo per verificare che il codice generato non sia gia presente
	 * @param codice
	 * @return
	 */
	private boolean verificaCodice(String codice) {
		FactoryPrenotazione fp = new FactoryPrenotazione();
		List<String> codici = fp.ottieniCodici();
		if (codici == null)
			return true;
		else
		{
			for (String c: codici)
				if (codice.equals(c))
					return false;
		}
		return true;
	}
    /**
     * Elimina la linea prenotazione dalla sua lista
     * aggiornando il pagamento associato e i suoi attributi
     * @param lp
     */
    public void eliminaLineePrenotazione(LineaPrenotazione lp) {
    	pagamento.autorizzaRimborso(lp.getImporto(),this.strutturaPrenotata.getGestore());
    	this.setPagamento(pagamento);
    	this.importo -= lp.getImporto();
        this.getLinee().remove(lp);
        if(this.linee.size() == 0)
        	return;
    }

	/**
	 * GET - SET 
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodicePin() {
		return codicePin;
	}

	public void setCodicePin(String codicePin) {
		this.codicePin = codicePin;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Affittuario getAffittuario() {
		return affittuario;
	}

	public void setAffittuario(Affittuario affittuario) {
		this.affittuario = affittuario;
	}

	public List<LineaPrenotazione> getLinee() {
		if (this.linee==null)
			linee = new ArrayList<LineaPrenotazione>();
			HashSet<LineaPrenotazione> hs = new HashSet<LineaPrenotazione>();
            hs.addAll(this.linee);
            linee.clear();
            linee.addAll(hs);
            return linee;
	}

	public void setLinee(List<LineaPrenotazione> linee) {
		this.linee = linee;
	}

	public Struttura getStrutturaPrenotata() {
		return strutturaPrenotata;
	}

	public void setStrutturaPrenotata(Struttura strutturaPrenotata) {
		this.strutturaPrenotata = strutturaPrenotata;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}
	
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

}