app.controller('statisticsController', function($scope, $http, $sce, $rootScope, $routeParams) {

	var formId = $("#form_id").attr("data-form-id");
	var responseId = $routeParams.id;
	var obj = null;
	var routeChangeTimestamp = null;
	
	$scope.responseId = null;
	
	// Beim initialen Laden auch feuern
	if ((responseId !== undefined && obj === null && routeChangeTimestamp === null) && (Date.now() - routeChangeTimestamp) > 5) {
		routeChangeTimestamp = Date.now();
		viewForm(responseId, obj);
	}

	// Bei jedem routeChange feuern
	$rootScope.$on('$routeChangeSuccess', function(event, next, current) {
		var responseId = next.params.id;
		if ((responseId !== undefined || obj === null || routeChangeTimestamp === null) && (Date.now() - routeChangeTimestamp) > 50) {
			routeChangeTimestamp = Date.now();
			viewForm(responseId, obj);
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

	$scope.triggerNewNoteEdit = function(value, username) {
		var date = new Date();
		var tmpValue = date.toLocaleDateString() + " " + date.toLocaleTimeString() + ' | ' + username + ': ' + value;
    	$scope.notes = tmpValue;
	}

	$("body").on("click", "#saveProcessedFormSubmit", function(event) {
		event.preventDefault();
		
		console.info("saveForm fired");

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
			getStatistics(formId);
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
			
			if (obj.meta.evaluationType === "chart") {
					
				// vorhandene FormularElemente aus Formular extrahieren (RadioGroups, SelectBoxen, etc.)
	        	var formElements = {};
	        	
	        	obj.formJson.map(function(value, key) {
	        		if (value.values !== undefined) {
	        			formElements[value.name] = value.values;
	        		}
	        	});
				
	        	var chartConfigData = {};
	        	chartConfigData.columns = {};
	        	
	        	getFormResponseElements(obj.resultsJson)
	        	.then(function(formResponseElements) {
	
	        		// Arrays fuer die Chart API aufbereiten
	        		
	        		var answerCount = {};
	        		
	        		// Im Formular vorhandene Elemente (RadioGroups, Selectboxen, etc.)
	        		for (var i = 0; i < formResponseElements.length; i++) {
	        			var element = formResponseElements[i];
	        			if (formElements[element] !== undefined) {
	        				chartConfigData.columns[element] = formElements[element]; 
	        			}
	        		}
	        		
	        		// Response Werte der einzelnen Formular Elemente
	        		$.each(obj.resultsJson, function(key, answers) {
	        			var answerObj = JSON.parse(answers.value);
	
	        			$.each(answerObj, function(name, value) {
	        				if (chartConfigData.columns[name] !== undefined) {
		        				answerCount[name] = answerCount[name] === undefined ? {} : answerCount[name];
		        				
		        				if (answerCount[name][value] === undefined) {
		        					answerCount[name][value] = 1;
		        				}
		        				else {
		        					answerCount[name][value] = answerCount[name][value] + 1;
		        				}
	        				}
	        			})
	        			
	        			chartConfigData.rows = answerCount; 
	        			
	        		})
	        		
	        		// Load the Visualization API and the corechart package.
		        	google.charts.load('current', {'packages':['corechart']});
	        		// Set a callback to run when the Google Visualization API is loaded.
	        		
	        		google.charts.setOnLoadCallback(function() {
	        			drawChart(chartConfigData, "circle");
	    			});
	        	});
			}
		})
	}

	function viewForm(responseId, obj) {
		
		console.info("viewForm fired");
		
		if (obj === null) {
			getStatistics(formId, responseId);
			return false;
		}
		
		$scope.htmlForm = "";
		$scope.responseId = responseId;

		var resultsObj = JSON.parse(obj.resultsJson[responseId].value);
		
		// Formular leer in Seite rendern
		var htmlForm = '<div id="formReadyIndicator">';
		htmlForm += obj.formHtml;
		htmlForm += '</div>';

		$scope.form = obj.resultsJson[responseId];

		$("#formReadyIndicator").ready(function() {

			// alle eingabeFelder auf Readonly setzen
			$("#formReadyIndicator").find(":checkbox").attr("disabled", true);
			$("#formReadyIndicator").find(":radio").attr("disabled", true);
			$("#formReadyIndicator").find("[type='select']").attr("disabled", true);
			$("#formReadyIndicator").find("input, textarea").attr("readonly", true);

			// Formular mit Response befuellen
			$.each(resultsObj, function(key, value) {
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
			})
		})
		$scope.htmlForm = $sce.trustAsHtml(htmlForm);
	}
	
	$scope.translateState = function(state) {
		return $scope.states[state] !== undefined ? $scope.states[state] : state;
	}
	
});
