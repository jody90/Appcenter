<tr>
	<td class="align-middle">
	    	${item.getId()}
    </td>
	<td class="align-middle">
		<a href="${pageContext.request.contextPath}/formularmanager/public?country=${item.getCountry()}&form_id=${item.getId()}">
	    	${item.getFormMeta()["formTitle"]}
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
 		<a class="list-option-link" href="${pageContext.request.contextPath}/formularmanager/statistics?country=${item.getCountry()}&form_id=${item.getId()}">
    		<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
    	</a>
    	
    	<span class="glyphicon glyphicon-send ${list.isActive(item.getFormMeta()['validFrom'], item.getFormMeta()['validTo']) ? 'form-active' : 'form-inactive'}" aria-hidden="true"></span>
    </td>
</tr>