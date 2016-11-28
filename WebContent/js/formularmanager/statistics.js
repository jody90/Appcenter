$(document).ready(function() {
	var formId = $("#form_id").attr("data-form-id");
    $.ajax({
        type: "POST",
        url: "statistics",
        data: {
            action: "getStatistics",
            form_id: formId
        },
        success: function(data) {
        	
        	// Response String aufbereiten
        	var obj = JSON.parse(data);
        	
        	if (obj.resultsJson != "null") {
       
	        	Object.keys(obj).map(function(key, index) {
	        		return obj[key] = JSON.parse(obj[key]);
	    		});
	        	
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
        	else {
        		var html = '<h1>Keine Daten vorhanden, deshalb ist leider keine Auswertung m&ouml;glich</h1>';
        		$("#chart_area").html(html);
        		console.log("keine Auswertung");
        	}
        },
        error: function(data) {
        	console.log("error", data);
        }
    });
})