'use strict';

sddcDashboard.controller('OrderedServicesController', function($scope, $http){
        $scope.orderedservices = [];

        $scope.findOrderedServices = function () {
            $http.get("/api/orderedservices").success(function (data) {
                $scope.orderedservices = data;
            })
        };

        $scope.cancelService = function(service) {
            $http.delete("/api/orderedservices/{id}".replace('{id}',service.id)).success(function (data)
            {
              $scope.orderedservices = data;
            }
          )
      	};

        $scope.findOrderedServices();

    });
