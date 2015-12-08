var app = angular.module('loginValidation', []);

app.controller('loginController', function($scope){

	$scope.submitForm = function(isValid){
		if(isValid){

			console.log("Username: " + $("#username").val());
			//console.log("Senha: " + $("#senha").val());
			
            var senhaDigest = sha1($("#senha").val());
            var timestamp = Math.floor($.now()/1000);
            console.log("timestamp " +timestamp);
            var auth_pass = sha1(senhaDigest+timestamp);

            console.log("Auth_pass: " +auth_pass);

            $.ajax({
	            url: '/backend/sessions',
	            type: 'POST',
	            dataType: 'json',
	            contentType: 'application/json',
	            data: JSON.stringify({
	                'username': $("#username").val(),
	                'auth_pass': auth_pass
	            }),
	            success: function(response){
	            	var session_token = response['session_token'];
	            	console.log("Resposta da API: " +session_token);

	            	sessionStorage.setItem('session_token', session_token);
	            	console.log("Checando tamanho do sessionStorage: " +sessionStorage.length);
	            	console.log("Recuperando o session_token do sessionStorage: " +sessionStorage.getItem('session_token'));

	            	alert("Login realizado com sucesso! [session_token: " +session_token +"]");
	            	window.location.href = "index.html";
	            }, 
	            statusCode: {
	            	403: function(response){
            			console.log(response['status'] +": " +response['responseJSON']['message']);
            			alert(response['status'] +": " +response['responseJSON']['message']);
	            	}
	            }
        	});
		}
	};
});
