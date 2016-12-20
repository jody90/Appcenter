<div ng-controller="statisticsController" ng-init="init()">
	<h1 class="text-center">
		{{formTitle}}
	</h1>

	<table class="table table-hover">
		<tr>
			<th>
				<div class="link-style" ng-click="orderByField='username'; reverseSort = !reverseSort">
	          		Antragssteller 
	          		<span ng-show="orderByField == 'username' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField == 'username' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th>
				<div class="link-style" ng-click="orderByField='processState'; reverseSort = !reverseSort">
	          		Status 
	          		<span ng-show="orderByField == 'processState' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField == 'processState' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th>
				<div class="link-style" ng-click="orderByField='processedBy'; reverseSort = !reverseSort">
	          		Bearbeiter 
	          		<span ng-show="orderByField == 'processedBy' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField == 'processedBy' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
		</tr>
		<tr ng-repeat="(id, form) in respondedForms | orderObjectBy:orderByField:reverseSort">
			<td>
				<a href="#viewForm/{{form.id}}">
					{{form.username}}
				</a>
			</td>
			<td>
				{{form.processState}}
			</td>
			<td>
				{{form.processedBy}}
			</td>			
		</tr>
	</table>
</div>