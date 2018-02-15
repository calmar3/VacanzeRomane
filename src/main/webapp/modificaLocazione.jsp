<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>

<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="strutturaBean" scope="request" class="bean.StrutturaBean" /> 
<jsp:useBean id="catalogoBean" scope="session" class="bean.CatalogoBean" />



<%session.setAttribute("cameraBean", null); %>

<%
	if(request.getParameter("indiceCamera") != null)
	{
		GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
		controllerLocazioni.rimuoviCamera(Integer.valueOf(request.getParameter("indice")),Integer.valueOf(request.getParameter("indiceCamera")),
				catalogoBean,utenteBean);
		String redirectURL = "modificaLocazione.jsp?indice="+request.getParameter("indice");
		response.sendRedirect(redirectURL);
		
		
	}
%>  

<jsp:setProperty name="strutturaBean" property="*" />
<% if(request.getParameter("confermaModifica") != null)
	{
		Integer indice = Integer.valueOf(request.getParameter("indice"));
		if(strutturaBean.modificaStruttura(indice, utenteBean, catalogoBean))
		{
			String redirectURL = "gestore.jsp";
		    response.sendRedirect(redirectURL);
		}
	}%>
	

	
<%
	if(request.getParameter("indice") != null)
	{
		Integer indice = Integer.valueOf(request.getParameter("indice"));
		strutturaBean = catalogoBean.getStrutture().get(indice);
	
	}
%>  

<%
	if(request.getParameter("aggiungiCamera") != null)
	{
		GestoreLocazioni controllerLocazioni = new GestoreLocazioni();
		controllerLocazioni.impostaStruttura(Integer.valueOf(request.getParameter("indice")),utenteBean);
		String redirectURL = "aggiuntaCamera.jsp?ritorno=modificaLocazione&indice="+request.getParameter("indice");
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
	
	<!-- Locazione -->
	<form name="modificaLocazione" method="POST">
	<section id="locazione" class="bg-dark">
	<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Modifica Locazione: <%=strutturaBean.getNome() %></h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
        <div class="panel panel-default">
        		<div class="panel-heading">
  					<div class="clearfix"></div>
        			<button type="submit" name="confermaModifica" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Conferma modifica</button>
  					<h3 class="panel-title">Informazioni</h3>	
  					<div class="clearfix"></div>
  				</div>
  				<div class="panel-body">
					<div class="row">
  					<div class="col-sm-4 col-sm-offset-1">
     					<h5>Nome</h5>
                    	<input type="text" id="nome" name="nome" class="form-control" value="<%=strutturaBean.getNome()%>">
              		</div>
              		<div class="col-sm-4">
              			<h5>Tipologia Struttura</h5>
                    	<input type="text" id="tipologia" name="tipologia" class="form-control" value="<%=strutturaBean.getTipologia()%>">
					</div>
                  </div>
                  <br><br>
                  <div class="row">
              		<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Nazione</h5>
                    	<input type="text" id="nazione" name="nazione" class="form-control" value="<%=strutturaBean.getNazione()%>">
              		</div>
              		<div class="col-sm-4">
              			<h5>Citta</h5>
                        <input type="text" name="citta" id="citta" class="form-control" value="<%=strutturaBean.getCitta()%>">
                     </div>
              	  </div>
              	  <br><br>
              	  <div class="row">
              	  	<div class="col-sm-4 col-sm-offset-1">
                    	<h5>Indirizzo</h5>
                    	<input type="text" id="indirizzo" name="indirizzo" class="form-control" value="<%=strutturaBean.getIndirizzo()%>">
              		</div>
              	  </div>
              	  <div class="row">
              	  	<div class="col-sm-10 col-sm-offset-1">
                    	<h5>Descrizione</h5>
                    	<input type="text" class="form-control" name="descrizione" id="descrizione" value="<%=strutturaBean.getDescrizione()%>">
              		</div>
              	  </div><br><br>
              	  <div class="row">
              	  	<div class="col-sm-10">
              	  	<button type="submit" name="aggiungiCamera" class="btn btn-primary pull-left"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Aggiungi Camera</button>
              	  	 </div>
              	  </div>
              	  <br>
					<div class="panel-footer">	
						<h4 class="panel-footer-title text-center">Camere della struttura</h4>	
									<div class="col s6"> 
					 					<ul class="collapsible" data-collapsible="accordion">
              							<% for(int i=0;i<strutturaBean.getCamere().size();i++)
      										{ 
        										if (!strutturaBean.getCamere().get(i).getNomeCamera().equals("")){
													%>
    													<li>
      														<div class="collapsible-header">
      														<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
      														<a href ="modificaCamera.jsp?indice=<%=request.getParameter("indice") %>&indiceCamera=<%=i %>" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Modifica Camera</a>
    														<%=strutturaBean.getCamere().get(i).getNomeCamera()%></div>
      					 									<div class="collapsible-body">
													<p>Tipologia: <%=strutturaBean.getCamere().get(i).getTipologiaCamera()%><br>
													<a href ="modificaLocazione.jsp?indice=<%=request.getParameter("indice") %>&indiceCamera=<%=i %>" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Elimina Camera</a>
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
   	