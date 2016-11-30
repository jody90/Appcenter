$(document).ready(function() {

	var templateBckup;
	
	$('#changePasswordModal').on('show.bs.modal', function (e) {
		var toggledButton = $(e.relatedTarget);
		var username = toggledButton.attr("data-username");
		var body = $(this).find(".modal-body");
		var template = $(body).html();
		templateBackup = template;
		
		console.log(username);

    	// Platzhalter aus Template ersetzen
    	template = template.replace("####username####", username);

    	$(body).html(template);
	});
	
	$('#changePasswordModal').on('hide.bs.modal', function (e) {
		var body = $(this).find(".modal-body");
    	$(body).html(templateBackup);
	})
	
	$("#submitPasswordChange").on("click", function(e) {
		var form = $("#changePasswordForm");
		var password1 = $("#changePasswordForm input[name=password]").val();
		var password2 = $("#changePasswordForm input[name=password1]").val();
		
		if (password1.trim() && password2.trim() && password1 === password2) {
			$("#changePasswordForm").submit();
			$('#changePasswordModal').modal('hide');			
		}
		else {
			$("#changePasswordModal .notification").removeClass("hidden");
//			setTimeout(function() {
//				$("#changePasswordModal .notification").addClass("hidden");
//			},1500);
		}
		
		
	})
	
})