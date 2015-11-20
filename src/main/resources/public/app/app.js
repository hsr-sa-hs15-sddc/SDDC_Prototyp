'use strict';

var sddcDashboard = angular.module('sddcDashboard', ['ngRoute','ngResource']);

sddcDashboard.config(function($routeProvider) {

  $routeProvider.
      when('/services', {
        controller: 'ServicesController',
        templateUrl: 'views/service/list.html'
      }).
      when('/orderedservices',{
        controller: 'OrderedServicesController',
        templateUrl: 'views/orderedservice/list.html'
      })
      .when('/services/:serviceId', {
			controller: 'detailService',
			templateUrl: '/views/service/detail.html'
		})
    .when('/orderedservices/:orderedserviceId', {
    controller: 'orderedDetailService',
    templateUrl: '/views/orderedservice/detail.html'
  })
  .otherwise({
			redirectTo: '/services'
		});
;
});


sddcDashboard.factory("Service", function($resource) {
  return $resource("/api/services/:id",{id: '@id'});
});

sddcDashboard.factory("OrderedService", function($resource) {
  return $resource("/api/orderedservices/:id",{id: '@id'});
});
