app.controller('statisticsController', function($scope, $http, $sce) {
	$scope.test = "hallo test";
	
	var formId = $("#form_id").attr("data-form-id");

	
    $http({
        method: "GET",
        url: "statistics?action=getStatistics&form_id="+formId
    })
    .then(function(response) {
    	var obj = response.data;
    	
    	if (obj.resultsJson != "null") {
        	Object.keys(obj).map(function(key, index) {
        		if (key != "formHtml") {        		
        			return obj[key] = JSON.parse(obj[key]);
        		}
    		});
    	}
    	
    	$scope.htmlForm = $sce.trustAsHtml(obj.formHtml);
    	
    	console.log("obj", obj);
    	
    	var resultsObj = JSON.parse(obj.resultsJson[30].value);
//    	var formHtml = obj.formHtml;
    	
    	setTimeout(function() {
	    	$.each(resultsObj, function(key, value) {
	    		console.log(key);
	    		var element = $(".public-form").find("input[name='" + key + "']");
	    		element.val(value);
	    		element.attr("readonly", true);
	    	})
    	}, 1500);
    	
    	console.log(resultsObj);
    	
    	
    });
	
	
	
});