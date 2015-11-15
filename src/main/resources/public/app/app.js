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
        templateUrl: 'views/orderedservices.html'
      })
      .when('/services/:serviceId', {
			controller: 'detailService',
			templateUrl: '/views/service/detail.html'
		}).otherwise({
			redirectTo: '/services'
		});
;
});


sddcDashboard.factory("Service", function($resource) {
  return $resource("/services/:id",{id: '@id'});
});
