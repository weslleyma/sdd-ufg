var app = angular.module('cadastroNucleoConhecimento', []);

app.controller('cadastroNucleoConhecimentoController', function ($scope, $http) {

    $scope.submitForm = function (isValid) {
        if (isValid) {
            console.log("Nome: " + $("#nomeNucleo").val());

            var token = sessionStorage.getItem("session_token");
            console.log("Token: " + token);

            $.ajax({
                url: '/backend/knowledges',
                type: 'PUT',
                dataType: 'json',
                contentType: 'application/json',
                headers: {
                    "Session-Token": token
                },
                data: JSON.stringify({
                    'name': $("#nomeNucleo").val(),
                }),
                success: function (response) {
                    console.log("Success");
                },
                statusCode: {
                    201: function (response, status, xhr) {
                        console.log("201");
                        console.log(response);
                    },
                    400: function (response) {
                        console.log(response['status'] + ": " + response['responseJSON']['message']);
                        alert(response['status'] + ": " + response['responseJSON']['message']);
                    },
                    403: function (response) {
                        console.log(response['status'] + ": " + response['responseJSON']['message']);
                        alert(response['status'] + ": " + response['responseJSON']['message']);
                    }
                },
                complete: function (xhr) {
                    if (xhr.getResponseHeader('Location')) {

                        var nucleo_id = xhr.getResponseHeader('Location').split('/')[2];

                        var data = xhr.responseJSON;

                        for (var i = 0; i < data.knowledge_levels.length; i++) {
                            $.ajax({
                                url: '/backend/knowledge_levels',
                                type: 'POST',
                                dataType: 'json',
                                contentType: 'application/json',
                                headers: {
                                    "Session-Token": sessionStorage.getItem("session_token")
                                },
                                data: JSON.stringify({
                                    'level': data.knowledge_levels[i].id,
                                    'teacher_id': data.knowledge_levels[i].teacher.id,
                                    'knowledge_id': nucleo_id
                                }),
                                success: function (response) {
                                    console.log('Niveis de nucleos cadastrados para todos os professores com valor "3"');
                                },
                                statusCode: {
                                    403: function (response) {
                                        console.log(response['status'] + ": " + response['responseJSON']['message']);
                                        alert(response['status'] + ": " + response['responseJSON']['message']);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    };
    
});
