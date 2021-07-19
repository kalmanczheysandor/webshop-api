/// <reference path="../Scripts/angular-1.1.4.js" />

/*#######################################################################
  
  Dan Wahlin
  http://twitter.com/DanWahlin
  http://weblogs.asp.net/dwahlin
  http://pluralsight.com/training/Authors/Details/dan-wahlin

  Normally like to break AngularJS apps into the following folder structure
  at a minimum:

  /app
      /controllers      
      /directives
      /services
      /partials
      /views

  #######################################################################*/

// var app = angular.module('customersApp', ['ngRoute','ngResource']);
//
// //This configures the routes and associates each route with a view and a controller
// app.config(function ($routeProvider) {
//     $routeProvider
//         .when('/customers',
//             {
//                 controller: 'CustomersController',
//                 templateUrl: '/app/partials/customers.html'
//             })
//         //Define a route that has a route parameter in it (:customerID)
//         .when('/customerorders/:customerID',
//             {
//                 controller: 'CustomerOrdersController',
//                 templateUrl: '/app/partials/customerOrders.html'
//             })
//         //Define a route that has a route parameter in it (:customerID)
//         .when('/orders',
//             {
//                 controller: 'OrdersController',
//                 templateUrl: '/app/partials/orders.html'
//             })
//         .when('/products',
//             {
//                 controller: 'ProductsController',
//                 templateUrl: '/app/partials/products.html',
//                 resolve: {
//                     myProductList: function($http) {
//                         return $http.get("/data/products/list").then(function (response) {
//                             return response.data;
//                         });
//                     }
//                 }
//             })
//         .otherwise({ redirectTo: '/customers' });
// });


var app = angular.module('customersApp', ['ui.router', 'ngResource']);


app.config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");

    $stateProvider
        .state('customers', {
            url: '/customers',
            controller: 'CustomersController',
            templateUrl: '/app/partials/customers.html'
        })
        .state('customerorders', {
            url: '/customerorders/:customerID',
            controller: 'CustomerOrdersController',
            templateUrl: '/app/partials/customerOrders.html'
        })
        .state('orders', {
            url: '/orders',
            controller: 'OrdersController',
            templateUrl: '/app/partials/orders.html'
        })
        .state("products", {
            url: '/products',
            templateUrl: '/app/partials/products.html',
            controller: 'ProductsController'
        })
        .state("products.dialog", {
            url: '/dialog/:productId',
            templateUrl: '/app/partials/dialog.html',
            controller: 'ProductsController'
        })


}]);
//     $routeProvider

