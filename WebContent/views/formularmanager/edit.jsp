<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>
<jsp:useBean id="help" class="sortimo.model.HelperFunctions"></jsp:useBean>

<div class="row">
	<div class="col-xs-3 padding-md">
		<form action="edit" method="post">
		
			<input type="hidden" name="form_id" value="${formId}">

			<div class="form-group">
				<label for="form_title">Formular Titel</label>
				<input class="form-control" type="text" name="meta_formTitle" value="${formData['formTitle']}" placeholder="Formular Titel">
			</div>
			
			<div class="margin-bottom-md">
				<label for="formType">Formular Type</label>
				<select name="formType" class="form-control">
					<c:forEach items="${conf.getTypes()}" var="type">
						<option value="${type.key}" ${formData['formType'] == type.key ? 'selected' : ''}>
							${type.value}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="margin-bottom-md">
				<div class="switch-field">
					<strong>Abteilungsleiter Freigabe erforderlich: </strong><br>
					<input type="radio" id="switch_left" name="meta_bossApprovalRequired" value="yes" ${formData['bossApprovalRequired'] == 'yes' ? 'checked' : ''} />
					<label for="switch_left">Ja</label>
					<input type="radio" id="switch_right" name="meta_bossApprovalRequired" value="no" ${formData['bossApprovalRequired'] != 'yes' ? 'checked' : ''} />
					<label for="switch_right">Nein</label>
				</div>
			</div>	
			
			<div class="margin-bottom-md">
				<label for="meta_processingRoles">Bearbeitende Rollen </label><br>
				<input type="hidden" name="meta_processingRoles" value='${formData["processingRoles"]}' id="processingRoles">
				<div class="role-label-container">
					<c:forEach items="${roles}" var="role">
						<span class="label label-default role-label" data-role-id="${role.getId()}">
							${role.getName()}
						</span>
					</c:forEach>
				</div>
			</div>

			<div class="margin-bottom-md">
				<label for="country">Land</label>
				<select name="country" class="form-control">
					<c:forEach items="${conf.getCountrys()}" var="land">
						<option value="${land.key}" ${country == land.key ? 'selected' : ''}>
							${land.value}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="margin-bottom-md">
				<label for="meta_evaluationType">Typ der Auswertung </label>
				<select name="meta_evaluationType" class="form-control">
					<c:forEach items="${conf.getEvaluationTypes()}" var="evaluationType">
						<option value="${evaluationType.key}" ${formData['evaluationType'] == evaluationType.key ? 'selected' : ''}>
							${evaluationType.value}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group">
				<label for="meta_validFrom">Gültig von:</label>
				<input class="form-control datetimepicker" type="text" name="meta_validFrom" value="${formData['validFrom']}" placeholder="01.01.2016 12:15">
			</div>
			
			<div class="form-group">
				<label for="meta_validTo">Gültig bis:</label>
				<input class="form-control datetimepicker" type="text" name="meta_validTo" value="${formData['validTo']}" placeholder="01.01.2016 10:30">
			</div>
			
			<textarea rows="10" cols="45" name="meta_formContentHtml" class="hidden" id="form_content_html"></textarea>
			
			<input type="hidden" name="meta_formContentJson" value='${formData["formContentJson"]}' id="form_content_json">
		
			<button class="btn btn-success save-form" type="submit" name="action" value="save">
				Formular speichern
			</button>
					
		</form>
	</div>
	<div class="col-xs-9">
		<div id="fb-editor"></div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>

