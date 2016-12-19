<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>

<div class="row">
	<div class="public-form col-sm-12 col-md-9" ng-bind-html="htmlForm"></div>
	<div class="form-statistics-meta col-sm-12 col-md-3">
		<div class="row">
			<div class="col-xs-12">
				<label for="username" class="fb-text-label">
					Bearbeiter
				</label>
				<input type="text" name="username" value="${user.getUsername()}" disabled="disabled" class="form-control">
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12">
			
				<select name="state" class="form-control">
					<c:forEach items="${conf.getStates()}" var="state">
						<option value="${state.getKey()}">
							${state.getValue()}
						</option>
					</c:forEach>
				</select>
			
			</div>
		</div>
	</div>
</div>


<div class="text-center">
	<div class="public-form" ng-bind-html="htmlForm"></div>
</div>