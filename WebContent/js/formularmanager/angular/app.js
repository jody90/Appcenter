var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "../views/formularmanager/_formsList.jsp"
    })
    .when("/viewForm/:id", {
        templateUrl : "../views/formularmanager/_viewForm.jsp"
    });
});

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