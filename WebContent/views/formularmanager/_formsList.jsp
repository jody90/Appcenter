<div ng-if="formData.evaluationType !== 'chart'">
	
	<div class="text-left">
		<dir-pagination-controls pagination-id="respondedForms"></dir-pagination-controls>
	</div>
	
	<table class="table table-hover">
		<tr>
			<th class="col-md-4">
				<div class="link-style" ng-click="orderByField='username'; reverseSort = !reverseSort">
	          		Antragssteller 
	          		<span ng-show="orderByField == 'username' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField == 'username' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-4">
				<div class="link-style" ng-click="orderByField='processState'; reverseSort = !reverseSort">
	          		Status 
	          		<span ng-show="orderByField == 'processState' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField == 'processState' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-4">
				<div class="link-style" ng-click="orderByField='processedBy'; reverseSort = !reverseSort">
	          		Bearbeiter 
	          		<span ng-show="orderByField == 'processedBy' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField == 'processedBy' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
		</tr>

		<tr dir-paginate="(id, form) in respondedForms | orderObjectBy:orderByField:reverseSort | itemsPerPage: 10" pagination-id="respondedForms">
			<td class="col-md-4">
				<a href="#viewForm/{{form.responseId}}">
					{{form.username}}
				</a>
			</td>
			<td class="col-md-4">
				{{translateState(form.processState)}}
			</td>
			<td class="col-md-4">
				{{form.processedBy}}
			</td>			
		</tr>
	</table>
	
	<dir-pagination-controls pagination-id="respondedForms"></dir-pagination-controls>
</div>