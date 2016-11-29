$(document).ready(function() {

	var templateBckup;
	var templateDeleteBackup;
	
	$('#editUserModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var username = toggledButton.attr("data-username");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateBackup = template;

		$.ajax({
	        type: "POST",
	        url: "index",
	        data: {
	            action: "getEditUserData",
	            editUser: username
	        },
	        success: function(data) {
	        	var obj = JSON.parse(data);
	        	
	        	console.log(obj);
	        	
	        	var userData = obj[0];
	        	var roles = obj[1];
	        	var rights = obj[2];
	        	
	        	var orderedRoles = orderArray(roles);
	        	var orderedRights = orderArray(rights);
	        	
	        	var userRights = userData.rights != undefined ? userData.rights.split(",") : [];
	        	var userRoles = userData.roles != undefined ? userData.roles.split(",") : [];

	        	var typeSpecific = {};
	        	typeSpecific.right = userRights;
	        	typeSpecific.role = userRoles;
	        	
	        	// Erzeugt Listen Elemente fuer Rechte und Rollen
	        	function createListElement(data, type, action, specificData) {
	        		var icon = action == "add" ? "plus" : "minus";
	        		var specificData = specificData || false;
	        		var typeSpecificArray = typeSpecific[type];
	        		
	        		var listItemTemplate = '<li data-id="####id####" class="list-group-item">####name#### <span data-type="'+type+'" data-action="'+action+'" class="action icon_right glyphicon glyphicon-'+icon+'" aria-hidden="true"></span></li>'
	        		var listHtml = "";
	        		
	        		for (var i = 0; i < data.length; i++) {
	        			var elementData = specificData != false ? specificData[data[i]] : data[i];
	        			if (!specificData) {
	        				var id = (data[i].id).toString();
	    	        		if (typeSpecificArray.indexOf(id) == -1) {
	    	        			var listItem = listItemTemplate.replace("####name####", data[i].name);
	    	        			listItem = listItem.replace("####id####", data[i].id);
	    	        			listHtml += listItem;
	    	        		}
	        			}
	        			else {
		        			var listItem = listItemTemplate.replace("####name####", elementData.name);
		        			listItem = listItem.replace("####id####", elementData.id);
		        			listHtml += listItem;
	        			}
	        		}
	        		return listHtml;
	        	}
	        	
	        	// Templatedaten fuer Rechte und Rollen generieren
	        	var userRightsListHtml = createListElement(userRights, "right", "remove", orderedRights);
	        	var userRolesListHtml = createListElement(userRoles, "role", "remove", orderedRoles);
	        	var rightsListHtml = createListElement(rights, "right", "add");
	        	var rolesListHtml = createListElement(roles, "role", "add");
	        	
	        	var username = userData.username != undefined ? userData.username : "";
	        	var firstame = userData.firstname != undefined ? userData.firstname : "";
	        	var lastname = userData.lastname != undefined ? userData.lastname : "";
	        	var email = userData.email != undefined ? userData.email : "";

	        	// Platzhalter aus Template ersetzen
	        	template = template.replace("####username####", username);
	        	template = template.replace("####firstname####", firstame);
	        	template = template.replace("####lastname####", lastname);
	        	template = template.replace("####email####", email);
	        	template = template.replace("####possibleRoles####", rolesListHtml);
	        	template = template.replace("####possibleRights####", rightsListHtml);
	        	template = template.replace("####currentRoles####", userRolesListHtml);
	        	template = template.replace("####currentRights####", userRightsListHtml);

	        	$(body).html(template);
	        	
	        	// Aktionen um Rechte und Rollen hinzuzufuegen und zu entfernen
	        	$(".list-group").on("click", ".action", function() {
	        		var action = $(this).attr("data-action");
	        		var type = $(this).attr("data-type");
	        		var id  = $(this).parent("li").attr("data-id");
	        		var listElement = $(this).parent("li").clone();
	        		var currentList = $(this).parent().parent().attr("id");
	        		
	        		$(this).parent("li").remove();
	        		
	        		var configInvert = {
        				"possibleRoles" : "currentRoles",
        				"currentRoles" : "possibleRoles",
        				"possibleRights" : "currentRights",
        				"currentRights" : "possibleRights"
	        		};
	        		
	        		var actionInvert = action == "add" ? "remove" : "add";
	        		var icon = action == "add" ? "glyphicon-plus" : "glyphicon-minus"
	        		var iconInvert = action == "add" ? "glyphicon-minus" : "glyphicon-plus";
	        		var boxInvert = configInvert[currentList];
	        		
	        		$(listElement).find("span").attr("data-action", actionInvert);
	        		$(listElement).find("span").removeClass(icon);
	        		$(listElement).find("span").addClass(iconInvert);
	        		
	        		$("#" + boxInvert).append(listElement);
	        	})
	        }
	    })
	})
	
	$('#editUserModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateBackup);
	})
	
	$("#submitUserChanges").on("click", function() {
		var roles = [];
		var rights = [];
		
		$("#currentRoles li").each(function(key, value) {
			var roleId = $(value).attr("data-id"); 
			roles.push(roleId);
		})
		
		$("#currentRights li").each(function(key, value) {
			var rightId = $(value).attr("data-id"); 
			rights.push(rightId);
		})
		
		$("#newRights").val(JSON.stringify(rights));
		$("#newRoles").val(JSON.stringify(roles));
		
		$("#editUserForm").submit();
		$('#editUserModal').modal('hide');
	})
	
	
	$('#deleteUserModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var username = toggledButton.attr("data-username");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateDeleteBackup = template;
		
		template = template.replace(/####username####/g, username);
		
		$(body).html(template);
		
	});
	
	$('#deleteUserModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateDeleteBackup);
	})
	
	$("#submitUserDelete").on("click", function() {
		$("#deleteUserForm").submit();
		$('#deleteUserModal').modal('hide');
	});
	
})

function orderArray(data) {
	var ordered = [];
	for (var i = 0; i < data.length; i++) {
		ordered[data[i].id] = data[i];
	}
	return ordered;
}