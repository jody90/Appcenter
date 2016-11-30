<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form action="index" method="get" class="margin-bottom-md">
	<label for="editRole">Name Rolle</label>
	<input type="text" class="form-control search_field" name="editRole" placeholder="Rolle">
	<button type="submit" class="btn btn-success" name="action" value="manageRoles">
		Suchen
	</button>
</form>

<button class="btn btn-primary margin-bottom-lg" data-toggle="modal" data-target="#editRoleModal" id="openNewRoleModal">
	Neue Rolle anlegen
	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
</button>

<table class="table table-hover">
	<tr>
		<th>
			ID
		</th>
		<th>
			Name
		</th>
		<th>
			Beschreibung
		</th>
		<th>
			Optionen
		</th>
	</tr>
	<c:forEach items="${roles}" var="role">
		<tr>
			<td class="align-middle">
				${role.getId()}
			</td>
			<td class="align-middle">
				${role.getName()}
			</td>
			<td class="align-middle">
				${role.getDescription()}
			</td>
			<td class="align-middle">
		    	<span class="settings-edit-link" data-toggle="modal" data-target="#editRoleModal" data-role-id="${role.getId()}" id="openEditRoleModal">
		    		<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
		    	</span>		    	
 			    <span class="settings-delete-link" data-toggle="modal" data-target="#deleteRoleModal" data-role-id="${role.getId()}" data-role-name="${role.getName()}" id="openDeleteRoleModal">
		    		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
		    	</span>		    
			</td>
		</tr>	
	</c:forEach>
</table>

<!-- Edit Role Modal -->
<div class="modal fade" id="editRoleModal" tabindex="-1" role="dialog"
	aria-labelledby="editRoleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editRoleModalLabel">Rolle verwalten</h4>
			</div>
			<div class="modal-body">
			
				<form action="index" method="post" id="editRoleForm">
					<input type="hidden" name="action" value="saveEditRole">
					<input type="hidden" name="rights" value="" id="newRights">
					<input type="hidden" name="oldRights" value="" id="oldRights">
					<input type="hidden" name="roleId" value="####roleId####">
					
					<div class="row">
						<div class="col-xs-6">
							<label for="name">Name Rolle</label>
							<input class="form-control" type="text" name="name" value="####name####" placeholder="Name">
						</div>					
						<div class="col-xs-6">
							<label for="description">Beschreibung Rolle</label>
							<textarea class="description" name="description">####description####</textarea>
						</div>
					</div>
										
					<div class="row margin-top-lg">
						<div class="flexbox">
							<div class="panel panel-info roleEditPanel">
								<div class="panel-heading">
									Benutzerrechte verfügbar: 
								</div>
								<div class="panel-body">
									<ul class="list-group" id="possibleRights">								
										####possibleRights####
									</ul>
								</div>
							</div>

							<div class="panel panel-success userEditPanel">
								<div class="panel-heading">
									Benutzerrechte aktuell:
								</div>
								<div class="panel-body">
									<ul class="list-group" id="currentRights">
										####currentRights####
									</ul>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
				<button type="button" class="btn btn-primary" id="submitRoleChanges">Speichern</button>
			</div>
		</div>
	</div>
</div>

<!-- Delete Role Modal -->
<div class="modal fade" id="deleteRoleModal" tabindex="-1" role="dialog"
	aria-labelledby="deleteRoleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="deleteRoleModalLabel">Rolle löschen</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="index" id="deleteRoleForm">
					<input type="hidden" name="action" value="deleteRole">
					<input type="hidden" name="roleId" value="####roleId####">
					<p>
						Bist du dir sicher, dass du <strong>####name####</strong> löschen willst?
					</p>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Nein</button>
				<button type="button" class="btn btn-danger" id="submitRoleDelete">Ja</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
