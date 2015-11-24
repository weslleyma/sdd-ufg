'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:NucleoCtrl
 * @description
 * # NucleoCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('NucleoCtrl', function($scope,$position, requestNucleoController) {
	  $scope.responseNucleos = [];
	  
	  /*
	   * Exemplo de requisicao dos nucleos de conhecimento
	   */
	  $scope.getNucleos = function() {		
		  requestNucleoController.getNucleos().success(
					function (response) {	
						$scope.responseNucleos = response;					
					}
			);
		};
  });
