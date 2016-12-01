<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div ng-app="app">
	<div ng-controller="statisticsController">
	
		<h1 class="text-center">
			{{formTitle}}
		</h1>
		
		<div ng-view></div>
		
		<div id="form_id" data-form-id="${formId}"></div>
		<div class="row" id="chart_area"></div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>