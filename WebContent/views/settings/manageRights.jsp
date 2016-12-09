<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form action="index" method="get" class="margin-bottom-md">
	<label for="editRight">Name Recht</label>
	<input type="text" class="form-control search_field" name="editRight" placeholder="Recht">
	<button type="submit" class="btn btn-success" name="action" value="manageRights">
		Suchen
	</button>
</form>

<button class="btn btn-primary margin-bottom-lg" data-toggle="modal" data-target="#editRightModal" id="openNewRightModal">
	Neues Recht anlegen
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
	<c:forEach items="${rights}" var="right">
		<tr>
			<td class="align-middle">
				${right.getId()}
			</td>
			<td class="align-middle">
				${right.getName()}
			</td>
			<td class="align-middle">
				${right.getDescription()}
			</td>
			<td class="align-middle">
		    	<span class="settings-edit-link" data-toggle="modal" data-target="#editRightModal" data-right-id="${right.getId()}" data-right-name="${right.getName()}" data-right-description="${right.getDescription()}"id="openEditRightModal">
		    		<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
		    	</span>		    	
 			    <span class="settings-delete-link" data-toggle="modal" data-target="#deleteRightModal" data-right-id="${right.getId()}" data-right-name="${right.getName()}" id="openDeleteRightModal">
		    		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
		    	</span>		    
			</td>
		</tr>	
	</c:forEach>
</table>

<!-- Edit Right Modal -->
<div class="modal fade" id="editRightModal" tabindex="-1" role="dialog"
	aria-labelledby="editRightModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editRightModalLabel">Recht verwalten</h4>
			</div>
			<div class="modal-body">
			
				<form action="index" method="post" id="editRightForm">
					<input type="hidden" name="action" value="saveEditRight">
					<input type="hidden" name="rightId" value="####rightId####">
					
					<div class="row">
						<div class="col-xs-6">
							<label for="name">Name Recht</label>
							<input class="form-control" type="text" id="rightName" name="name" value="####name####" placeholder="Name">
						</div>					
						<div class="col-xs-6">
							<label for="description">Beschreibung Recht</label>
							<textarea class="description" name="description">####description####</textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
				<button type="button" class="btn btn-primary" id="submitRightChanges">Speichern</button>
			</div>
		</div>
	</div>
</div>

<!-- Delete Right Modal -->
<div class="modal fade" id="deleteRightModal" tabindex="-1" role="dialog"
	aria-labelledby="deleteRightModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="deleteRightModalLabel">Recht löschen</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="index" id="deleteRightForm">
					<input type="hidden" name="action" value="deleteRight">
					<input type="hidden" name="rightId" value="####rightId####">
					<p>
						Bist du dir sicher, dass du <strong>####name####</strong> löschen willst?
					</p>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Nein</button>
				<button type="button" class="btn btn-danger" id="submitRightDelete">Ja</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
