app.service('productsService',  function ($http) {



    this.getProducts = function (customMethod) {
        //$http.get("/data/product/list").then(customMethod(response));
    }

});

// app.factory('productsService', ['$resource',
//     function($resource) {
//         return $resource('/data/product/list', {}, {
//             query: {
//                 method: 'GET',
//                 // params: {phoneId: 'phones'},
//                 isArray: true
//             }
//         });
//     }
// ]);


// app.factory('productsService', ['$resource',
//     function($resource) {
//         return $resource('phones/:phoneId.json', {}, {
//             query: {
//                 method: 'GET',
//                 params: {phoneId: 'phones'},
//                 isArray: true
//             }
//         });
//     }
// ]);