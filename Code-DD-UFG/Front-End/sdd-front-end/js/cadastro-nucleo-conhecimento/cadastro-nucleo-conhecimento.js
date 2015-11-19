var app = angular.module('cadastroNucleoConhecimento', []);

app.controller('cadastroNucleoConhecimentoController', function ($scope) {

    $scope.submitForm = function (isValid) {
        if (isValid) {
            console.log("Tudo ok!");
            // Abre a requisição para o back-end
        }
    };
});