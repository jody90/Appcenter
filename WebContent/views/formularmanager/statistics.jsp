<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>

<div ng-app="app">
	<div ng-controller="statisticsController">
			
		<div ng-view></div>
		
		<div id="form_id" data-form-id="${formId}"></div>
		<div class="row" id="chart_area"></div>
		<!--
		-->
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>