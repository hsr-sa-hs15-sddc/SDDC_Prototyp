'use strict';

sddcDashboard.controller('detailService',
    function detailService($scope, $routeParams, Service) {

  $scope.service = Service.get({id: $routeParams.serviceId});

});
