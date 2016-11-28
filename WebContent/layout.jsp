<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="de">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>sortimo App Center</title>
	
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/helperClasses.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/jquery.datetimepicker.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/navi.css" rel="stylesheet">
    
	<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>
<body>
    <div class="container-fluid">
		<div class="pageheader row margin-bottom-0 text-center page_header">
            <div class="col-xs-6 col-sm-3 header-logo">
	            <a href="${pageContext.request.contextPath}/index">
	                <img src="${pageContext.request.contextPath}/images/sortimo-logo.png" title="sortimo" alt="sortimo logo" class="img-responsive">
	            </a>
            </div>
            <div class="hidden-xs col-sm-6 header-text">
                <h1>
                    ${pageTitle}
                </h1>
            </div>
            <div class="col-xs-6 col-sm-3 text-right">
     			<c:if test="${not empty firstname}">
					<div class="padding-top-xs inlineblock">
						Hallo ${firstname}
					</div>
				
					<a class="logout_link btn btn-warning" href="${pageContext.request.contextPath}/login?action=logout" title="logout">
						Logout
					</a>
		   		</c:if>
            </div>
        </div>

		<nav class="navbar navbar-default">
			<!-- Toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li>
						<a href="${pageContext.request.contextPath}/formularmanager/">
							Formular Manager
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/settings/">
							Nutzerverwaltung
						</a>
					</li>
					<li>
						<a href="#">
							Anderes Tool
						</a>
					</li>
					<!--<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Dropdown <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="#">Separated link</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					</li> --> 
				</ul>
			</div>
		</nav>

		<jsp:include page="views/${path}/${view}.jsp"></jsp:include>
	</div>
</body>
</html>