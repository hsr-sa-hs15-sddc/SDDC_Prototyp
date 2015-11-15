'use strict';

sddcDashboard.controller('orderedDetailService',
    function orderedDetailService($scope, $routeParams, OrderedService) {
  $scope.service = OrderedService.get({id: $routeParams.orderedserviceId});
	/*$scope.cancelService = function() {
			$scope.service.$remove();
      scope.$apply(function() { $location.path("/orderedservices"); });
	};*/
});
