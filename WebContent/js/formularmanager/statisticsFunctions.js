// Enthaltene Formular Response Elemente extrahieren
var getFormResponseElements = function(resultsJson) {
	var FormElements = [];
    var deferred = new $.Deferred();
            		
	$.each(resultsJson, function(key, value) {
		var formElement = JSON.parse(value.value); 
		$.each(formElement, function(name, response) {
			if ($.inArray(name, FormElements) === -1) {
				FormElements.push(name);
			}
		})
		deferred.resolve(FormElements);
	})
	return deferred.promise();
}

var drawChart = function(chartConfigData, type) {
	
	var count = 0;
	
	$.each(chartConfigData.columns, function(name, column) {
		var dataTableArray = [[name, "Anzahl Stimmen"]];

		for(var i = 0; i < column.length; i++) {
			var valueName = column[i].value;
			var rowValue = chartConfigData.rows[name][valueName] !== undefined ? chartConfigData.rows[name][valueName] : 0; 
			
			dataTableArray.push([valueName, rowValue]);
		}
		
		switch (type) {
			case "circle" :
				drawCircleChart(dataTableArray, count);
			break;
			case "bar" :
				drawBarChart(dataTableArray, count);
			break;
		}
		
		count++;
	});
}
    
var drawCircleChart = function(rows, count) {

	var data = google.visualization.arrayToDataTable(rows);
	
	// Chart Optionen festlegen
	var options = {
		'title' : 'So verlief die Abstimmung',
		'width' : 400,
		'height' : 250,
		'is3D' : true
	};
	
	// Elemente erzeugen in die die Charts gerendert werden sollen
	var chartContainer = '<div class="col-xs-12 col-sm-4 chart_container" id="chart_' + count + '">hallo</div>';
	$("#chart_area").append(chartContainer);
	
	// Chart mit Daten und Optionen initialisieren
	var chart = new google.visualization.PieChart(document.getElementById("chart_" + count));
	chart.draw(data, options);
        
}

var drawBarChart = function(rows, count) {
	
	// Load the Visualization API Package
	google.charts.load('current', {packages: ['bar']});

	var data = google.visualization.arrayToDataTable(rows);
	
	// Chart Optionen festlegen
	var options = {
		'title' : 'So verlief die Abstimmung',
		'width' : 450,
		'height' : 350
	};
	
	// Elemente erzeugen in die die Charts gerendert werden sollen
	var chartContainer = '<div class="col-xs-12 col-sm-6 chart_container" id="chart_' + count + '">chart</div>';
	$("#chart_area").append(chartContainer);
	
	// Chart mit Daten und Optionen initialisieren
	
	var chart = new google.visualization.ColumnChart(document.getElementById("chart_" + count));
	chart.draw(data, options);

}