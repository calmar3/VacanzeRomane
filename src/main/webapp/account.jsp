<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>


<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="lineaPrenotazioneBean" scope="session" class="bean.LineaPrenotazioneBean" />
<jsp:useBean id="prenotazioneBean" scope="session" class="bean.PrenotazioneBean" />
<jsp:useBean id="catalogoBean" scope="session" class="bean.CatalogoBean" />
<jsp:setProperty name="utenteBean" property="*" /> 
 
<!-- Logout JSP  -->
<%
	if(request.getParameter("confermaLogout")!= null)
	{
		
		GestoreUtente.avviaControllerUtente().logout(utenteBean);
	    String redirectURL = "index.jsp";
	    response.sendRedirect(redirectURL);
	};
%>


<!-- Modifiche Account JSP  -->
<% 
	if (request.getParameter("confermaModifiche") != null)
	{
		utenteBean.modifica();
	};
%>

<!-- EliminaAccount JSP  -->
<% 
	if (request.getParameter("eliminaAccount") != null)
	{
		if(GestoreUtente.avviaControllerUtente().eliminaAccount(utenteBean,catalogoBean))
		{
			utenteBean.setLog(false);
		    String redirectURL = "index.jsp";
		    response.sendRedirect(redirectURL);
		}
	};
%>
	
<!-- RimozionePrenotazione JSP  -->
<%
	if(request.getParameter("indicePrenotazione") != null)
	{
		Integer indicePrenotazione = Integer.valueOf(request.getParameter("indicePrenotazione"));
		if(request.getParameter("indiceLinea")!=null)
		{
			Integer indiceLinea = Integer.valueOf(request.getParameter("indiceLinea"));
			GestorePrenotazioni controllerPrenotazioni = new GestorePrenotazioni();
			controllerPrenotazioni.eliminaLineaPrenotazione(indicePrenotazione, indiceLinea, utenteBean);
		}
	}
%>

