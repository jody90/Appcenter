app.controller('bossListController', function($scope, $http, $sce, $rootScope, $routeParams) {
	
	console.info("BossListController");
	
	$scope.controller = "boss"
	
	$http({
		method : "GET",
		url : "boss?action=getForms"
	}).then(function(response) {

		obj = response.data;
	
		if (obj.respondedForms != "null") {
			Object.keys(obj).map(function(key, index) {
				if (this.isJson(obj[key])) {
					return obj[key] = JSON.parse(obj[key]);
				}
			});
		}
		
		console.log("Object", obj);
		
		$scope.respondedForms = obj.bossForms;
	});
});
