<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div ng-app="app">
 	<div>
		<div ng-view></div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>

