<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>

<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="catalogoBean" scope="session" class="bean.CatalogoBean" />


<% session.setAttribute("strutturaBean",null); %>
<%GestoreLocazioni controllerLocazioni= new GestoreLocazioni();
controllerLocazioni.caricaStruttureGestore(catalogoBean,utenteBean); %>

<% if (request.getParameter("indRimozione")!= null)
	{
		Integer indiceRimozione = Integer.valueOf(request.getParameter("indRimozione"));
		controllerLocazioni.rimuoviStruttura(indiceRimozione,utenteBean,catalogoBean);
		String redirectURL = "gestore.jsp";
	    response.sendRedirect(redirectURL);
	}
%>


<%
	if(request.getParameter("confermaLogout")!= null)
	{
		GestoreUtente.avviaControllerUtente().logout(utenteBean);
		session.setAttribute("utenteBean", null);
		String redirectURL = "index.jsp";
	    response.sendRedirect(redirectURL);
	};
	
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

	<!-- Header -->
    <header>
        <div class="header-content">
            <div class="header-content-inner">
                <h1>Benvenuto a casa</h1>
                <hr>
                <p>Accogli i viaggiatori e guadagna qualcosa in più</p>
            </div>
        </div>
    </header>
	
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
	<section id="locazioni">
		 <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Le tue strutture</h2>
                    <a href="inserimentoLocazione.jsp" class="btn btn-custom pull-left"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>  Inserisci una nuova struttura</a><br>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
         <% 
				if(catalogoBean.getStrutture() != null)
				{
					if (catalogoBean.getStrutture().isEmpty()){%>
					<p class="flow-text center-align">Non è presente alcuna struttura!</p>
					<% 
					}
					else{
						for(int i=0;i<catalogoBean.getStrutture().size();i++)
							{
								%>
									<div class="row">
									<div class="panel panel-primary">
  										<div class="panel-heading">
  											<div class="clearfix"></div>
    										<a href="gestore.jsp?indRimozione=<%= i%>" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Elimina</a>	
    							  			<a href="visualizzaLocazione.jsp?indice=<%= i%>" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-tag" aria-hidden="true"></span> Visualizza In Dettaglio</a>
    							  			<h3 class="panel-title"><%= catalogoBean.getStrutture().get(i).getNome()%></h3>	
    							  			<div class="clearfix"></div>
  										</div>
  									<div class="panel-body">
									<div class="row">
										<div class="col-sm-6">
											<p>Tipologia: <%=catalogoBean.getStrutture().get(i).getTipologia()%></p>
										</div>
										<div class="col-sm-6">
											<p>Descrizione: <%=catalogoBean.getStrutture().get(i).getDescrizione()%></p>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
										<p>Nazione: <%=catalogoBean.getStrutture().get(i).getNazione()%></p>
										</div>
										<div class="col-sm-6">
											<p>Città: <%=catalogoBean.getStrutture().get(i).getCitta() %>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-5">
											<p>Indirizzo: <%=catalogoBean.getStrutture().get(i).getIndirizzo()%>
										</div>
									</div>
									</div>
									<div class="panel-footer">
										<h4 class="panel-footer-title text-center">Camere</h4>	
									<div class="col s6"> 
					 					<ul class="collapsible" data-collapsible="accordion">
              							<% for (int j=0; j<catalogoBean.getStrutture().get(i).getCamere().size();j++)
      									{%>
    										<li>
      											<div class="collapsible-header"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
      					 						<%=catalogoBean.getStrutture().get(i).getCamere().get(j).getNomeCamera()%></div>
												<div class="collapsible-body">
													<p>Tipologia: <%=catalogoBean.getStrutture().get(i).getCamere().get(j).getTipologiaCamera()%><br>
													Descrizione: <%=catalogoBean.getStrutture().get(i).getCamere().get(j).getDescrizioneCamera()%><br>
													Prezzo:	<%=catalogoBean.getStrutture().get(i).getCamere().get(j).getPrezzoCamera() %></p>
												</div>
      										</li>
 				 						<%
 				 						}%>
 				 						</ul>
 				 					</div>
  									</div>
									</div>
									</div>	
 				 <%
				}}}%>
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
                        <i class="fa fa-4x fa-users wow bounceIn text-primary"></i>
                        <h3 class="text-muted">Piattaforma facile da usare</h3>
                        <p class="text-muted">Non bisogna essere esperti informatici per utilizzare le nostre piattaforme: sono semplici e intuitive.</p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-globe wow bounceIn text-primary" data-wow-delay=".1s"></i>
                        <h3 class="text-muted">Rilevanza</h3>
                        <p class="text-muted">Puoi scegliere quali strutture mostrare.Tutte o solo quelle più rilevanti</p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-money wow bounceIn text-primary" data-wow-delay=".2s"></i>
                        <h3 class="text-muted">Elevato potenziale di profitti</h3>
                        <p class="text-muted">Approfittane per aumentare i TUOI guadagni.</p>
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
