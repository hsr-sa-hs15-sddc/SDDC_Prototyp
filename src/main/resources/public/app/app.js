angular.module('servicesApp', [])
    .controller('ServicesCtrl', function($scope, $http){
        $scope.services = [];

        $scope.findServices = function () {
            $http.get("/services").success(function (data) {
                $scope.services = data;
            })
        };

        $scope.findServices();

    });