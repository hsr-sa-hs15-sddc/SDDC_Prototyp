'use strict';

sddcDashboard.controller('OrderedServicesController', function($scope, $http){
        $scope.orderedservices = [];

        $scope.findOrderedServices = function () {
            $http.get("/orderedservices").success(function (data) {
                $scope.orderedservices = data;
            })
        };

        $scope.findOrderedServices();

    });
