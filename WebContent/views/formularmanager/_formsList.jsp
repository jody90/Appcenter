<div ng-if="formData.evaluationType !== 'chart'">
	
	<div class="text-left">
		<dir-pagination-controls pagination-id="respondedForms"></dir-pagination-controls>
	</div>
	
	<table class="table table-hover">
		<tr>
			<th class="col-md-1">
				<div class="link-style" ng-click="orderByField='username'; reverseSort = !reverseSort">
	          		Antragssteller 
	          		<span ng-show="orderByField === 'username' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField === 'username' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-4">
				<div class="link-style" ng-click="orderByField='formTitle'; reverseSort = !reverseSort">
	          		Formular 
	          		<span ng-show="orderByField === 'formTitle' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField === 'formTitle' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-3">
				<div class="link-style" ng-click="orderByField='processState'; reverseSort = !reverseSort">
	          		Status 
	          		<span ng-show="orderByField === 'processState' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField === 'processState' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-1">
				<div class="link-style" ng-click="orderByField='processedBy'; reverseSort = !reverseSort">
	          		Bearbeiter 
	          		<span ng-show="orderByField === 'processedBy' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField === 'processedBy' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-1">
				<div class="link-style" ng-click="orderByField='createdAt'; reverseSort = !reverseSort">
	          		Erstellt am: 
	          		<span ng-show="orderByField === 'createdAt' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField === 'createdAt' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
			<th class="col-md-1">
				<div class="link-style" ng-click="orderByField='modifiedAt'; reverseSort = !reverseSort">
	          		Zuletzt geändert:  
	          		<span ng-show="orderByField === 'modifiedAt' && !reverseSort" class="glyphicon glyphicon-menu-down"></span>
			    	<span ng-show="orderByField === 'modifiedAt' && reverseSort" class="glyphicon glyphicon-menu-up"></span>
				</div>
			</th>
		</tr>

		<tr dir-paginate="(id, form) in respondedForms | orderObjectBy:orderByField:reverseSort | itemsPerPage: 10" pagination-id="respondedForms">
			<td class="col-md-1">
<!-- 
				<a ng-href="#viewForm/{{form.responseId}}/{{form.formId}}" ng-if="controller !== 'boss'">
					{{form.username}}
				</a>
				<a ng-href="#viewBossForm/{{form.responseId}}/{{form.formId}}" ng-if="controller === 'boss'">
					{{form.username}}
				</a>
 -->
					{{form.username}}
			</td>
			<td class="col-md-5">
				<a ng-href="#viewForm/{{form.responseId}}/{{form.formId}}" ng-if="controller !== 'boss'">
					{{form.formTitle}}
				</a>
				<a ng-href="#viewBossForm/{{form.responseId}}/{{form.formId}}" ng-if="controller === 'boss'">
					{{form.formTitle}}
				</a>
			</td>
			<td class="col-md-3">
				<i ng-repeat="(icon, name) in stateIcons" class="{{stateIcon(icon, form.processState)}} {{form.processState}} state-icons" aria-hidden="true"></i>
				<span class="font-sm valign-middle">
					{{translateState(form.processState)}}
				</span>
			</td>
			<td class="col-md-1">
				{{form.processedBy}}
			</td>	
			<td class="col-md-1">
				{{form.createdAt}}
			</td>	
			<td class="col-md-1">
				{{form.modifiedAt}}
			</td>			
		</tr>
	</table>
	
	<dir-pagination-controls pagination-id="respondedForms"></dir-pagination-controls>
</div>