var app = angular.module('app', ['ngRoute', 'angularUtils.directives.dirPagination']);

app.config(function($routeProvider) {
    $routeProvider
    .when("/listForms/:formId", {
        templateUrl : "../views/formularmanager/_formsList.jsp",
        controller : "statisticsListController"
    })
    .when("/showChart/:formId", {
        templateUrl : "../views/formularmanager/_viewForm.jsp",
        controller : "statisticsChartController"
    })
    .when("/viewForm/:responseId/:formId", {
    	templateUrl : "../views/formularmanager/_viewForm.jsp",
    	controller : "statisticsFormController"
    })
    .when("/listBossForms", {
        templateUrl : "../views/formularmanager/_formsList.jsp",
        controller : "bossListController"
    })
    .when("/viewBossForm/:responseId/:formId", {
        templateUrl : "../views/formularmanager/_viewForm.jsp",
        controller : "bossFormController"
    })
    .when("/listTodoForms", {
        templateUrl : "../views/formularmanager/_formsList.jsp",
        controller : "todoListController"
    });
});

app.run(function($rootScope, $location) {
    $rootScope.$on("$routeChangeStart", function(event, next, current) { 
    	if (next.params) {
    		$rootScope.formId = next.params.formId;
    		$rootScope.responseId = next.params.responseId;
    	}
    });    
});

app.isJson = function (str) {
	try {
		JSON.parse(str);
	} catch (e) {
		return false;
	}
	return true;	
};

app.filter('orderObjectBy', function() {
	return function(items, field, reverse) {
		var filtered = [];
		angular.forEach(items, function(item) {
			filtered.push(item);
		});
		filtered.sort(function(a, b) {
			return (a[field] > b[field] ? 1 : -1);
		});
		if (reverse)
			filtered.reverse();
		return filtered;
	};
});