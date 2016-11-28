<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="form_id" data-form-id="${formId}"></div>

<div class="row" id="chart_area"></div>

<script src="${pageContext.request.contextPath}/js/formularmanager/statisticsFunctions.js"></script>
<script src="${pageContext.request.contextPath}/js/formularmanager/statistics.js"></script>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
