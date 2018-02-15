<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page errorPage="paginaErrore.jsp" %>

<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />
<jsp:useBean id="strutturaBean" scope="session" class="bean.StrutturaBean" /> 
<jsp:useBean id="cameraBean" scope="session" class="bean.CameraBean" />
<jsp:useBean id="lineaPrenotazioneBean" scope="request" class="bean.LineaPrenotazioneBean" />
<jsp:useBean id="prenotazioneBean" scope="session" class="bean.PrenotazioneBean" />
<jsp:useBean id="catalogoBean" scope="session" class="bean.CatalogoBean" />


<jsp:setProperty name="lineaPrenotazioneBean" property="*" />
<%
	if(request.getParameter("confermaLogout")!= null)
	{
		GestoreUtente.avviaControllerUtente().logout(utenteBean);
		session.setAttribute("utenteBean", null);
		String redirectURL = "index.jsp";
	    response.sendRedirect(redirectURL);
	};
%>


<%  if(request.getParameter("indice")!=null && utenteBean !=null)
	{
	if(catalogoBean.getStrutture()!=null )
		if (!catalogoBean.getStrutture().isEmpty())
		{
			Integer indice = Integer.valueOf(request.getParameter("indice"));
			strutturaBean = catalogoBean.getStrutture().get(indice);

		}
	}
%>

<%  if(request.getParameter("indiceCamera")!=null)
	{
		Integer indiceCamera = Integer.valueOf(request.getParameter("indiceCamera"));
		cameraBean = strutturaBean.getCamere().get(indiceCamera);
	}
%>  	


<%
	String[] serviziPremium;

	if((serviziPremium = request.getParameterValues("checkServizioPremium")) != null)
	{
	   lineaPrenotazioneBean.aggiungiServiziScelti(cameraBean.getServizi(), serviziPremium);
	}
	else 
		lineaPrenotazioneBean.aggiungiServiziScelti(cameraBean.getServizi(),null);
	if(request.getParameter("aggiungiCarrello")!=null)
	{
		GestorePrenotazioni controllerPrenotazioni= new GestorePrenotazioni(); 
		if(controllerPrenotazioni.prenotaCamera(strutturaBean,utenteBean,prenotazioneBean, cameraBean,lineaPrenotazioneBean))
		{
			String redirectURL = "prenotaLocazione.jsp?indice=" + request.getParameter("indice");
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
                	<li>
                		<a class="page-scroll" href="#ricerca"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
                	</li>
                	<li>
                		<li><a href="carrello.jsp"><span class="glyphicon glyphicon-shopping-cart	" aria-hidden="true"></span></a>
                	</li>
                    <li>
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
	<form name="lineaForm" method="POST">	
	<section class="bg-dark" id="confermaPrenotazione">
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Prenotazione</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
        	<div class="row">
			<div class="panel panel-default">
  				<div class="panel-heading">
  					<h3 class="panel-title"><%= cameraBean.getNomeCamera()%></h3>	
  				</div>
  				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<p>Tipologia: <%=cameraBean.getTipologiaCamera()%>	</p>
						</div>
						<div class="col-sm-6">
							<p>Descrizione: <%=cameraBean.getDescrizioneCamera()%>	</p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<p>Prezzo Base: <%= cameraBean.getPrezzoCamera()%></p>
						</div>
						
					</div>
				</div>
			</div>
			</div>
			<div class="row">
			<div class="panel panel-default">
				<div class="panel-heading">
  					<h3 class="panel-title">Scegli servizi</h3>	
  				</div>
				<div class="panel-body">
				<div class="row">
					<div class="col-sm-11 col-lg-offset">
     					<%if (cameraBean.getServizi().size()>0) 
	    				{%>
	    				<table class="table">
				    		<thead>
			          		<tr>
			              		<th data-field="nome">Servizio</th>
			              		<th data-field="descrizione">Descrizione</th>
			              		<th data-field="prezzo">Prezzo</th>
			              		<th data-field="scegli">Scegli</th>
			          		</tr>
				        	</thead>
					    	<%for (int i=0;i<cameraBean.getServizi().size();i++){ %>
					    	<tbody>
			          			<tr>
			      				<td><%=cameraBean.getServizi().get(i).getNomeServizio() %></td>
			            		<td><%=cameraBean.getServizi().get(i).getDescrizioneServizio()%></td>
			            		<%if (cameraBean.getServizi().get(i).getTipologiaServizio().equals("0")){ %>
			            			<td>-</td>
			            			<td>
									<div class="checkbox checked">
  										<label><input type="checkbox" name="checkServizioBase" value="<%=cameraBean.getServizi().get(i).getNomeServizio() %>" disabled></label>
									</div>
								</td>
			            		<%}
			            		else 
			            		{%>
			          			<td><%=cameraBean.getServizi().get(i).getPrezzoServizio() %></td>
			            	    <td>
			        			<div class="checkbox">
  									<label><input type="checkbox" name="checkServizioPremium" value="<%=cameraBean.getServizi().get(i).getNomeServizio()%>"></label>
								</div>
								</td>
								<%} %>
			          			</tr>
			        		</tbody>
					    	<%} %>	
				    	</table>
				    	<%}%>
			            </div>
			        </div>
			    <div class="row">
                <div class='col-sm-5 col-lg-offset-1'>
				      <div class="form-group">
                		<div class='input-group' data-provide='datepicker'>
                    		<input type='text' class="form-control datepicker" id="checkIn" name="checkIn" placeholder="Data Check-In" />
                    		<span class="input-group-addon">
                        		<span class="glyphicon glyphicon-calendar"></span>
                    		</span>
						</div>
            		</div>
        		</div>
        		<div class='col-sm-5'>
				      <div class="form-group">
                		<div class='input-group' data-provide='datepicker'>
                    		<input type='text' class="form-control datepicker" id="checkOut" name="checkOut" placeholder="Data Check-Out" />
                    		<span class="input-group-addon">
                        		<span class="glyphicon glyphicon-calendar"></span>
                    		</span>
						</div>
            		</div>
        		</div>
        		</div>
				<div class="panel-footer">
					<div class="row">
			              <button class="btn btn-primary pull-right" name="aggiungiCarrello" type="submit"><span class="glyphicon glyphicon-ok"  aria-hidden="true"></span> Aggiungi al carrello</button>
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
	var dateToday = new Date();
  		$(document).ready(function()
  			{
  				$('.selectpicker').selectpicker({
  			  		style: 'btn-info',
  			  		size: 4
  					});
  				$.fn.datepicker.defaults.format = "dd/mm/yyyy";
  				$('.datepicker').datepicker({
  					 minDate: dateToday
  				});
  			});
  		
  	</script>

</body>
</html>
	
