var app = angular.module('loginValidation', []);

app.controller('loginController', function($scope){

	$scope.submitForm = function(isValid){
		if(isValid){
			console.log("Our form is awesome!");
			alert("Our form is awesome!");
		}
	};
});
