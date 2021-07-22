var app = angular.module('customersApp', ['ngRoute', 'ngResource', 'ngMaterial']);

//This configures the routes and associates each route with a view and a controller
app.config(function ($routeProvider) {

    $routeProvider
        .when('/customers',
            {
                controller: 'CustomersController',
                templateUrl: '/app/partials/customers.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/customerorders/:customerID',
            {
                controller: 'CustomerOrdersController',
                templateUrl: '/app/partials/customerOrders.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/orders',
            {
                controller: 'OrdersController',
                templateUrl: '/app/partials/orders.html'
            })
        .when('/products',
            {
                controller: 'ProductsController',
                templateUrl: '/app/partials/products.html',
                resolve: {
                    myProductList: function ($http) {
                        return $http.get("/data/products/list").then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
        .when('/tests',
            {
                controller: 'TestsController',
                templateUrl: '/app/partials/tests.html',
            })
        .when('/dropdowns',
            {
                controller: 'DropdownsController',
                templateUrl: '/app/partials/dropdowns.html',
            })
        .otherwise({redirectTo: '/customers'});
});


app.config(function ($mdThemingProvider) {

    $mdThemingProvider.theme('default')
        .primaryPalette('blue',{'default':'700'})
        .accentPalette('blue',{'default':'700'});

});



app.config(function($mdIconProvider) {
    $mdIconProvider
        .iconSet("call", 'img/icons/sets/communication-icons.svg', 24)
        .iconSet("social", 'img/icons/sets/social-icons.svg', 24);
});

//
// var app = angular.module('customersApp', ['ui.router', 'ngResource']);
//
//
// app.config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
//     $urlRouterProvider.otherwise("/");
//
//     $stateProvider
//         .state('customers', {
//             url: '/customers',
//             controller: 'CustomersController',
//             templateUrl: '/app/partials/customers.html'
//         })
//         .state('customerorders', {
//             url: '/customerorders/:customerID',
//             controller: 'CustomerOrdersController',
//             templateUrl: '/app/partials/customerOrders.html'
//         })
//         .state('orders', {
//             url: '/orders',
//             controller: 'OrdersController',
//             templateUrl: '/app/partials/orders.html'
//         })
//         .state("products", {
//             url: '/products',
//             templateUrl: '/app/partials/products.html',
//             controller: 'ProductsController'
//         })
//         .state("products.dialog", {
//             url: '/dialog/:productId',
//             templateUrl: '/app/partials/dialog.html',
//             controller: 'ProductsController'
//         })
//
//
// }]);
// //     $routeProvider
//
