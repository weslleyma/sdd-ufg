'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:UserCtrl
 * @description
 * # UserCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('UserCtrl', function($scope, requestUserController) {
	  $scope.usuarios = [];
	  
	  
	  $scope.getUsuarios = function() {		
			request.getUsuarios().success(
					function (response) {	
						$scope.usuarios = response;
						
					}
			);
		};
	  
  });
