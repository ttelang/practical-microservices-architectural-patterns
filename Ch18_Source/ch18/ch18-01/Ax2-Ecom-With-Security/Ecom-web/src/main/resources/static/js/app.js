'use strict';
var ecomApp = angular.module('ecomApp', ['ngRoute','ngCookies']);

ecomApp.run(['$rootScope','$http', '$q','$window', function($rootScope, $http, $q, $window) {
	
	$rootScope.getAPIUrl=function() {
	$http.get('/configController')
	.then(
			function(response){
				$window.sessionStorage.setItem('apiUrl',response.data.apiGatewayURL);
				$rootScope.$emit('apiUrlRecieved', 'true');
			}, 
			function(errResponse){
				console.error('Error while fetching API gateway URL'+errResponse);
			}
	);
	}
	
	$rootScope.getAPIUrl();
}]);


ecomApp.directive('errSrc', function() {
	  return {
	    link: function(scope, element, attrs) {
	      element.bind('error', function() {
	        if (attrs.src != attrs.errSrc) {
	          attrs.$set('src', attrs.errSrc);
	        }
	      });
	    }
	  }
	});

ecomApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
        when('/productSearch', {
	    templateUrl: '/productSearch.html',
	     //controller: 'ProductController'
      }).
      when('/product/:id', {
  	    templateUrl: '/product.html',
  	     //controller: 'ProductController'
        }).

      when('/order', {
        	templateUrl: '/order.html',
        	//controller: 'ShowOrdersController'
      }).
      when('/cart', {
      	templateUrl: '/cart.html',
      	//controller: 'ShowOrdersController'
       }).
      when('/user', {
      	templateUrl: '/user.html',
      	//controller: 'ShowOrdersController'
       }).
       when('/newUser', {
         	templateUrl: '/newUser.html',
         	//controller: 'ShowOrdersController'
          }).
      otherwise({
    	  templateUrl: '/productSearch.html'
      });
}]).config(['$httpProvider', function($httpProvider) {
	  $httpProvider.defaults.withCredentials = true;
	  $httpProvider.interceptors.push('AuthInterceptor');
	}]);


ecomApp.factory('AuthInterceptor', function ($window, $q) {
	    return {
	        request: function(config) {
	            config.headers = config.headers || {};
	            if (sessionStorage.getItem('xtoken')) {
	                config.headers['x-token'] = sessionStorage.getItem('xtoken');
	            }
	            return config || $q.when(config);
	        },
	        response: function(response) {
	            if (response.status === 401) {
	                //  Redirect user to login page / signup Page.
	            	alert(response.status);
	            }
	            return response || $q.when(response);
	        }
	    };
	});

