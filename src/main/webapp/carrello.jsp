<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>


<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="strutturaBean" scope="session" class="bean.StrutturaBean" /> 
<jsp:useBean id="cameraBean" scope="session" class="bean.CameraBean" />
<jsp:useBean id="lineaPrenotazioneBean" scope="session" class="bean.LineaPrenotazioneBean" />
<jsp:useBean id="prenotazioneBean" scope="session" class="bean.PrenotazioneBean" />
<jsp:useBean id="pagamentoBean" scope="session" class="bean.PagamentoBean" />


<!-- ,tipologia,circuito,numeroCarta,codice;
	private Date scadenzaCarta; -->

<jsp:setProperty name="pagamentoBean" property="*" />


<%
	if(request.getParameter("confermaLogout")!= null)
	{
		GestoreUtente.avviaControllerUtente().logout(utenteBean);
		session.setAttribute("utenteBean", null);
	    String redirectURL = "index.jsp";
	    response.sendRedirect(redirectURL);
	};
%>


<%
	if(request.getParameter("pagaCarta")!= null )
		pagamentoBean.setTipologia("Carta di credito");
	else if(request.getParameter("pagaBonifico")!=null)
		pagamentoBean.setTipologia("Bonifico");
	if (request.getParameter("pagaCarta")!=null || request.getParameter("pagaBonifico")!=null)
	{
		if (pagamentoBean.confermaPrenotazione(utenteBean))
		{
			String redirectURL = "affittuario.jsp";
			response.sendRedirect(redirectURL);
		}
	}
%>

<!-- HTML 5 -->
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Marco Calzetta, Danilo Ceravolo">

    <title>Vacanze Romane</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="css/animate.min.css" type="text/css">
    <link rel="stylesheet" href="css/custom.css" type="text/css">
   <!--  <link rel="stylesheet" href="css/datepicker.css" type="text/css"> -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/css/bootstrap-select.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/creative.css" type="text/css">

</head>


