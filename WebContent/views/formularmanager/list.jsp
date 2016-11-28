<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="list" class="sortimo.formularmanager.model.List"></jsp:useBean>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>

<div class="row margin-bottom-lg">
	<div class="col-xs-12 col-sm-4 col-md-2">
		<label for="list_country_selector">Land</label>
		<select id="list_country_selector" class="form-control">
			<option value="${pageContext.request.contextPath}/formularmanager/list?country=ALL&filter=${filter}">
				Alle
			</option>
			
			<c:forEach items="${conf.getcountrys()}" var="land">
				<option value="${pageContext.request.contextPath}/formularmanager/list?country=${land.key}&filter=${filter}" ${country == land.key ? 'selected' : ''}>
					${land.value}
				</option>
			</c:forEach>
		</select>
	</div>
</div>

<table class="table table-striped table-hover">
	<tr>
		<th>
			ID
		</th>
		<th>
			Titel
		</th>
		<th>
			Typ
		</th>
		<th>
			Land
		</th>
		<th class="text-right">
			Optionen			
		</th>
	</tr>
	<c:forEach items="${formsList}" var="item">
		<c:choose>
		   	<c:when test="${filter == 'active'}">
		   		<c:if test="${list.isActive(item.getFormMeta()['validFrom'], item.getFormMeta()['validTo'])}">
					<%@include file="/views/formularmanager/_listContent.tpl.jsp" %>
		   		</c:if>
		   	</c:when>
		   	<c:otherwise>
				<%@include file="/views/formularmanager/_listContent.tpl.jsp" %>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</table>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>

