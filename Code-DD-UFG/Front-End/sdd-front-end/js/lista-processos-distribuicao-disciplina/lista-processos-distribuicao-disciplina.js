var app = angular.module('listaProcessoDistribuicaoDisciplina', []);

app.controller('processoDistribuicaoDisciplinaController', function ($scope) {

    var processos = [
            {processo_seletivo:'2016-1', disciplina:'Engenharia de Requisitos', horario:'2N2345', conflito:'Sim', qtde_inscritos:0},
            {processo_seletivo:'2015-2', disciplina:'Banco de Dados', horario:'2N2345', conflito:'Não', qtde_inscritos:2}
        ];
    
    $scope.processos = processos;
    
    $scope.editarItem = function(processo) {
        alert('Editar');
    };
    
    $scope.deletarItem = function(processo) {
        alert('Deletar');
    };
});