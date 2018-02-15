<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>


<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="ricercaBean" scope="request" class="bean.RicercaBean" />
<jsp:useBean id="catalogoBean" scope="session" class="bean.CatalogoBean" />
<jsp:setProperty name="ricercaBean" property="*" />
<% session.setAttribute("prenotazioneBean",null); %>


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
	if (request.getParameter("ricerca")!=null)
	{
		ricercaBean.effettuaRicerca(catalogoBean);
		String redirectURL = "ricerca.jsp";
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
                	<li>
                		<a class="page-scroll" href="#ricerca"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
                	</li>
          
                		<li><a href="carrello.jsp"><span class="glyphicon glyphicon-shopping-cart	" aria-hidden="true"></span></a>
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
                <p>Viaggia, esplora il mondo e visita nuovi e fantastici posti!</p>
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
    
    <!-- Ricerca -->
	<form name="ricercaForm" method="POST">
	<section id="ricerca">
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Cerca</h2>
                </div>
            </div>
        </div>
		<hr class="light"><br><br>	
        <div class="container">
            <div class="row">
            	<div class="col-lg-4 col-lg-offset-2">
            		<div class="form-group">
                    	<label for="citta" class="sr-only">Citta</label>
                    	<input type="text" id="citta" name="luogo" class="form-control" placeholder="Citta">
              		</div>
              	</div>
              	<div class="col-lg-4 col-lg-offset">
              	<select class="selectpicker" data-width="100%" name="tipologiaStruttura">
              			<option value="" disabled selected>Tipologia Struttura</option>
  						<option value="Hotel">Hotel</option>
  						<option value="B&B">Bed&amp;Breakfast</option>
  						<option value="Agriturismo">Agriturismo</option>
				</select>
				</div>
			</div>
			<br>
			<div class="row">
              	<div class="col-lg-4 col-lg-offset-2">	
              		<div class="form-group">
                    	<label for="nPersone" class="sr-only">Numero Persone</label>
                    	<input type="number" id="nPersone" name="numeroPersone" class="form-control" placeholder="Numero Persone">
                 	</div>
              	</div>
              	<div class="col-lg-4 col-lg-offset">
              	<select class="selectpicker" data-width="100%" name="tipologiaCamera">
              			<option value="" disabled selected>Tipologia Camera</option>
  						<option value="Camera Singola">Camera Singola</option>
  						<option value="Camera Matrimoniale">Camera Matrimoniale</option>
  						<option value="Camera Tripla">Camera Tripla</option>
				</select>
				</div>
            </div>
            <br>
             <div class="row">
            <div class="col-lg-4 col-lg-offset-2">
            		<div class="form-group">
                    	<label for="prezzoMin" class="sr-only">Prezzo Minimo</label>
                    	<input type="text" id="prezzoMin" name="prezzoMin" class="form-control" placeholder="Prezzo Minimo">
              		</div>
              	</div>
             <div class="col-lg-4 col-lg-offset">
            		<div class="form-group">
                    	<label for="prezzoMax" class="sr-only">Prezzo Massimo</label>
                    	<input type="text" id="prezzoMax" name="prezzoMax" class="form-control" placeholder="Prezzo Massimo">
              		</div>
            </div>
            </div>
            <br>
            <div class="row">
                <div class='col-sm-5 col-lg-offset-1'>
				      <div class="form-group">
                		<div class='input-group'  data-provide='datepicker'>
                    		<input type='text' name="dataCheckIn" class="form-control datepicker" placeholder="Data Check-In" />
                    		<span class="input-group-addon">
                        		<span class="glyphicon glyphicon-calendar"></span>
                    		</span>
						</div>
            		</div>
        		</div>
        		<div class='col-sm-5'>
				      <div class="form-group">
                		<div class='input-group' data-provide='datepicker'>
                    		<input type='text' name="dataCheckOut" class="form-control datepicker" placeholder="Data Check-Out" />
                    		<span class="input-group-addon">
                        		<span class="glyphicon glyphicon-calendar"></span>
                    		</span>
						</div>
            		</div>
        		</div>
            </div>
            <br><br>
            <div class="row">
            	<div class="col-sm-5 col-lg-offset-5">
            		<button name="ricerca" id="ricerca" type="submit" class="btn btn-primary btn-xl page-scroll"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Ricerca</button>
            	</div>
            </div>
		</div>
    </section>
    </form>
    
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
