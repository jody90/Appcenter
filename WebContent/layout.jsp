<jsp:useBean id="HelperFunctions" class="sortimo.model.HelperFunctions"></jsp:useBean>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sortimo.model.HelperFunctions" %>
<%
	HelperFunctions helper = new HelperFunctions();
	request.setAttribute("helper", helper);
	helper.setRequest(request);
	helper = null;
%>
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
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    
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
     			<c:if test="${user['firstname'] != null}">
					<div class="padding-top-xs inlineblock">
						Hallo ${user['firstname']}
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
					<li class="dropdown primary-nav-element">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							Formular Manager <span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<c:if test='${helper.isAuthorized("new_form")}'>
								<li>
									<a href="${pageContext.request.contextPath}/formularmanager/edit">
										Neues Formular
									</a>
								</li>
							</c:if>
							<c:if test='${helper.isAuthorized("form_overview")}'>
								<li>
									<a href="${pageContext.request.contextPath}/formularmanager/list">
										Übersicht
									</a>
								</li>
							</c:if>
							<c:if test='${helper.isAuthorized("boss_view")}'>
								<li>
									<a href="${pageContext.request.contextPath}/formularmanager/boss#listBossForms">
										Boss View
									</a>
								</li>
							</c:if>
						</ul>
					</li>
					
					<li class="primary-nav-element">
						<a href="${pageContext.request.contextPath}/settings/">
							Nutzerverwaltung
						</a>
					</li>
					<li class="primary-nav-element">
						<a href="#">
							Anderes Tool
						</a>
					</li>
				</ul>
			</div>
		</nav>

		<jsp:include page="views/${path}/${view}.jsp"></jsp:include>
	</div>
</body>
</html>