app.controller('statisticsController', function($scope, $http, $sce, $rootScope, $routeParams) {
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
    			
    			$("#formReadyIndicator").find(":checkbox").attr("disabled", true);
    			$("#formReadyIndicator").find(":radio").attr("disabled", true);
    			$("#formReadyIndicator").find("[type='select']").attr("disabled", true);
    			
    			$.each(resultsObj, function(key, value) {
    				var element = $("#formReadyIndicator").find("[name='" + key + "']")[0].outerHTML;
    				var type = $(element).attr("type");

    				switch (type) {
    				
    					case "textarea" : 
    					case "text" : 
	    				case "date" : 
	    					$("#formReadyIndicator").find("[name='" + key + "']").val(value).attr("readonly", true);	    				
    					break;
	    				case "checkbox" : 
	    				case "radio" : 
	    					$("#formReadyIndicator").find("[name='" + key + "'] [value='" + value + "']").attr("checked", true);	    				
    					break;
	    				case "select" :
	    					$("#formReadyIndicator").find("[name='" + key + "'] [value='" + value + "']").attr("selected", true);	    					
    					break;
    				
    				}
    				
    				
    				console.log();
    				
//    				console.log(key);
//    				console.log(resultsObj[key]);
 
    			})
    		})
    		$scope.htmlForm = $sce.trustAsHtml(htmlForm);
    	}
    	
    	// Beim initialen Laden auch feuern
    	var id = $routeParams.id;
		if (id !== undefined) {
			viewForm(id);
		}
    	
		// Bei jedem routeChange feuern
    	$rootScope.$on('$routeChangeSuccess', function (event, next, current) {
    		var id = next.params.id;
    		if (id !== undefined) {
    			viewForm(id);
    		}
    	});
    })
});

