<table class="table table-hover">
	<tr>
		<th>
			Antragssteller
		</th>
		<th>
			Status
		</th>
		<th>
			Bearbeiter
		</th>
	</tr>
	<tr ng-repeat="(id, form) in respondedForms">
		<td>
			<a href="#viewForm/{{id}}">
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