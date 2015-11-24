angular.module('sbAdminApp').factory('requestUserController', function ($http, $filter) {
    var pathUrl = $filter('pathUrl');
       
    var _getUsuarios = function (cdEmp){
    	return $http.get(pathUrl('user'));
    };
    
    return {
    	getUsuarios: _getUsuarios
    };
});

