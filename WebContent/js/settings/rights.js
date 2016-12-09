$(document).ready(function() {

	var templateBckup;
	var templateDeleteBackup;
	
	$('#editRightModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var rightName = toggledButton.attr("data-right-name");
		var rightDescription = toggledButton.attr("data-right-description");
		var rightId = toggledButton.attr("data-right-id");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateBackup = template;

    	var name = rightName != undefined ? rightName : "";
    	var description = rightDescription != undefined ? rightDescription : "";
    	var id = rightId != undefined ? rightId : -1;

    	// Platzhalter aus Template ersetzen
    	template = template.replace("####name####", name);
    	template = template.replace("####description####", description.trim());
    	template = template.replace("####rightId####", id);

    	$(body).html(template);

    	if (rightName !== undefined) {
    		$("#rightName").attr("readonly", "readonly");
    	}
	});
	
	$('#editRightModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateBackup);
	})
	
	$("#submitRightChanges").on("click", function() {	
		$("#editRightForm").submit();
		$('#editRightModal').modal('hide');
	})
	
	$('#deleteRightModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var rightId = toggledButton.attr("data-right-id");
		var name = toggledButton.attr("data-right-name");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateDeleteBackup = template;
		
		template = template.replace(/####name####/g, name);
		template = template.replace(/####rightId####/g, rightId);
		
		$(body).html(template);
		
	});
	
	$('#deleteRightModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateDeleteBackup);
	})
	
	$("#submitRightDelete").on("click", function() {
		$("#deleteRightForm").submit();
		$('#deleteRightModal').modal('hide');
	});
	
})