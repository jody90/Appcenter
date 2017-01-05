<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>
<jsp:useBean id="helper" class="sortimo.model.HelperFunctions"></jsp:useBean>


<div class="row">
	
	<div class="col-xs-12 col-md-9">
		<div class="statistics-form" ng-bind-html="htmlForm"></div>
	</div>

	<div class="col-xs-12 col-md-3">
		<form action="" method="post" id="saveProcessedForm">
		<input type="hidden" ng-value="{{meta.country}}" name="country">
		<input type="hidden" ng-value="{{meta.formId}}" name="formId">
		
			<div class="form-statistics-meta margin-bottom-lg" ng-if="controller !== 'boss'">
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
						<button type="submit" name="action" value="saveProcessed" class="btn btn-success btn-lg margin-top-lg" ng-click="saveProcessedForm()">
							Speichern
						</button> 
					</div>
				</div>
			</div>

			<div class="form-statistics-meta">
				
				<label for="addNote">
					Notiz hinzufügen
				</label>
				<textarea class="notes" id="addNoteContainer" name="addNote" rows="4" placeholder="Notiz hinzufügen" ng-change="triggerNewNoteEdit(addNoteObject.addNote, user.username)" ng-model="addNoteObject.addNote"></textarea>
				
				<label for="notes" class="margin-top-md">
					Notizen
				</label>
				<div class="textarea border" id="notesTextarea">
					<span ng-if="addNoteObject.addNote.length > 0">
						<div id="noteInfo">
							<span class="noteDate">
								{{dateString}} | {{user.username}} : 
							</span>
							{{addNoteObject.addNote}} <br> 
						</div>
					</span>
					<span ng-bind-html="notesHtml">
					</span>
				</div>
			</div>
			
			<div class="form-statistics-meta margin-top-lg" ng-if="controller === 'boss'">
				<h3 class="margin-top-0">Genehmigung erteilen?</h3>
				<div class="row">
					<div class="col-xs-6">
						<div class="btn btn-success button-boss-approval btn-block" ng-click="saveBossDecision(1)">
							<i class="fa fa-thumbs-up" aria-hidden="true"></i>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="btn btn-danger button-boss-approval btn-block" ng-click="saveBossDecision(0)">
							<i class="fa fa-thumbs-down" aria-hidden="true"></i>
						</div>
					</div>
				</div>
			</div>
			
		</form>		
	</div>
</div>