<body id="page-top">

    <!-- Navigation Panel -->
    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand page-scroll" href="#page-top">Vacanze Romane</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    	<li><a href="account.jsp"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>    <%= utenteBean.getNome()%> <%= utenteBean.getCognome()%></a></li>
                    <li>
                        <a class="forget" data-toggle="modal" data-target=".forget-modal-logout" href="#modalLogout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    
    <!-- Logout Popup -->
	<form name="logoutForm" method="POST" autocomplete="off">
		<div id="modalLogout" class="modal fade forget-modal-logout" tabindex="-1" role="dialog" aria-labelledby="myLogoutModalLabel" aria-hidden="true">
    		<div class="modal-dialog">
        		<div class="modal-content">
            		<div class="modal-header">
                		<button type="button" class="close" data-dismiss="modal">
                    		<span aria-hidden="true">×</span>
                    		<span class="sr-only">Close</span>
                		</button>
                		<h4 class="modal-title">Logout</h4>
            		</div>
            	<div class="modal-body">
              		<h4>Sei sicuro di voler effettuare il logout?</h4>
            	</div>
            	<div class="modal-footer">
                	<input type="submit" id="btn-login" class="btn btn-custom " name="confermaLogout" value="Conferma">
            	</div>
        		</div> 
    		</div>
		</div> 
	</form>
    
    
  	<!-- Pagamento Carta Popup -->
	<form name="pagamentoCartaForm" method="POST" autocomplete="off">
		<div id="modalCarta" class="modal fade forget-modal-carta" tabindex="-1" role="dialog" aria-labelledby="myCartaModalLabel" aria-hidden="true">
    		<div class="modal-dialog">
        		<div class="modal-content">
            		<div class="modal-header">
                		<button type="button" class="close" data-dismiss="modal">
                    		<span aria-hidden="true">×</span>
                    		<span class="sr-only">Close</span>
                		</button>
                		<h4 class="modal-title">Pagamento: Carta di credito</h4>
            		</div>
            	<div class="modal-body">
            		<div class="form-group">
            		<select class="selectpicker" name="circuito" data-width="100%">
              			<option value="" disabled selected>Circuito</option>
  						<option value="Visa">Visa</option>
  						<option value="Mastercard">MasterCard</option>
					</select>
            		</div>
              		<div class="form-group">
                    	<h4>Numero carta</h4>
                    	<input type="text" id="numeroCarta" name="numeroCarta" class="form-control">
              		</div>
              		<div class="form-group">
              			<h4>Scadenza Carta</h4>
                    	<div class='input-group' data-provide='datepicker'>
                    		<input name="scadenzaCarta" id="scadenza" type='text' style="z-index: 1178!important" class="form-control datepicker" />
                    		<span class="input-group-addon">
                        		<span class="glyphicon glyphicon-calendar"></span>
                    		</span>
						</div>
              		</div>
              		<div class="form-group">
              		<select class="selectpicker" name="codice" data-width="100%">
              			<option value="" disabled selected>Scegli</option>
  						<option value="CVV2">CVV2</option>
  						<option value="CVC2">CVC2</option>
  						<option value="4DBC">4DBC</option>
					</select>
              		</div>
            	</div>
            	<div class="modal-footer">
            		<button class="btn btn-primary pull-right" >Annulla</button>
            		<button class="btn btn-primary pull-right" name="pagaCarta">Conferma pagamento</button>
            	</div>
        		</div> 
    		</div>
		</div> 
	</form>
  	
  	<!-- Pagamento Bonifico Popup -->
	<form name="pagamentoBonificoForm" method="POST" autocomplete="off">
		<div id="modalBonifico" class="modal fade forget-modal-bonifico" tabindex="-1" role="dialog" aria-labelledby="myBonificoModalLabel" aria-hidden="true">
    		<div class="modal-dialog">
        		<div class="modal-content">
            		<div class="modal-header">
                		<button type="button" class="close" data-dismiss="modal">
                    		<span aria-hidden="true">×</span>
                    		<span class="sr-only">Close</span>
                		</button>
                		<h4 class="modal-title">Pagamento: Bonifico</h4>
            		</div>
            	<div class="modal-body">
              		<div class="form-group">
                    	<h4>Iban</h4>
                    	<input type="text" id="iban" name="iban" class="form-control">
              		</div>
              		<div class="form-group">
                    	<h4>Swift</h4>
                    	<input type="text" id="swift" name="swift" class="form-control">
              		</div>
            	</div>
            	<div class="modal-footer">
            		<button class="btn btn-primary pull-right" >Annulla</button>
            		<button class="btn btn-primary pull-right" name="pagaBonifico">Conferma pagamento</button>
            	</div>
        		</div> 
    		</div>
		</div> 
	</form>
    
    <!-- Central Body -->
    <section id="carrello" class="bg-dark">
    <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Carrello</h2>
                    <hr class="primary">
                </div>
            </div>
    </div>
    <div class="container">
    	<div class="row"> 
    		<h4 class="center-align">Camere prenotate</h4>
    	<div class="panel panel-default">
    		<div class="panel-heading">
				<h3 class="panel-title">Carrello</h3>	
			</div>
			<div class="panel-body">
				
		<%for (LineaPrenotazioneBean lpb: prenotazioneBean.getLinee())
			{%>
				<div class="container">
				<div class="row"><br>
				<div class="col-sm-11">
				<div class="panel panel-default">
					<div class="panel-heading">
						  	<h3 class="panel-title">Linea Prenotazione</h3>	
					</div>
					<div class="panel-body">
						<div class="row">
						  	<div class="col-sm-4">
						  		<h5><b>Nome Camera: </b><%=lpb.getCamera().getNomeCamera()%></h5><br>
						  	</div>
						  	<div class="col-sm-4 col-lg-offset-2">
						  		<b>Descrizione camera: </b><%=lpb.getCamera().getDescrizioneCamera()%>
						  	</div>
						</div>
						<div class="row">
						   <div class="col-sm-4">
						    	<b>Tipologia camera: </b> <%=lpb.getCamera().getTipologiaCamera()%>
						    </div>
						    <div class="col-sm-4 col-lg-offset-2">
						    	<b>Prezzo camera: </b> <%=lpb.getCamera().getPrezzoCamera()%> Euro
						    </div>
						</div>
						<br><br>
						<div class="row">
						    <div class="col-sm-4">
						    	<b>Data check-in: </b><%=lpb.getCheckIn()%>
						    </div>
						    <div class="col-sm-4 col-lg-offset-2">
						    	<b>Data check-out: </b> <%=lpb.getCheckOut()%>
						    </div>
						</div>
						<div class="row">
						  	<%if (lpb.getServiziScelti().size()>0) 
						  		{%>
						  			<br><br>
						  			<div class="col-sm-11">
									<table class="table">
										<thead>
										  <tr>
										    <th data-field="nome">Servizio Scelto</th>
										    <th data-field="descrizione">Descrizione</th>
										    <th data-field="prezzo">Prezzo</th>
										    </tr>
										</thead>
										<%for (ServizioBean sb: lpb.getServiziScelti()) 
										 {%>
										  	<tbody>
										      <tr>
										      <td><%=sb.getNomeServizio() %></td>
										      <td><%=sb.getDescrizioneServizio()%></td>
										        <%if (sb.getPrezzoServizio().equals("")){ %>
										            			<td>-</td>
										         <%}
										         else {%>
										            <td><%=sb.getPrezzoServizio() %></td>
										         <%} %>
										       </tr>
										     </tbody>
										 <%} %>	
										  	</table>
										  	</div>
						  			<%}%>
						  		</div>
						  		</div>
						  		<div class="panel-footer">
						  				<div class="row text-center"><b>Totale Linea: <%=lpb.getImporto() %></b></div><br>
						  		</div>
						 </div>
						 </div>
						 </div>
						 </div>
					
						<%}//endfor %>
					</div>			
  			
  			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-3">
						<b>Totale Prenotazione: <%= prenotazioneBean.getImporto() %></b>
					</div>
					<div class="col-sm-6 col-lg-offset">
						<a class="forget btn btn-custom pull-right" data-toggle="modal" data-target=".forget-modal-bonifico" href="#modalBonifico">Bonifico</a>
					</div>
					<div class="col-sm-3 ">
						<a class="forget btn btn-custom pull-left" data-toggle="modal" data-target=".forget-modal-carta" href="#modalCarta">Carta di credito</a>
					</div>
				</div>
			</div>
			</div>
			</div>
			</div>
  	  </section>
    
