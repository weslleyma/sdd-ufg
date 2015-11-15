var app = angular.module('loginValidation', []);

app.controller('loginController', function($scope){

	$scope.submitForm = function(isValid){
		if(isValid){
			console.log("Tudo ok!");
			// Abre a requisição para o back-end
		}
	};
});
