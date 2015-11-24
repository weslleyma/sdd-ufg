angular.module('sbAdminApp')
        .filter('pathUrl', ['$location', function ($location) {
                return function (urlComplemento) {

                    var newURL = 'http://private-anon-85e42b33c-sddufg.apiary-mock.com/';

                    return newURL + urlComplemento;
                };
            }]);