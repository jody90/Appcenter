<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="content">
	<div class="row margin-bottom-lg">
		<div class="col-xs-12 text-center">
			<h1>
				${formData['form_title']}
			</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12 text-center">
			<form method="post" action="public" class="public-form">
				<input type="hidden" name="form_id" value="${formId}">
				
				${formData['formContentHtml']}

				<br>
				<button class="btn btn-success margin-top-md" type="submit" name="action" value="save">
					Abschicken
				</button>
			</form>
		</div>
	</div>

</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