<!-- Servizi -->
	<section id="services" class="bg-dark">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">I Nostri Servizi</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-diamond wow bounceIn text-primary"></i>
                        <h3 class="text-muted">Affitta quando e come vuoi!</h3>
                        <p class="text-muted">Potrai visitare qualunque posto vorrai</p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-paper-plane wow bounceIn text-primary" data-wow-delay=".1s"></i>
                        <h3 class="text-muted">Gestisci le tue strutture</h3>
                        <p class="text-muted">Pubblicizza le strutture che possiedi!</p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-newspaper-o wow bounceIn text-primary" data-wow-delay=".2s"></i>
                        <h3 class="text-muted">Libertà</h3>
                        <p class="text-muted">Potrai pagare in diversi modi, andando a scegliere la struttura e la camera che desideri.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
  

    
    <footer id="footer">
    <div class="container"><br><br>
        <div class="row">
            <div class="col-xs-12">
                <p>Vacanze Romane - Sistema di prenotazioni a scopo vacanziero</p>
                <p>Powered by Marco Calzetta, Danilo Ceravolo</p><br><br>
            </div>
        </div>
    </div>
	</footer>

  	
    <!-- jQuery -->
    <script src="js/jquery.js"></script>
   

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/jquery.fittext.js"></script>
    <script src="js/wow.min.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/creative.js"></script>
    
    <!-- Javascript -->
	<script>
		
  		$(document).ready(function()
  			{
  				$('.selectpicker').selectpicker({
  			  		style: 'btn-info',
  			  		size: 4
  					});
  				$.fn.datepicker.defaults.format = "dd/mm/yyyy";
  				$('.datepicker').datepicker({
  					style: 'z-index:1151 !important'
  				});
  			});
  		
  	</script>


</body>
</html>
   