<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>


<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="strutturaBean" scope="session" class="bean.StrutturaBean" /> 
<jsp:setProperty name="strutturaBean" property="*" />
<%session.setAttribute("cameraBean", null); %>


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
	if(request.getParameter("confermaStruttura")!= null)
	{
		if(strutturaBean.inserisciStruttura(utenteBean))
		{ 	   
			String redirectURL = "gestore.jsp";
	    	response.sendRedirect(redirectURL);
		}

	};
%>

<%
	if(request.getParameter("aggiungiCamera")!= null)
	{	   
			String redirectURL = "aggiuntaCamera.jsp";
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
	<form name="inserisciLocazione" method="POST">
	<section id="inserimentoLocazione" class="bg-dark">
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Aggiungi una nuova struttura</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
        	<div class="row">
			<div class="panel panel-default">
  				<div class="panel-heading">
  					<div class="clearfix"></div>
  					<button class="btn btn-primary pull-right"  name="aggiungiCamera" type="submit"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Aggiungi Camera</button>
  					<h3 class="panel-title">Informazioni</h3>	
  					<div class="clearfix"></div>
  				</div>
  				<div class="panel-body">
  				<div class="row">
  					<div class="col-sm-4 col-sm-offset-1">
     					<h5>Nome</h5>
                    	<input type="text" id="nomeStruttura" name="nome" class="form-control" value="<%=strutturaBean.getNome()%>">
              		</div>
              		<div class="col-sm-4">
              			<h5>Tipologia Struttura</h5>
                    	<select name="tipologia" class="selectpicker" data-width="100%">
              				<option value="<%=strutturaBean.getTipologia() %>" disabled selected>Scegli</option>
  							<option value="Hotel">Hotel</option>
  							<option value="B&B">Bed&amp;Breakfast</option>
  							<option value="Agriturismo">Agriturismo</option>
						</select>
              		</div>
                  </div>
                  <br><br>
                  <div class="row">
              		<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Nazione</h5>
                    	<input type="text" id="nazioneStruttura" name="nazione" class="form-control" value="<%=strutturaBean.getNazione()%>">
              		</div>
              		<div class="col-sm-4">
              			<h5>Citta</h5>
                        <input type="text" name="citta" id="cittaStruttura" class="form-control" value="<%=strutturaBean.getCitta()%>">
                     </div>
              	  </div>
              	  <br><br>
              	  <div class="row">
              	  	<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Indirizzo</h5>
                    	<input type="text" id="indirizzoStruttura" name="indirizzo" class="form-control" value="<%=strutturaBean.getIndirizzo()%>">
              		</div>
              	  </div>
              	  <div class="row">
              	  	<div class="col-sm-10 col-sm-offset-1">
                    	<h5>Descrizione</h5>
                    	<input type="text" class="form-control" name="descrizione" id="descrizioneStruttura" value="<%=strutturaBean.getDescrizione()%>">
              		</div>
              	  </div>
              	  <br>
					<div class="panel-footer">	
					<button class="btn btn-primary pull-right"  name="confermaStruttura" type="submit">Conferma</button>
							<h4 class="panel-footer-title text-center">Camere aggiunte alla struttura</h4>	
									<div class="col s6"> 
					 					<ul class="collapsible" data-collapsible="accordion">
              							<% for(int i=0;i<strutturaBean.getCamere().size();i++)
      										{ 
        										if (!strutturaBean.getCamere().get(i).getNomeCamera().equals("")){
													%>
    													<li>
      														<div class="collapsible-header"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
      					 									<%=strutturaBean.getCamere().get(i).getNomeCamera()%></div>
															<div class="collapsible-body">
													<p>Tipologia: <%=strutturaBean.getCamere().get(i).getTipologiaCamera()%><br>
													Descrizione: <%=strutturaBean.getCamere().get(i).getDescrizioneCamera()%><br>
													Prezzo:	<%=strutturaBean.getCamere().get(i).getPrezzoCamera() %></p>
												</div>
      										</li>
 				 						<%
 				 						}}%>
 				 						</ul>
 				 					</div>
  					</div>
				 </div>
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
   
