app.controller('statisticsChartController', function($scope, $http, $rootScope) {

	console.info("statisticsChartController");
	
	var formId = $rootScope.formId;
	
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
		
		$scope.formData = obj.formData;
		$scope.respondedForms = obj.respondedForms;
		
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
	})
	
});
