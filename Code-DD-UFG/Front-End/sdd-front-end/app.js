'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
angular
  .module('sbAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
  ])
  .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider) {
    
    $ocLazyLoadProvider.config({
      debug:false,
      events:true,
    });

    $urlRouterProvider.otherwise('/dashboard/inicio');

    $stateProvider
      .state('dashboard', {
        url:'/dashboard',
        templateUrl: 'sdd-pages/dashboard/main.html',
        resolve: {
            loadMyDirectives:function($ocLazyLoad){
                return $ocLazyLoad.load(
                {
                    name:'sbAdminApp',
                    files:[
                    'js/directives/header.js',
                    'js/directives/header-notification.js',
                    'js/directives/sidebar.js',
                    'js/directives/sidebar-search.js'
                    ]
                }),
                $ocLazyLoad.load(
                {
                   name:'toggle-switch',
                   files:["bower_components/components/angular-toggle-switch/angular-toggle-switch.min.js",
                          "bower_components/components/angular-toggle-switch/angular-toggle-switch.css"
                      ]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngAnimate',
                  files:['bower_components/components/angular-animate/angular-animate.js']
                });
                $ocLazyLoad.load(
                {
                  name:'ngCookies',
                  files:['bower_components/components/angular-cookies/angular-cookies.js']
                });
                $ocLazyLoad.load(
                {
                  name:'ngResource',
                  files:['bower_components/components/angular-resource/angular-resource.js']
                });
                $ocLazyLoad.load(
                {
                  name:'ngSanitize',
                  files:['bower_components/components/angular-sanitize/angular-sanitize.js']
                });
                $ocLazyLoad.load(
                {
                  name:'ngTouch',
                  files:['bower_components/components/angular-touch/angular-touch.js']
                });
            }
        }
    })
      .state('dashboard.inicio',{
        url:'/inicio',
        controller: 'InicioCrtl',
        templateUrl:'sdd-pages/inicio.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              'js/controllers/perfil-controller.js',
              'js/perfil-docente/perfil-docente.js'
              ]
            })
          }
        }
      })
      
      .state('dashboard.configuracao',{
        templateUrl:'',
        url:'/configuracao',
        controller: 'ConfiguracaoCtrl',
        resolve: {
            loadMyFiles:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                name:'sbAdminApp',
                files:[
                'js/controllers/configuracao-controller.js'
                ]
              })
            }
          }
        })
        
      .state('dashboard.distribuicao',{
        templateUrl:'sdd-pages/processo-distribuicao.html',
        url:'/distribuicao',
        controller: 'DistribuicaoCtrl',
        resolve: {
            loadMyFiles:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                name:'sbAdminApp',
                files:[
                'js/processo-distribuicao/processo-distribuicao.js',
                'js/controllers/distribuicao-de-disciplina-controller.js'
                ]
              })
            }
          }      	
    })
      .state('login',{
        templateUrl:'sdd-pages/login.html',
        url:'/login',
        controller: 'LoginCtrl',
        resolve: {
            loadMyFiles:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                name:'sbAdminApp',
                files:[
                'js/filter/path_url.js',
                'js/factory/request-login-controller.js',
                'js/controllers/login-controller.js'
                ]
              })
            }
          }     
    })
      .state('cadastro-docente',{
    	templateUrl:'sdd-pages/cadastro-de-docente.html',
    	url:'/cadastro-de-docente',
    	controller: 'CadastroDocenteCtrl',
    	 resolve: {
             loadMyFiles:function($ocLazyLoad) {
               return $ocLazyLoad.load({
                 name:'sbAdminApp',
                 files:[
                 'js/controllers/cadastro-docente-controller.js'
                 ]
               })
             }
           }     
    })
    
      .state('dashboard.usuario',{
        templateUrl:'',
        url:'/usuario',
        controller:'UserCtrl',
        resolve: {
            loadMyFiles:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                name:'sbAdminApp',
                files:[
                'js/filter/path_url.js',
                'js/factory/request-user-controller.js',
                'js/controllers/user-controller.js'
                ]
              })
            }
          }     
    })
      .state('dashboard.disciplinas',{
        templateUrl:'',
        url:'/disciplinas',
        controller: 'DisciplinaCtrl',
        resolve: {
            loadMyFiles:function($ocLazyLoad) {
              return $ocLazyLoad.load({
                name:'sbAdminApp',
                files:[
                'js/controllers/disciplina-controller.js'
                ]
              })
            }
          }   
    })
      .state('dashboard.nucleos',{
          templateUrl:'sdd-pages/lista_de_nucleo_de_conhecimento.html',
          url:'/nucleos',
          controller: 'NucleoCtrl',
          resolve: {
              loadMyFiles:function($ocLazyLoad) {
                return $ocLazyLoad.load({
                  name:'sbAdminApp',
                  files:[
                  'js/filter/path_url.js',
                  'js/factory/request-nucleo-controller.js',
                  'js/controllers/nucleo-controller.js'
                  ]
                })
              }
            }   
      })
  }]);