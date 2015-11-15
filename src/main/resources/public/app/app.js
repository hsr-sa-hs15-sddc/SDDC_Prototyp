'use strict';

var sddcDashboard = angular.module('sddcDashboard', ['ngRoute']);

sddcDashboard.config(function($routeProvider) {

  $routeProvider.
      when('/', {
        controller: 'ServicesController',
        templateUrl: 'views/services.html'
      });
});
