$(document).ready(function() {

	var processingRoles = $("#processingRoles").val();
	
	console.log(processingRoles);
	
	var processingRolesArray = processingRoles != undefined ? processingRoles.split(",") : [];
	
	console.log("processingRolesArray", processingRolesArray);
	
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
	var roleData = data.toString();
	$("#processingRoles").val(roleData);
}