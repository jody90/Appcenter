$(document).ready(function() {

	var processingRoles = $("#processingRoles").val();
	
	var processingRolesArray = isJson(processingRoles) ? JSON.parse(processingRoles) : [];
	
	// Labels Initial durchlaufen und aktiv Schalten
	for (var i = 0; i < processingRolesArray.length; i++) {
		$(".role-label-container").find("[data-role-id='" + processingRolesArray[i] + "']").addClass("active");
	}
	
	// Auf Klick von Labels reagieren
	$(".role-label").on("click", function() {
		var roleId = $(this).attr("data-role-id");
		var isActive = $(this).hasClass("active");
		
		if (isActive) {
			$(this).removeClass("active");
			for (var i = 0; i < processingRolesArray.length; i++) {
				if (processingRolesArray[i] === roleId) {
					processingRolesArray.splice(i, 1);
					setProcessingRights(processingRolesArray);
				}
			}
		}
		else {
			$(this).addClass("active");
			if (processingRolesArray.indexOf(roleId) === -1) {
				processingRolesArray.push(roleId);
				setProcessingRights(processingRolesArray);
			}
		}
	})
})

function isJson(str) {
	try {
		JSON.parse(str);
	} catch (e) {
		return false;
	}
	return true;
}

function setProcessingRights(data) {
	var jsonData = JSON.stringify(data);
	$("#processingRoles").val(jsonData);
}