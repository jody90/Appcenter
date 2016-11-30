<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="index" method="get" class="margin-bottom-md">
	<label for="editUser">Benutzername</label>
	<input type="text" class="form-control search_field" name="editUser" placeholder="Benutzername">
	<button type="submit" class="btn btn-success" name="action" value="manageUsers">
		Suchen
	</button>
</form>

<button class="btn btn-primary margin-bottom-lg" data-toggle="modal" data-target="#editUserModal" id="openNewUserModal">
	Neuen Benutzer anlegen
	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
</button>

<table class="table table-striped table-hover">
	<tr>
		<th>
			Benutzername
		</th>
		<th>
			Vorname
		</th>
		<th>
			Nachname
		</th>
		<th>
			Email
		</th>
		<th>
			Optionen
		</th>
	</tr>
	<c:forEach items="${users}" var="user">
		<tr>
			<td class="align-middle">
				${user["username"]}
			</td>
			<td class="align-middle">
				${user["firstname"]}		
			</td>
			<td class="align-middle">
				${user["lastname"]}
			</td>
			<td class="align-middle">
				${user["email"]}
			</td>
			<td class="align-middle">
		    	<span class="settings-edit-link" data-toggle="modal" data-target="#editUserModal" data-username="${user['username']}" id="openEditUserModal">
		    		<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
		    	</span>		    	
 			    <span class="settings-delete-link" data-toggle="modal" data-target="#deleteUserModal" data-username="${user['username']}" id="openDeleteUserModal">
		    		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
		    	</span>		    
			</td>
		</tr>
	</c:forEach>
</table>

<!-- Edit User Modal -->
<div class="modal fade" id="editUserModal" tabindex="-1" role="dialog"
	aria-labelledby="editUserModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editUserModalLabel">Benutzer Rechte/Rollen verwalten</h4>
			</div>
			<div class="modal-body">
			
				<form action="index" method="post" id="editUserForm">
					<input type="hidden" name="action" value="saveEditUser">
					<input id="newRights" type="hidden" name="rights" value=''>
					<input id="newRoles" type="hidden" name="roles" value=''>
					<input id="oldName" type="hidden" name="oldUsername" value='####oldUsername####'>
					<div class="row margin-bottom-md">
						<div class="col-xs-6">
							<label for="username">Benutzername</label>
							<input class="form-control" type="text" name="username" value="####username####" placeholder="username">
						</div>
						<div class="col-xs-6">
							<label for="email">Email</label>
							<input class="form-control" type="text" name="email" value="####email####" placeholder="Email">
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-6">
							<label for="firstname">Vorname</label>
							<input class="form-control" type="text" name="firstname" value="####firstname####" placeholder="Vorname">
						</div>
						<div class="col-xs-6">
							<label for="lastname">Nachname</label>
							<input class="form-control" type="text" name="lastname" value="####lastname####" placeholder="Nachname">
						</div>
					</div>
					
					<div class="row margin-top-lg">
						<div class="flexbox">

							<div class="panel panel-info userEditPanel">
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
					
					<div class="row">
						<div class="flexbox">
							<div class="panel panel-info userEditPanel">
								<div class="panel-heading">
									Benutzerrollen verfügbar:
								</div>
								<div class="panel-body">
									<ul class="list-group" id="possibleRoles">
										####possibleRoles####
									</ul>
								</div>
							</div>
							<div class="panel panel-success userEditPanel">
								<div class="panel-heading">
									Benutzerrollen aktuell:
								</div>
								<div class="panel-body">
									<ul class="list-group" id="currentRoles">
										####currentRoles####
									</ul>
								</div>
							</div>
						</div>
					</div>
					
				</form>
			
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
				<button type="button" class="btn btn-primary" id="submitUserChanges">Speichern</button>
			</div>
		</div>
	</div>
</div>

<!-- New User Modal -->
<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog"
	aria-labelledby="deleteUserModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="deleteUserModalLabel">Benutzer löschen</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="index" id="deleteUserForm">
					<input type="hidden" name="action" value="deleteUser">
					<input type="hidden" name="username" value="####username####">
					<p>
						Bist du dir sicher, dass du <strong>####username####</strong> löschen willst?
					</p>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Nein</button>
				<button type="button" class="btn btn-danger" id="submitUserDelete">Ja</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>



