<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tr>
	<td class="align-middle">
    	${item.getId()}
    </td>
	<td class="align-middle">
		<a href="${pageContext.request.contextPath}/formularmanager/public?country=${item.getCountry()}&form_id=${item.getId()}">
	    	${item.getFormTitle()}
		</a>
    </td>
	<td class="align-middle">
    	${item.getType()}
    </td>
    <td class="align-middle">
    	<img src="${pageContext.request.contextPath}/images/formularmanager/${item.getCountry()}.png" alt="country_flag" title="${item.getCountry()}" width="25">
    </td>
	<td class="align-middle text-right">
    	<a class="list-option-link" href="${pageContext.request.contextPath}/formularmanager/edit?action=edit&country=${item.getCountry()}&form_id=${item.getId()}">
    		<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
    	</a>
	    <a class="list-option-link" href="${pageContext.request.contextPath}/formularmanager/edit?action=delete&country=${item.getCountry()}&form_id=${item.getId()}">
    		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
    	</a>
    	<c:if test="${item.getEvaluationType() == 'chart'}">
	 		<a class="list-option-link" href="${pageContext.request.contextPath}/formularmanager/statistics?country=${item.getCountry()}&form_id=${item.getId()}#showChart/${item.getId()}">
	    		<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
	    	</a>
    	</c:if>
	    <c:if test="${item.getEvaluationType() != 'chart'}">
	 		<a class="list-option-link" href="${pageContext.request.contextPath}/formularmanager/statistics?country=${item.getCountry()}&form_id=${item.getId()}#listForms/${item.getId()}">
	    		<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
	    	</a>
    	</c:if>
    	<span class="glyphicon glyphicon-send ${list.isActive(item.getValidFrom(), item.getValidTo()) ? 'form-active' : 'form-inactive'}" aria-hidden="true"></span>
    </td>
</tr>