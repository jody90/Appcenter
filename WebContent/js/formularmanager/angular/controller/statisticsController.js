app.controller('statisticsController', function($scope, $http, $sce, $rootScope, $routeParams) {

	var formId = $("#form_id").attr("data-form-id");
	$scope.responseId = null;
	var obj = null;
	
	var routeChangeTimestamp = null;
	var now = Date.now();

	$scope.init = function() {
		if (obj === null) {
			var formId = window.location.search.match(/(form_id=)(\d*)/)[2];
			console.info("init fired");
			getStatistics(formId);
		}
	}
	
	// Beim initialen Laden auch feuern
	var responseId = $routeParams.id;
	if (responseId !== undefined && obj === null && (routeChangeTimestamp === null || now - routeChangeTimestamp > 5)) {
		console.log("route init fired");
		routeChangeTimestamp = now;
		viewForm(responseId, obj);
	}


	// Bei jedem routeChange feuern
	$rootScope.$on('$routeChangeSuccess', function(event, next, current) {
		var responseId = next.params.id;
		if (responseId !== undefined && obj === null && (routeChangeTimestamp === null || now - routeChangeTimestamp > 5)) {
			routeChangeTimestamp = now;
			viewForm(responseId, obj);
			console.log("route change fired");
		}
	});

	function isJson(str) {
		try {
			JSON.parse(str);
		} catch (e) {
			return false;
		}
		return true;
	}

	$("body").on("click", "#saveProcessedFormSubmit", function(event) {
		event.preventDefault();

		var state = $("#saveProcessedForm").find("select[name='state']").val();

		$http({
			method : "GET",
			url : "statistics",
			params : {
				action : "saveProcessed",
				formId : formId,
				state : state,
				processedBy : $scope.user.username,
				responseId : $scope.responseId
			},

		})
		.then(function successCallback(response) {
			showNotification("Der Datensatz wurde erfolgreich upgedated", "success", "Speichern erfolgreich");
		}, function errorCallback(response) {
			showNotification("Der Datensatz wurde nicht upgedated", "error", "Speichern nicht erfolgreich");
		});
	});

	function getStatistics(formId, responseId) {

		console.info("getStatistics fired");
		
		$http({
			method : "GET",
			url : "statistics?action=getStatistics&form_id=" + formId
		}).then(function(response) {

			obj = response.data;

			if (obj.resultsJson != "null") {
				Object.keys(obj).map(function(key, index) {
					if (isJson(obj[key])) {
						return obj[key] = JSON.parse(obj[key]);
					}
				});
			}
			
			$scope.formTitle = obj.formTitle;
			$scope.user = obj.user;
			$scope.states = obj.states;
			$scope.meta = obj.meta;
			$scope.respondedForms = obj.resultsJson;
			$scope.htmlForm;
			$scope.orderByField = 'username';
			$scope.reverseSort = false;

			if (responseId !== undefined) {
				viewForm(responseId, obj);
			}
		})
	}

	function viewForm(responseId, obj) {
		
		if (obj === null) {
			getStatistics(formId, responseId);
			return false;
		}
		
		$scope.htmlForm = "";
		$scope.responseId = responseId;

		var resultsObj = JSON.parse(obj.resultsJson[responseId].value);

		var htmlForm = '<div id="formReadyIndicator">';
		htmlForm += obj.formHtml;
		htmlForm += '</div>';

		$scope.form = obj.resultsJson[responseId];

		$("#formReadyIndicator").ready(function() {

			$("#formReadyIndicator").find(":checkbox").attr("disabled", true);
			$("#formReadyIndicator").find(":radio").attr("disabled", true);
			$("#formReadyIndicator").find("[type='select']").attr("disabled", true);

			$.each(resultsObj, function(key, value) {
				var element = $("#formReadyIndicator").find("[name='" + key + "']")[0].outerHTML;
				var type = $(element).attr("type");

				switch (type) {
					case "textarea":
					case "text":
					case "date":
						$("#formReadyIndicator").find("[name='" + key + "']").val(value).attr("readonly", true);
					break;
					case "checkbox":
					case "radio":
						$("#formReadyIndicator").find("[name='" + key + "'] [value='" + value + "']").attr("checked", true);
					break;
					case "select":
						$("#formReadyIndicator").find("[name='" + key + "'] [value='" + value + "']").attr("selected", true);
					break;
				}
			})
		})
		$scope.htmlForm = $sce.trustAsHtml(htmlForm);
	}
});
