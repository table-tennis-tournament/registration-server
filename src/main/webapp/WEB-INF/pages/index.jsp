<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" ng-app="TTRegistration">
<head>
  <meta charset="utf-8">
  <title>TTV Ettlingen - Albgauturnier 2022</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet" media="screen" type="text/css"/>
  <link href="<c:url value="/resources/css/bootstrap-select.css"/>" rel="stylesheet" media="screen" type="text/css"/>
  <link href="<c:url value="/resources/css/typeahead.js-bootstrap.css"/>" rel="stylesheet" media="screen" type="text/css"/>
  <link href="<c:url value="/resources/css/bootstrap-datepicker.css"/>" rel="stylesheet" media="screen" type="text/css" />
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" media="screen" type="text/css" />



  <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

</head>

<body>
<div id="loader" class="transparent">
      <div>Loading...</div>
</div>
<div class="container" id="navbarContainer">
      <div class="row">
        <div class="col-md-12">
          <!-- Navigation bar -->
          <nav class="navbar navbar-default">
            <div class="navbar-header">
              <a class="navbar-brand">TTV Ettlingen</a>
            </div>
            <div class="navbar-collapse" >
              <ul id="navigationTabsList" class="nav navbar-nav">
                <li id="information"><a onclick="navigateToRequestedID('information');">Home</a></li>
                <li id="registration"><a onclick="navigateToRequestedID('registration');">Anmeldung</a></li>
                <li id="participants"><a onclick="navigateToRequestedID('participants');">Teilnehmer</a></li>
              </ul>
              <ul class="nav navbar-nav navbar-right" id="navbarRightToLogin">
                <li class="dropdown" id="menu1">
                 <a class="dropdown-toggle" data-toggle="dropdown" href="#menu1">
                   Login
                    <b class="caret"></b>
                 </a>
                 <div class="dropdown-menu">
                 <div id="loginDiv" >
                   <form style="margin:0px 0px;" accept-charset="UTF-8" action="/sessions" method="post" autocomplete="on">
                   <div style="margin:0;padding:0;display:inline">
                    <input name="authenticity_token" type="hidden" value="4L/A2ZMYkhTD3IiNDMTuB/fhPRvyCNGEsaZocUUpw40=" />
                   </div>
                     <fieldset class='textbox ' style="padding:10px; min-width:210px">
                        <input type="email" style="margin-top: 8px" class="form-control" id="loginEmailInput" placeholder="Username" name="email">
                        <input type="password" style="margin-top: 8px" class="form-control" id="loginPasswordInput" placeholder="Password" name="password">
                     </fieldset>
                   </form>
                    <a style="margin-left:10px" onclick="login();" class="btn btn-primary btn-sm">Log In &raquo;</a>
                 </div>
              </div>
              </li>
              </ul>
              <ul class="nav navbar-nav navbar-right" id="navbarRightWhenLoggedIn">
                <li>
                  <a><span style="margin-left:10px" id="username"></span></a>
                </li>
                <li>
                  <a href="#">
                    <span class="glyphicon glyphicon-log-out" onclick="logout()"></span>
                  </a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </div>
    
    <div class="container">
      <div class="panel panel-default col-md-12">
        <div class="panel-body">
          <div id="mainContentContainer">
            <noscript>
             <div class="alert alert-danger">
                <h3>Bitte Javascript aktivieren, da die Anmeldung ansonsten nicht funktioniert. Vielen Dank!</h3>
             </div>
            </noscript>
            <!-- Content will be shown in here -->
          </div>
          <treg-information></treg-information>
          <preregistration-overview id="preregistration-view"></preregistration-overview>
        </div>
      </div>
    </div>


    <treg-footer id="footer"></treg-footer>

  <!-- Other Javascript files and libraries -->
  <script src="<c:url value="/resources/js/ie/jquery-1.2.6.min.js"/>"></script>
  <script src="<c:url value="/resources/js/ie/jquery.ie6blocker.js"/>"></script>


  <script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>
  <script src="<c:url value="/resources/js/bootstrap/bootstrap.js"/>"></script>
  <script src="<c:url value="/resources/js/bootstrap/bootstrap-select.js"/>"></script>
  <script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js"/>"></script>
  <script src="<c:url value="/resources/js/bootstrap/bootstrap-typeahead.js"/>"></script>
  
  <script src="<c:url value="/resources/js/navigation.js"/>"></script>
  <script src="<c:url value="/resources/js/login.js"/>"></script>
  <script src="<c:url value="/resources/js/utility.js"/>"></script>
  <script src="<c:url value="/resources/js/register.js"/>"></script> 

  <!-- AngularJS -->
  <script src="<c:url value="/resources/js/angularjs/angular.min.js"/>"></script>
  <script src="<c:url value="/resources/js/angularjs/angular-sanitize.min.js"/>"></script>

  <!-- Application -->
  <script src="<c:url value="/resources/application/application.js"/>"></script>

  <!-- Services -->
  <script src="<c:url value="/resources/services/server-api.js"/>"></script>

  <!-- directives -->
  <script src="<c:url value="/resources/directives/treg-footer.js"/>"></script>
  <script src="<c:url value="/resources/directives/preregistration-overview.js"/>"></script>
  <script src="<c:url value="/resources/directives/treg-information.js"/>"></script>

  <!-- models -->
  <script src="<c:url value="/resources/models/home.js"/>"></script>

</body>
</html>
