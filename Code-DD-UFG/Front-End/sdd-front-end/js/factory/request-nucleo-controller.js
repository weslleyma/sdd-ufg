angular.module('sbAdminApp').factory('requestNucleoController', function ($http, $filter) {
    var pathUrl = $filter('pathUrl');
       
    var _getNucleos = function (){
    	return $http.get(pathUrl('knowledges'));
    };
    
    return {
    	getNucleos: _getNucleos
    };
});

