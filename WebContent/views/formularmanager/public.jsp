<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="content">
	<div class="row margin-bottom-lg">
		<div class="col-xs-12 text-center">
			<h1>
				${formData['form_title']}
			</h1>
		</div>
	</div>
	
	<form method="post" action="public" class="public-form">
		<input type="hidden" name="form_id" value="${formId}">
		<c:if test="${empty bossStorageList}">
			<input type="hidden" name="bossApproved" value="1">
		</c:if>
			
		<c:if test="${not empty bossStorageList}">
			<div class="alert alert-info" role="alert">
				Dieses Formular erfordert die Freigabe durch deinen Vorgesetzten. Bitte wähle Ihn aus der Liste aus.
			</div>
			<div class="row">
				<div class="col-xs-12 margin-bottom-lg">
					<label for="boss">Vorgesetzten auswählen: </label>
					<select name="boss" class="form-control selectbox-boss">
						<c:forEach items="${bossStorageList}" var="boss">
							<option value="${boss.username}">
								${boss.lastname} ${boss.firstname}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</c:if>
	
		<div class="row">
			<div class="col-xs-12">
					
				${formData['formContentHtml']}

				<br>

				<button class="btn btn-success btn-block margin-top-lg" type="submit" name="action" value="save">
					Abschicken
				</button>
			</div>
		</div>	
	</form>

</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
