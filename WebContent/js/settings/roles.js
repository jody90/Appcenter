$(document).ready(function() {

	var templateBckup;
	var templateDeleteBackup;
	
	$('#editRoleModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var roleId = toggledButton.attr("data-role-id");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateBackup = template;
		
		$.ajax({
	        type: "POST",
	        url: "index",
	        data: {
	            action: "getEditRoleData",
	            roleId: roleId
	        },
	        success: function(data) {
	        	var obj = JSON.parse(data);
	        	var role = obj[0];
	        	var roleRights = role.rights !== undefined ? role.rights.split(",") : [];
	        	var rights = obj[1];
	        	
	        	var orderedRights = orderArray(rights);
	        	
        		var possibleRights = "";
        		var currentRights = "";

        		roleId = role.id;
        		
        		for (var i = 0; i < rights.length; i++) {
        			var right_id = rights[i].id.toString();
	        		if (roleRights.indexOf(right_id) == -1) {
	        			var action = "add";
	        			var icon = "plus"
		        		var listItemTemplate = '<li data-id="####id####" class="list-group-item">####name#### <span data-action="'+action+'" class="action icon_right glyphicon glyphicon-'+icon+'" aria-hidden="true"></span></li>'
		        		listItemTemplate = listItemTemplate.replace("####id####", rights[i].id);
		        		listItemTemplate = listItemTemplate.replace("####name####", rights[i].name);
		        		possibleRights += listItemTemplate;
	        		}
	        		else {
	        			var action = "remove";
	        			var icon = "minus";
	        			var listItemTemplate = '<li data-id="####id####" class="list-group-item">####name#### <span data-action="'+action+'" class="action icon_right glyphicon glyphicon-'+icon+'" aria-hidden="true"></span></li>'
		        		listItemTemplate = listItemTemplate.replace("####id####", rights[i].id);
		        		listItemTemplate = listItemTemplate.replace("####name####", rights[i].name);
	        			currentRights += listItemTemplate;
	        		}
        		}
        		
	        	var name = role.name != undefined ? role.name : "";
	        	var description = role.description != undefined ? role.description : "";
	        	var roleId = role.id != undefined ? role.id : -1;

	        	// Platzhalter aus Template ersetzen
	        	template = template.replace("####name####", name);
	        	template = template.replace("####description####", description);
	        	template = template.replace("####roleId####", roleId);
	        	template = template.replace("####possibleRights####", possibleRights);
	        	template = template.replace("####currentRights####", currentRights);

	        	$(body).html(template);
	        	
	        	// Aktionen um Rechte und Rollen hinzuzufuegen und zu entfernen
	        	$(".list-group").on("click", ".action", function() {
	        		var action = $(this).attr("data-action");
	        		var id  = $(this).parent("li").attr("data-id");
	        		var listElement = $(this).parent("li").clone();
	        		var currentList = $(this).parent().parent().attr("id");
	        		
	        		$(this).parent("li").remove();
	        		
	        		var configInvert = {
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
	
	$('#editRoleModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateBackup);
	})
	
	$("#submitRoleChanges").on("click", function() {
		var rights = [];
		var possibleRights = [];
		
		$("#currentRights li").each(function(key, value) {
			var rightId = $(value).attr("data-id"); 
			rights.push(rightId);
		})
		
		$("#possibleRights li").each(function(key, value) {
			var rightId = $(value).attr("data-id"); 
			possibleRights.push(rightId);
		})
		
		$("#newRights").val(JSON.stringify(rights));
		$("#oldRights").val(JSON.stringify(possibleRights));
		
		$("#editRoleForm").submit();
		$('#editRoleModal').modal('hide');
	})
	
	$('#deleteRoleModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var roleId = toggledButton.attr("data-role-id");
		var name = toggledButton.attr("data-role-name");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateDeleteBackup = template;
		
		template = template.replace(/####name####/g, name);
		template = template.replace(/####roleId####/g, roleId);
		
		$(body).html(template);
		
	});
	
	$('#deleteRoleModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateDeleteBackup);
	})
	
	$("#submitRoleDelete").on("click", function() {
		$("#deleteRoleForm").submit();
		$('#deleteRoleModal').modal('hide');
	});
	
})

function orderArray(data) {
	var ordered = [];
	for (var i = 0; i < data.length; i++) {
		ordered[data[i].id] = data[i];
	}
	return ordered;
}