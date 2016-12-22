app.controller('statisticsController', function($scope, $http, $sce, $rootScope, $routeParams) {

	var formId = $("#form_id").attr("data-form-id");
	var responseId = $routeParams.id;
	var obj = null;
	var routeChangeTimestamp = null;
	
	$scope.addNoteObject = {};
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
		$scope.dateString = date.toLocaleDateString() + " " + date.toLocaleTimeString();
	}

	$("body").on("click", "#saveProcessedFormSubmit", function(event) {
		event.preventDefault();
		
		console.info("saveForm fired");
		
		var state = $("#saveProcessedForm").find("select[name='state']").val();

		var noteInfo = $("#saveProcessedForm").find("#noteInfo").html();
		var notes = noteInfo + $scope.notes;
		
		$http({
			method : "GET",
			url : "statistics",
			params : {
				action : "saveProcessed",
				formId : formId,
				state : state,
				processedBy : $scope.user.username,
				responseId : $scope.responseId,
				notes : notes,
			},
		})
		.then(function successCallback(response) {
			showNotification("Der Datensatz wurde erfolgreich upgedated", "success", "Speichern erfolgreich");
			$scope.addNoteObject.addNote = "";
			$scope.notes = notes;
			$scope.notesHtml = $sce.trustAsHtml($scope.notes) ;
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

			if (obj.respondedForms != "null") {
				Object.keys(obj).map(function(key, index) {
					if (isJson(obj[key])) {
						return obj[key] = JSON.parse(obj[key]);
					}
				});
			}
			
			console.info("Object", obj);
			
			$scope.formData = obj.formData;
			$scope.user = obj.user;
			$scope.states = obj.states;
			$scope.respondedForms = obj.respondedForms;
			$scope.htmlForm;
			$scope.orderByField = 'username';
			$scope.reverseSort = false;
			
			if (responseId !== undefined) {
				$scope.notes = $scope.respondedForms[responseId].notes;
				$scope.notesHtml = $sce.trustAsHtml($scope.notes) ;
				viewForm(responseId, obj);
			}
			
			if (obj.formData.evaluationType === "chart") {
					
				if (isJson(obj.formData.formContentJson)) {
					// vorhandene FormularElemente aus Formular extrahieren (RadioGroups, SelectBoxen, etc.)
		        	var formElements = {};

	        		var formJson = JSON.parse(obj.formData.formContentJson);
	        	
		        	formJson.map(function(value, key) {
		        		if (value.values !== undefined) {
		        			formElements[value.name] = value.values;
		        		}
		        	});
					
		        	var chartConfigData = {};
		        	chartConfigData.columns = {};
		        	
		        	getFormResponseElements(obj.respondedForms)
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
		        		$.each(obj.respondedForms, function(key, answers) {
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
				else {
					showNotification("Es sind leider keine Daten vorhanden", "error", "Keine Daten");
				}
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
		
		var resultsObj = JSON.parse(obj.respondedForms[responseId].value);
		
		// Formular leer in Seite rendern
		var htmlForm = '<div id="formReadyIndicator">';
		htmlForm += obj.formData.formContentHtml;
		htmlForm += '</div>';

		$scope.form = obj.respondedForms[responseId];

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
