app.controller('statisticsListController', function($scope, $http, $rootScope) {

	console.info("statisticsListController");
	
	$http({
		method : "GET",
		url : "statistics?action=getRespondedForms&form_id=" + $rootScope.formId
	}).then(function(response) {

		obj = response.data;

		if (obj.respondedForms != "null") {
			Object.keys(obj).map(function(key, index) {
				if (isJson(obj[key])) {
					return obj[key] = JSON.parse(obj[key]);
				}
			});
		}
		
		$scope.states = obj.states;
		$scope.stateIcons = {};
		$scope.respondedForms = obj.respondedForms;
		$scope.orderByField = 'processState';
		$scope.reverseSort = true;
		
		// State Icons Sortieren und breinigen
		var keys = Object.keys(obj.stateIcons);
		keys.sort();
		for (var i = 0; i < keys.length; i++) {
			var key = keys[i].replace(/\d*_/, "");
			$scope.stateIcons[key] = obj.stateIcons[keys[i]];
		}
	});
	
	$scope.translateState = function(state) {
		return $scope.states[state] !== undefined ? $scope.states[state] : state;
	}
	
	$scope.stateIcon = function(icon, state) {
		return icon == state ? $scope.stateIcons[icon] + " active" : $scope.stateIcons[icon];
	}
	
});
