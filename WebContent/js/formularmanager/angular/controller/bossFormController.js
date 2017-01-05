app.controller('bossFormController', function($scope, $http, $sce, $rootScope) {
	
	console.info("BossFormController");

	var formId = $rootScope.formId;
	var responseId = $rootScope.responseId;
	var obj = null;
	var routeChangeTimestamp = null;

	$scope.controller = "boss";
	
	$scope.addNoteObject = {};
	$scope.addNoteObject.addNote = "";
	$scope.responseId = null;

	// Formulardaten anfragen und aufbereiten
	$http({
		method : "GET",
		url : "statistics",
		params : {
			action : "getFormStatistics",
			form_id : $rootScope.formId,
			response_id : $rootScope.responseId,
		},
	}).then(function(response) {

		obj = response.data;

		if (obj.respondedForms != "null") {
			Object.keys(obj).map(function(key, index) {
				if (isJson(obj[key])) {
					return obj[key] = JSON.parse(obj[key]);
				}
			});
		}

		$scope.formData = obj.formData;
		$scope.respondedForms = obj.respondedForms;
		$scope.user = obj.user;
		$scope.htmlForm;
		$scope.orderByField = 'processState';
		$scope.reverseSort = true;

		viewForm(obj);
	})

	// Speichern
	$scope.saveBossDecision = function(bossDecision) {

		var tmpScopeNotes = $scope.notes !== undefined ? $scope.notes : "";
		var noteInfo = $("#saveProcessedForm").find("#noteInfo").html();
		var notes = noteInfo + tmpScopeNotes;

		$http({
			method : "GET",
			url : "boss",
			params : {
				action : "saveBossDecision",
				formId : formId,
				notes : notes,
				responseId : $scope.responseId,
				bossDecision : bossDecision,
			},
		})
		.then(function successCallback(response) {
			showNotification("Der Datensatz wurde erfolgreich upgedated", "success", "Speichern erfolgreich");
			$scope.addNoteObject.addNote = "";
			$scope.notes = notes;
			$scope.notesHtml = $sce.trustAsHtml($scope.notes);

			var href = window.location.origin + window.location.pathname + "#/listBossForms";
			window.location.href = href;
			
		}, function errorCallback(response) {
			showNotification("Der Datensatz wurde nicht upgedated", "error", "Speichern nicht erfolgreich");
		});
	};

	function viewForm(obj) {

		$scope.notes = obj.responseData.notes;
		$scope.notesHtml = $sce.trustAsHtml($scope.notes);

		$scope.htmlForm = "";
		$scope.responseId = responseId;

		var resultsObj = JSON.parse(obj.responseData.value);

		// Formular leer in Seite rendern
		var htmlForm = '<div id="formReadyIndicator">';
		htmlForm += obj.formData.formContentHtml;
		htmlForm += '</div>';

		$scope.form = obj.responseData;

		$("#formReadyIndicator").ready(function() {

			// alle eingabeFelder auf Readonly setzen
			$("#formReadyIndicator").find(":checkbox").attr("disabled", true);
			$("#formReadyIndicator").find(":radio").attr("disabled", true);
			$("#formReadyIndicator").find("[type='select']").attr("disabled", true);
			$("#formReadyIndicator").find("input, textarea").attr("readonly", true);

			// Formular mit Response befuellen
			$.each(resultsObj, function(key, value) {			
				if ($("#formReadyIndicator").find("[name='" + key + "']")[0] !== undefined) {

					var element = $("#formReadyIndicator").find("[name='" + key + "']")[0].outerHTML;
					var type = $(element).attr("type");

					switch (type) {
					case "textarea":
					case "text":
					case "date":
						$("#formReadyIndicator").find("[name='" + key + "']").val(value);
						break;
					case "checkbox":
					case "radio":
						$("#formReadyIndicator").find("[name='" + key + "'] [value='" + value + "']").attr("checked", true);
						break;
					case "select":
						$("#formReadyIndicator").find("[name='" + key + "'] [value='" + value + "']").attr("selected", true);
						break;
					}
				}
			})
		})
		$scope.htmlForm = $sce.trustAsHtml(htmlForm);
	}

	$scope.triggerNewNoteEdit = function(value, username) {
		var date = new Date();
		$scope.dateString = date.toLocaleDateString() + " " + date.toLocaleTimeString();
	}
	
});
