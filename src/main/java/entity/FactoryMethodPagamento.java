package entity;

import bean.PagamentoBean;

/**
 * Restituisce alla prenotazione l'opportuna istanza di pagamento
 */
public abstract class FactoryMethodPagamento {

    public static Pagamento effettuaPagamento(int tipoPagamento,PagamentoBean pb,Prenotazione p) 
    {
        switch (tipoPagamento)
        {
        	case 0: return new PagamentoBonifico(pb.getIban(),pb.getSwift(),p);
        	case 1: return new PagamentoCartaCredito(pb.getCircuito(),pb.getNumeroCarta(),pb.getScadenzaCarta(),
        			pb.getCodice(),p);
        	default: return null;
        }
    }

}