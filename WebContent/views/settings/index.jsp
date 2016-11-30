<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row settings_menu text-center">
  	<div class="col-md-6 col-md-offset-3 settings_menu_link_container">
		<a href="${pageContext.request.contextPath}/settings/index?action=manageUsers" class="settings_menu_link">
			<span class="glyphicon glyphicon-user settings_menu_icon" aria-hidden="true"></span>
			Benutzer verwalten
		</a>
  	</div>
 	<div class="col-md-6 col-md-offset-3 settings_menu_link_container">
		<a href="${pageContext.request.contextPath}/settings/index?action=manageRoles" class="settings_menu_link">
			<span class="glyphicon glyphicon-certificate settings_menu_icon" aria-hidden="true"></span>
			Rollen verwalten
		</a>
  	</div>
  	<div class="col-md-6 col-md-offset-3 settings_menu_link_container">
		<a href="${pageContext.request.contextPath}/settings/index?action=manageRights" class="settings_menu_link">
			<span class="glyphicon glyphicon-object-align-horizontal settings_menu_icon" aria-hidden="true"></span>
			Rechte verwalten
		</a>
  	</div>
 	<div class="col-md-6 col-md-offset-3 settings_menu_link_container">
    	<span class="settings_menu_link" data-toggle="modal" data-target="#changePasswordModal" data-username="${username}">
    		<span class="glyphicon glyphicon-refresh settings_menu_icon" aria-hidden="true"></span>
			Passwort ändern
    	</span>		
  	</div>
</div>

<!-- Change Password Modal -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog"
	aria-labelledby="changePasswordModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="changePasswordModalLabel">Passwort ändern</h4>
			</div>
			<div class="modal-body">
				<div class="notification hidden margin-bottom-md">
		    		<span class="glyphicon glyphicon-alert" aria-hidden="true"></span>
					Die Passwörter stimmen nicht überein.			
				</div>
			
				<form action="index" method="post" id="changePasswordForm">
					<input type="hidden" name="action" value="changePassword">
					<input type="hidden" name="editUser" value="####username####">
					
					<div class="row">
						<div class="col-xs-12">
							<label for="password">Neues Passwort</label>
							<input class="form-control" type="password" name="password" value="" placeholder="Neues Passwort">
						</div>					
						<div class="col-xs-12 margin-top-md">
							<label for="description">Neues Passwort wiederholen</label>
							<input class="form-control" type="password" name="password1" value="" placeholder="Neues Passwort wiederholen">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
				<button type="button" class="btn btn-primary" id="submitPasswordChange">Speichern</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
