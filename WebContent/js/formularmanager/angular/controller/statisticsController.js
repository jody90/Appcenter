app.controller('statisticsController', function($scope, $http, $sce, $rootScope) {
	var formId = $("#form_id").attr("data-form-id");
	
//	$rootScope.$on('$routeChangeStart', function (event, next, current) {
//		var id = next.params.id;
//		if (id !== undefined) {
//			viewForm(id);
//		}
//	});
//	
	function isJson(str) {
	    try {
	        JSON.parse(str);
	    } catch (e) {
	        return false;
	    }
	    return true;
	}
	
    $http({
        method: "GET",
        url: "statistics?action=getStatistics&form_id="+formId
    })
    .then(function(response) {
    	var obj = response.data;
    	
    	console.log(obj);
    	
    	$scope.formTitle = obj.formTitle;
    	
    	if (obj.resultsJson != "null") {
        	Object.keys(obj).map(function(key, index) {
        		if (isJson(obj[key])) {
        			return obj[key] = JSON.parse(obj[key]);
        		}
    		});
    	}
    	
    	$scope.respondedForms = obj.resultsJson;
    	
    	$scope.htmlForm;
    	
    	function viewForm(id) {    	
    		$scope.htmlForm = "";
    		var resultsObj = JSON.parse(obj.resultsJson[id].value);
    		
    		var htmlForm = '<div id="formReadyIndicator">';
    		htmlForm += obj.formHtml;    	
    		htmlForm += '</div>';
    		
    		$("#formReadyIndicator").ready(function() {
    			$.each(resultsObj, function(key, value) {
    				$("#formReadyIndicator").find("input[name='" + key + "']").val(value).attr("readonly", true);
    			})
    		})
    		$scope.htmlForm = $sce.trustAsHtml(htmlForm);
    	}
    	
    	$rootScope.$on('$routeChangeStart', function (event, next, current) {
    		var id = next.params.id;
    		if (id !== undefined) {
    			viewForm(id);
    		}
    	});
    })
});

