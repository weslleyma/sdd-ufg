var app = angular.module('cadastroDocente', []);

app.controller('cadastroDocenteController', function($scope){

	$scope.submitForm = function(isValid){
		if(isValid){
			
			console.log("Nome: " + $("#nomeDocente").val());
			console.log("Matrícula: " + $("#matricula").val());
			console.log("Data de ingresso: " + $("#dataIngresso").val());
			console.log("Username: " + $("#username").val());
			console.log("E-mail: " + $("#email").val());
			console.log("Senha: " + $("#senha").val());

			$.ajax({
	            url: '/backend/teachers',
	            type: 'POST',
	            dataType: 'json',
	            headers: {
					"Content-Type": 'application/json'
	            },
	            data: JSON.stringify({
	                'name': $("#nomeDocente").val(),
				    'registry': $("#matricula").val(),
				    'url_lattes': "",
				    'date_entry': $("#dataIngresso").val(),
				    'formation': "",
				    'workload': "",
				    'about': "",
				    'rg': "",
				    'cpf': "",
				    'birth_date': ""
	            }),
	            success: function(response){
	            	console.log("Success ajax1");
	            }, 
	            statusCode: {
	            	201: function(response){
	            		console.log("201");
	            		console.log(response);
	            		//alert(xhr.getResponseHeader('Location'));
	            	},
	            	400: function(response){
            			console.log(response['status'] +": " +response['responseJSON']['message']);
            			alert(response['status'] +": " +response['responseJSON']['message']);
	            	},
	            	403: function(response){
            			console.log(response['status'] +": " +response['responseJSON']['message']);
            			alert(response['status'] +": " +response['responseJSON']['message']);
	            	}
	            },
	            complete: function(xhr){
					if(xhr.getResponseHeader('Location')){
						
						var location = xhr.getResponseHeader('Location');
						console.log("Location: " +location);

						// Pega o id do docente
						var arr = location.split('/');
						console.log("Array: " +arr[2]);

						var senhaDigest = sha1($("#senha").val());
						console.log("SenhaDigest: " +senhaDigest);

						$.ajax({
				            url: '/backend/users',
				            type: 'POST',
				            dataType: 'json',
				            contentType: 'application/json',
						    data: JSON.stringify({
						    	'username': $("#username").val(),
						    	'password': senhaDigest,
						    	'email': $("#email").val(),
						    	'teacher_id': arr[2]
						    }),
				            success: function(response){
				            	console.log("Success ajax2");
				            }, 
				            statusCode: {
				            	201: function(response){
				            		console.log("201");
	            					console.log(response);
				            	},
				            	400: function(response){
				            		console.log(response['status'] +": " +response['responseJSON']['message']);
				            		alert(response['status'] +": " +response['responseJSON']['message']);
				            	}
				            }
				    	});
					}
	            }
        	});

			
		}
	};
});