<!--   -->
<% boolean affittuario = utenteBean.getTipologia().equals("Affittuario"); 
	if (affittuario && utenteBean.isLog())
	{
		GestorePrenotazioni controllerPrenotazioni= new GestorePrenotazioni();
		controllerPrenotazioni.caricaPrenotazioniUtente(utenteBean);	
	}
   boolean gestore = utenteBean.getTipologia().equals("Gestore");
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
                 <%if (affittuario) {
		            %>
		            <a class="navbar-brand page-scroll" href="affittuario.jsp">Vacanze Romane</a>
		            <%}
		            else{%>
		            <a class="navbar-brand page-scroll" href="gestore.jsp">Vacanze Romane</a>
		            <%}%>
		    </div>
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                	<%if(affittuario)
                	{%>
                	<li>
                		<a class="page-scroll" href="#prenotazioni">Le tue prenotazioni</a>
                	 <li>
                	 <li>
                		<li><a href="carrello.jsp"><span class="glyphicon glyphicon-shopping-cart	" aria-hidden="true"></span></a>
                	</li>
                	<%}
                	else{%>
                	<li>
                		<a class="page-scroll" href="#strutturePrenotate">Le strutture prenotate</a>
                	 <li>
                	 <%} %>
                	<li>
                    	<a href="account.jsp"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>    <%= utenteBean.getNome()%> <%= utenteBean.getCognome()%></a></li>
                    
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

	<!-- Central Body -->
	
	<!-- Locazioni -->
	<form name="modificaUtente" method="POST">
	<section id="account" class="bg-dark">
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Il tuo account</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
        	<div class="row">
			<div class="panel panel-default">
  				<div class="panel-heading">
  					<div class="clearfix"></div>
    				<button type="submit" name="eliminaAccount" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Elimina Account</button>
  					<h3 class="panel-title">Informazioni</h3>	
  					<div class="clearfix"></div>
  				</div>
  				<div class="panel-body">
  				<div class="row">
  					<div class="col-sm-4">
     					<h5>Nome</h5>
                    	<input type="text" id="nomeAccount" name="nome" class="form-control" placeholder="<%=utenteBean.getNome()%>">
              		</div>
              		<div class="col-sm-4">
                    	<h5>Cognome</h5>
                    	<input type="text" id="cognomeAccount" name="cognome" class="form-control" placeholder="<%=utenteBean.getCognome()%>">
              		</div>
              		<div class="col-sm-4">
              			<h5>E-mail</h5>
                        <label for="mailAccount" class="sr-only">Email</label>
                        <input type="email" name="mail" id="mailAccount" class="form-control" placeholder="<%=utenteBean.getMail()%>">
                     </div>
                  </div>
                  <br><br>
                  <div class="row">
              		<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Username</h5>
                    	<input type="text" id="usernameAccount" name="username" class="form-control" placeholder="<%=utenteBean.getUsername()%>">
              		</div>
              		<div class="col-sm-4 col-sm-offset-2">
                    	<h5>Password</h5>
                    	<input type="password" id="passwordAccount" name="password" class="form-control" placeholder="<%=utenteBean.getPassword()%>">
              		</div>
              	  </div>
              	  <br><br>
              	  <div class="row">
              	  	<div class="col-sm-4 col-sm-offset-4">
              	  		<h5>Tipologia Utente</h5>
                    	<input type="text" id="tipologiaAccount" name="tipologia" readonly class="form-control" placeholder="<%=utenteBean.getTipologia()%>">
              	  	</div>
              	  </div>
              	  <div class="row">
              		<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Numero conto corrente</h5>
                    	<input type="text" id="numeroConto" name="codiceContoCorrente" class="form-control" readonly placeholder="<%=utenteBean.getCodiceContoCorrente()%>">
              		</div>
              	  </div>
              	  <br>
					<div class="panel-footer">	
							<input type="submit" id="btn-modifiche" class="btn btn-custom pull-right" name="confermaModifiche" value="Conferma">
  					</div>
				 </div>
			</div>	
 			</div>
 		</div>
	</section>
	</form>
	
	<!-- Coordinate Bancarie -->
	<%if(gestore) 
	{
	%>
	<section id="strutturePrenotate">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Strutture Prenotate</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
      		<% GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
      		   controllerLocazioni.caricaPrenotazioniStrutture(utenteBean);
      		   for(int i=0;i<utenteBean.getPrenotazioni().size();i++)
      		   {%>
      		       <div class="panel panel-primary">
		  				<div class="panel-heading">
		  					<h3 class="panel-title">Struttura: <%=utenteBean.getPrenotazioni().get(i).getNomeStruttura() %></h3>	
		    			</div>
			    	<div class="panel-body">
			    	<% for(int j=0;j<utenteBean.getPrenotazioni().get(i).getLinee().size();j++)
			    	{%>
			    	<div class="panel panel-primary">
		  				<div class="panel-heading">
		  					<h3 class="panel-title">Camera: <%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getCamera().getNomeCamera() %></h3>	
		    			</div>
			    	<div class="panel-body">
							<div class="row">
							    <div class="col-sm-4">
							    	<b>Data check-in: </b><%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getCheckIn()%>
							    </div>
							    <div class="col-sm-4 col-lg-offset-2">
							    	<b>Data check-out: </b> <%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getCheckOut()%>
							    </div>
							</div>
							<div class="row">
						  	<%if (utenteBean.getPrenotazioni().get(i).getLinee().get(j).getServiziScelti().size()>0) 
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
										<%System.out.println("dAI CHE CE STAMO");%>
										
										<%for (ServizioBean sb: utenteBean.getPrenotazioni().get(i).getLinee().get(j).getServiziScelti()) 
										 {
											System.out.println("AOOOOoooooOooOoOo");
										 	%>
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
						  </div>
				    	<%} %>
		            </div>
        	</div>
        	<%} %>
        	</div>
        	</div>
      </section>
      <%} %>
	
	
	
	<!-- Prenotazioni -->
	<%if(affittuario)
		{%>
	<form name="tuePrenotazione" method="POST">
	<section id="prenotazioni">
	<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Le tue Prenotazioni</h2>
                    <hr class="primary">
                </div>
            </div>
            <div class="container">
  				<% if (utenteBean.getPrenotazioni().size()>0)
  			 		{%>
  		 				<% for (int i=0;i<utenteBean.getPrenotazioni().size();i++) 
  		 					{%>
  		 						<div class="row">
								<div class="panel panel-default">
  								<div class="panel-heading">
  									<div class="clearfix"></div>
    								<h3 class="panel-title">Prenotazione</h3>	
  									<div class="clearfix"></div>
  								</div>
  								<div class="panel-body">
  		 							<div class="row">
  		 							<div class="col-sm-6">
  		 								<p>Pin : <%=utenteBean.getPrenotazioni().get(i).getPin() %></p>
  		 							</div>
  		 							<div class="col-sm-6">
  		 								<p>Importo totale : <%=utenteBean.getPrenotazioni().get(i).getImporto() %> </p>
  		 							</div>
  		 							</div>
  		 							<br>
  		 							<% for(int j=0; j<utenteBean.getPrenotazioni().get(i).getLinee().size();j++) {%>
  		 							<div class="row">
  		 							<div class="col-sm-10">
  		 								<div class="panel panel-default">
  											<div class="panel-heading">
  												<div class="clearfix"></div>
  												<a href="account.jsp?indicePrenotazione=<%=i %>&indiceLinea=<%=j %>" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancella</a>
  												<h4 class="panel-title">Linea Prenotazione</h4>	
  												<div class="clearfix"></div>
  											</div>
  											<div class="panel-body">
  											<div class="row">
  											<div class="col-sm-5">
	      											<strong>Data check-in: </strong><%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getCheckIn() %>
	      									</div>
	      									<div class="col-sm-5">
	      											<strong>Data check-out:</strong> <%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getCheckOut() %>
	      									</div>
  											</div>
  											<br>
  											<div class="row">
	      									<div class="col-sm-5">
	      											Importo : <%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getImporto() %>
	      									</div>
	      									<div class="col-sm-5">
	      											Nome Camera : <%=utenteBean.getPrenotazioni().get(i).getLinee().get(j).getCamera().getNomeCamera() %>
	      									</div>
  											</div>
  		 									</div>
	      								</div>
	      							</div>
	      							</div>
    								<%} %>
    							</div>
      							</div>
      							</div>
    						<%}%>
    					<%}%>
  				</div>
          </div>
	</section>
	</form>
	<%} %>
	
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
  				$('.datepicker').datepicker();
  			});
  		
  	</script>


</body>
</html>
   