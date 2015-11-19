var app = angular.module('cadastroDocente', []);

app.controller('cadastroDocenteController', function($scope){

	$scope.submitForm = function(isValid){
		if(isValid){
			console.log("Tudo ok!");
			// Abre a requisição para o back-end
		}
	};
});