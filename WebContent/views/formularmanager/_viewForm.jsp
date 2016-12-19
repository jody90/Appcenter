<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" class="sortimo.formularmanager.global.ConfigMaps"></jsp:useBean>
<!-- 
<div class="row">
	<div class="col-lg-2 border">1</div>
	<div class="col-lg-2 border">2</div>
	<div class="col-lg-2 border">3</div>
	<div class="col-lg-2 border">4</div>
	<div class="col-lg-2 border">5</div>
	<div class="col-lg-2 border">6</div>
</div>
 -->

<div class="row">
	<div class="col-xs-12 col-md-9">
		<div class="statistics-form" ng-bind-html="htmlForm"></div>
	</div>

	<div class="col-xs-12 col-md-3">
		<div class="form-statistics-meta">
			<div class="row">
				<div class="col-xs-12">
					<label for="username" class="fb-text-label">
						Bearbeiter
					</label>
					<input type="text" name="username" ng-value="{{user.username}}" value="{{user.username}}" disabled class="form-control">
				</div>
			</div>

			<div class="row margin-top-md">
				<div class="col-xs-12">
					<label for="state" class="fb-text-label">
						Status
					</label>
					<select name="state" class="form-control">
						<option ng-repeat="(key, state) in states" ng-value="{{key}}">
							{{state}}
						</option>
					</select>
				</div>
			</div>
		
		</div>
	</div>
</div>


<!-- 
<div class="text-center">
	<div class="public-form" ng-bind-html="htmlForm"></div>
</div>
 -->