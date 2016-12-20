$.datetimepicker.setLocale('de');

$(document).ready(function() {
	$('.datetimepicker').datetimepicker({
		format:'Y-m-d H:i',
	});	
	
	console.log("Ready");

	$("#list_country_selector").on("change", function() {
		var url = $(this).val();
		window.location.href = url;
	})
})