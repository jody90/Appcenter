<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>

<div ng-app="app">
	<div>

		<h1 class="text-center margin-bottom-md">
			{{formData.formTitle}}
		</h1>

		<div ng-view></div>

	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>