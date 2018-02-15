<%@page import="exception.UserException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "entity.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "controller.*" %>
<%@ page import = "exception.*" %>
<%@ page isErrorPage="true" import="java.io.*"%>

<jsp:useBean id="utenteBean" scope="session" class="bean.UtenteBean" />


<!-- Java Code -->
 <% boolean affittuario = utenteBean.getTipologia().equals("Affittuario"); 
   boolean gestore = utenteBean.getTipologia().equals("Gestore");%> 

<%
	if(request.getParameter("tornaAffittuario") != null)
	{
		String redirectURL = "affittuario.jsp";
	    response.sendRedirect(redirectURL);
	}
	if(request.getParameter("tornaGestore") != null)
	{
		String redirectURL = "gestore.jsp";
	    response.sendRedirect(redirectURL);
	}
	if(request.getParameter("tornaIndex") != null)
	{
		String redirectURL = "index.jsp";
	    response.sendRedirect(redirectURL);
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
                 <%if (affittuario) {
		            %>
		            <a class="navbar-brand page-scroll" href="affittuario.jsp">Vacanze Romane</a>
		            <%}
		            else{%>
		            <a class="navbar-brand page-scroll" href="gestore.jsp">Vacanze Romane</a>
		            <%}%>
		    </div>
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            </div>
        </div>
    </nav>

	<!-- Central Body -->
	<form name="tornaIndietro" method="POST">
	<section id="errori" class="bg-dark">
	<div class="container">
	<div class="row">
		<div class="col-lg-12 text-center">
			<h2 class="section-heading">Errore</h2>
			<hr class="primary">
		</div>
		 <div class="container">
            <div class="row">
            	<!-- Exception Handler -->
            	<% if (!((exception instanceof UserException) ||(exception instanceof StrutturaException) || (exception instanceof PrenotazioneException)))
            	{ %>
            	<div class="col-lg-12 text-center">
            		<h2 class="text-muted">Il Sistema ha avuto un comportamento anomalo</h2>
            		<% exception.printStackTrace();%>
            	</div>
            	<%}
            	else{ %>
            	<div class="col-lg-12 text-center">
            		<h2 class="text-muted"><%=exception.getMessage() %></h2>
            	</div>
            	<%} %>
            </div>
            <div class="row">
            <div class="col-lg-12 text-center">
            	<br><br>
            	<%if(affittuario && utenteBean.isLog()) {%>
				<button name="tornaAffittuario" id="tornaAffittuario" type="submit" class="btn btn-primary btn-xl page-scroll"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Torna alla pagina precedente</button>
				<%}
            	else if(gestore && utenteBean.isLog()) {%>
  				<button name="tornaGestore" id="tornaGestore" type="submit" class="btn btn-primary btn-xl page-scroll"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Torna alla pagina precedente</button>
  				<%}
            	else{%>
            	<button name="tornaIndex"  id="tornaIndex" type="submit" class="btn btn-primary btn-xl page-scroll"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Torna alla pagina precedente</button>
            	<%} %>
           	</div>
            </div>
          </div>
	</div>
	</div>
	</section>
	</form>
	
	<!-- Servizi -->
	<section id="services">
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


</body>
</html>