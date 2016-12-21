<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>
<jsp:useBean id="helper" class="sortimo.model.HelperFunctions"></jsp:useBean>


<div class="row">
	<h1 class="text-center">
		{{formTitle}}
	</h1>
	
	<div class="col-xs-12 col-md-9">
		<div class="statistics-form" ng-bind-html="htmlForm"></div>
	</div>

	<div class="col-xs-12 col-md-3">
		<form action="" method="post" id="saveProcessedForm">
		<input type="hidden" ng-value="{{meta.country}}" name="country">
		<input type="hidden" ng-value="{{meta.formId}}" name="formId">
		
			<div class="form-statistics-meta">
				<div class="row">
					<div class="col-xs-12">
						<label for="username" class="fb-text-label">
							Bearbeiter
						</label>
						<input type="text" name="username" value="{{user.username}}" disabled class="form-control">
					</div>
				</div>
	
				<div class="row margin-top-md">
					<div class="col-xs-12">
						<label for="state" class="fb-text-label">
							Status
						</label>
						<select name="state" class="form-control">
							<option ng-repeat="(key, state) in states" value="{{key}}" ng-selected="key == form.processState ? true : false">
								{{state}}
							</option>
						</select>
					</div>
				</div>
				
				<div class="row text-right">
					<div class="col-xs-12">
						<button type="submit" name="action" value="saveProcessed" class="btn btn-success btn-lg margin-top-lg" id="saveProcessedFormSubmit">
							Speichern
						</button> 
					</div>
				</div>
			</div>
			
			<div class="form-statistics-meta margin-top-lg">
				
				<label for="addNote">
					Notiz hinzufügen
				</label>
				<textarea class="notes" name="addNote" rows="4" placeholder="Notiz hinzufügen" ng-change="triggerNewNoteEdit(addNote, user.username)" ng-model="addNote"></textarea>
				<div class="text-right margin-top-md">
					<div id="addNote" class="btn btn-info">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						Notiz hinzufügen
					</div>
				</div>
				
				<label for="notes" class="margin-top-md">
					Notizen
				</label>
				<textarea class="notes" name="notes" rows="12" placeholder="Notizen" readonly ng-model="notes"></textarea>
			
			</div>
			
		</form>		
	</div>
</div>


<!-- 
<div class="text-center">
	<div class="public-form" ng-bind-html="htmlForm"></div>
</div>
 -->