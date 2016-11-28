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
</div>
<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
