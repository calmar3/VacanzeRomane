<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>


<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="strutturaBean" scope="session" class="bean.StrutturaBean" /> 
<jsp:useBean id="catalogoBean" scope="session" class="bean.CatalogoBean" /> 
<jsp:useBean id="cameraBean" scope="session" class="bean.CameraBean" />
<jsp:useBean id="servizioBean" scope="request" class="bean.ServizioBean" />
<jsp:setProperty name="servizioBean" property="*" />
<jsp:setProperty name="cameraBean" property="*"/> 


<!-- Java Code -->

<%if(request.getParameter("indice")!=null)
	{
		Integer indice = Integer.valueOf(request.getParameter("indice"));
		strutturaBean = catalogoBean.getStrutture().get(indice);
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
<% 

		if(request.getParameter("confermaCamera")!= null)
		{ 
			if(cameraBean.inserisciCamera(strutturaBean,utenteBean))
				{
					String redirectURL = "inserimentoLocazione.jsp";
					if(request.getParameter("ritorno")!=null)
					{
						if (request.getParameter("ritorno").equals("modificaLocazione"))
							redirectURL = "modificaLocazione.jsp?indice="+request.getParameter("indice");
					}
	   				response.sendRedirect(redirectURL);

				}
		};
		
%>


<% 
		if(request.getParameter("confermaServizio")!= null)
			cameraBean.aggiungiServizio(servizioBean);
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
	
	<!-- Camera -->
	<form name="inserisciCamera" method="POST" autocomplete="off">
	<section id="inserimentoCamera" class="bg-dark">
	
	<!-- Servizio Base/Premium Popup -->
	<div id="modalServizio" class="modal fade forget-modal-servizio" tabindex="-1" role="dialog" aria-labelledby="myServiceModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
        	<div class="modal-content">
            	<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                 		<span aria-hidden="true">×</span>
                    	<span class="sr-only">Close</span>
                </button>
                <input type="hidden" id="tipologiaServizioInput" name="tipologiaServizio">
                <h4 class="modal-title">Aggiungi un nuovo servizio</h4>
           	</div>
           	<div class="modal-body">
            	<div class="form-group">
                <h5>Nome:</h5>
                   	<input type="text" id="nomeServizio" name="nomeServizio" class="form-control" placeholder="Nome Servizio">
              	</div>
              	<div class="form-group">
                   	<h5>Descrizio Servizio:</h5>
                   	<input type="text" id="descrizioneServizio" name="descrizioneServizio" class="form-control" placeholder="Descrizione">
              	</div>
              	<div class="form-group">
                    <h5>Prezzo Servizio:</h5>
                    <input type="number" name="prezzoServizio" id="prezzoServizio" class="form-control" placeholder="0">
                </div>
            </div>
            <div class="modal-footer">
                	<input type="submit" id="btn-login" class="btn btn-custom " name="confermaServizio" value="Conferma">
            </div>
         </div> 
    	</div>
	</div> 
	
	
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Aggiungi una nuova camera</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
        	<div class="row">
			<div class="panel panel-default">
  				<div class="panel-heading">
  					<div class="clearfix"></div>
    				<a class="forget btn btn-primary pull-right" data-toggle="modal" data-target=".forget-modal-servizio" onclick="javascript:setTipologiaServizio(0);" href="#modalServizio"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Servizio Base</a>
    				<a class="forget btn btn-primary pull-right" data-toggle="modal" data-target=".forget-modal-servizio" onclick="javascript:setTipologiaServizio(1);" href="#modalServizio"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Servizio Premium</a>
    				<h3 class="panel-title">Informazioni</h3>	
  					<div class="clearfix"></div>
  				</div>
  				<div class="panel-body">
  				<div class="row">
  					<div class="col-sm-5 col-sm-offset-1">
     					<h5>Nome</h5>
                    	<input type="text" id="nomeCamera" name="nomeCamera" class="form-control" value="<%=cameraBean.getNomeCamera()%>">
              		</div>
              		<div class="col-sm-5">
              			<h5>Tipologia Camera</h5>
                    	<select name="tipologiaCamera" class="selectpicker" data-width="100%">
              				<option value="<%=cameraBean.getTipologiaCamera() %>" disabled selected>Scegli</option>
  							<option value="Camera Singola">Camera Singola</option>
  							<option value="Camera Matrimoniale">Camera Matrimoniale</option>
  							<option value="Camera Tripla">Camera Tripla</option>
						</select>
              		</div>
                  </div>
                  <br><br>
                  <div class="row">
              		<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Prezzo</h5>
                    	<input type="number" id="prezzoCamera" name="prezzoCamera" class="form-control" value="<%=cameraBean.getPrezzoCamera()%>">
              		</div>
              	  </div>
              	  <br><br>
              	  <div class="row">
              	  	<div class="col-sm-10 col-sm-offset-1">
                    	<h5>Descrizione</h5>
                    	<input type="text" class="form-control" name="descrizioneCamera" id="descrizioneCamera" value="<%=cameraBean.getDescrizioneCamera()%>">
              		</div>
              	  </div>
              	  <br>
					<div class="panel-footer">	
							<button class="btn btn-primary pull-right"  name="confermaCamera" type="submit">Conferma</button>
							<h4 class="panel-footer-title text-center">Servizi aggiunti alla camera</h4>
							<div class="col s8"><ul>
							<% for(int i=0;i<cameraBean.getServizi().size();i++)
      							{ 
        							if (!cameraBean.getServizi().get(i).getNomeServizio().equals("")){
										%>

									<li>
          								<span class="glyphicon glyphicon-tags" aria-hidden="true"></span>      Nome Servizio: <%=cameraBean.getServizi().get(i).getNomeServizio()%><br>
          								Tipologia Servizio: 
          								<% 
          								if(cameraBean.getServizi().get(i).getTipologiaServizio().equals("0"))
          								{%> Base<br><br> <%}
          								else{%> Premium <br>
          									Prezzo: <%=cameraBean.getServizi().get(i).getPrezzoServizio() %> 
    								<br><br></li>
    					<%}}} %>
    					</ul></div>
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
  			});
  		
  		 function setTipologiaServizio(tipologiaNum) {
 			
 			var inputHiddenTipologia = document.getElementById("tipologiaServizioInput");
 			inputHiddenTipologia.value = tipologiaNum;
 			var prezzoInputServizio = document.getElementById("prezzoServizio");
 			if(tipologiaNum == 0)
 				prezzoInputServizio.readOnly = true;
 			else prezzoInputServizio.readOnly = false;
 			$('#modalServizio').openModal();
 		};
  	</script>


</body>
</html>
   
