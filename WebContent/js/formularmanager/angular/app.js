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
