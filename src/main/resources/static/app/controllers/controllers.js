app.controller('NavbarController', function ($scope, $location) {
    $scope.getClass = function (path) {
        if ($location.path().substr(0, path.length) == path) {
            return true
        } else {
            return false;
        }
    }
});


app.controller('DropdownsController', function ($scope,$mdDialog) {
    $scope.products = [
        {
            "id": 5,
            "name": "alma",
            "code": "CVX-001",
            "category": "gyümölcs"
        },
        {
            "id": 6,
            "name": "körte",
            "code": "CVX-002",
            "category": "gyümölcs"
        }

    ];





    var originatorEv;

    $scope.openMenu = function ($mdMenu, ev) {
        originatorEv = ev;
        $mdMenu.open(ev);
    };

    $scope.notificationsEnabled = true;
    $scope.toggleNotifications = function () {
        $scope.notificationsEnabled = !$scope.notificationsEnabled;
    };

    $scope.redial = function (event,productId) {
        $mdDialog.show(
            $mdDialog.alert()
                .targetEvent(originatorEv)
                .clickOutsideToClose(true)
                .parent('body')
                .title('Suddenly, a redial at id:'+productId)
                .textContent('You just called a friend; who told you the most amazing story. Have a cookie!')
                .ok('That was easy')
        );

        originatorEv = null;
    };

    $scope.checkVoicemail = function () {
        // This never happens.
    };


});


app.controller('TestsController', function ($scope, $mdDialog) {

    $scope.status = '  ';
    $scope.customFullscreen = false;

    $scope.showAlert = function (ev) {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true)
                .title('This is an alert title')
                .textContent('You can specify some description text in here.')
                .ariaLabel('Alert Dialog Demo')
                .ok('Got it!')
                .targetEvent(ev)
        );
    };

    $scope.showConfirm = function (ev) {
        var confirm = $mdDialog.confirm()
            .title('Would you like to delete your debt?')
            .textContent('All of the banks have agreed to forgive you your debts.')
            .ariaLabel('Lucky day')
            .targetEvent(ev)
            .ok('Please do it!')
            .cancel('Sounds like a scam');

        $mdDialog.show(confirm).then(function () {
            $scope.status = 'You decided to get rid of your debt.';
        }, function () {
            $scope.status = 'You decided to keep your debt.';
        });
    };

    $scope.showPrompt = function (ev) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.prompt()
            .title('What would you name your dog?')
            .textContent('Bowser is a common name.')
            .placeholder('Dog name')
            .ariaLabel('Dog name')
            .initialValue('Buddy')
            .targetEvent(ev)
            .required(true)
            .ok('Okay!')
            .cancel('I\'m a cat person');

        $mdDialog.show(confirm).then(function (result) {
            $scope.status = 'You decided to name your dog ' + result + '.';
        }, function () {
            $scope.status = 'You didn\'t name your dog.';
        });
    };

    $scope.showAdvanced = function (ev) {
        $mdDialog.show({
            controller: DialogController,
            templateUrl: '/app/partials/dialog1.html',
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application to prevent interaction outside of dialog
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        }).then(function (answer) {
            $scope.status = 'You said the information was "' + answer + '".';
        }, function () {
            $scope.status = 'You cancelled the dialog.';
        });
    };

    $scope.showTabDialog = function (ev) {
        $mdDialog.show({
            controller: DialogController,
            templateUrl: '/app/partials/tabDialog.html',
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application to prevent interaction outside of dialog
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        }).then(function (answer) {
            $scope.status = 'You said the information was "' + answer + '".';
        }, function () {
            $scope.status = 'You cancelled the dialog.';
        });
    };

    $scope.showPrerenderedDialog = function (ev) {
        $mdDialog.show({
            contentElement: '#myDialog',
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application to prevent interaction outside of dialog
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
    };

});

function DialogController($scope, $mdDialog) {
    $scope.hide = function () {
        $mdDialog.hide();
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.answer = function (answer) {
        $mdDialog.hide(answer);
    };
}


// app.controller('TestsController', function ($mdDialog) {
//
//
//     var alert;
//     var ctrl = this;
//     ctrl.showAlert = showAlert;
//     ctrl.showDialog = showDialog;
//     ctrl.items = [1, 2, 3];
//
//     // Internal method
//     function showAlert() {
//         alert = $mdDialog.alert({
//             title: 'Attention',
//             textContent: 'This is an example of how simple dialogs can be!',
//             ok: 'Close'
//         });
//
//         $mdDialog
//             .show( alert )
//             .finally(function() {
//                 alert = undefined;
//             });
//     }
//
//     function showDialog($event) {
//         var parentEl = angular.element(document.body);
//         $mdDialog.show({
//             parent: parentEl,
//             targetEvent: $event,
//             template:"<md-dialog aria-label=\"List dialog\">" +
//             "<md-dialog-content>malacka"+
//             "<md-list><md-list-item ng-repeat=\"item in ctrl.items\"><p>Number {{item}}</p></md-list-item></md-list>"+
//             "</md-dialog-content>" +
//             "<md-dialog-actions><md-button ng-click=\"ctrl.closeDialog()\" class=\"md-primary\">Close Dialog</md-button></md-dialog-actions>" +
//             "</md-dialog>",
//             locals: {
//                 items: ctrl.items
//             },
//             controller: DialogController,
//             controllerAs: 'ctrl'
//         });
//         function DialogController($mdDialog) {
//             this.closeDialog = function() {
//                 $mdDialog.hide();
//             }
//         }
//     }
//
//
//
// });


app.controller('ProductsController', function ($scope, $http, $mdDialog) {
    $scope.orderby = 'id';
    $scope.reverse = false;

    parent = this;
    this.loadList = function () {
        $http.get("/data/products/list").then(function (response) {
            $scope.products = response.data;
        });
    }
    this.loadList();





    $scope.addProduct = function (event) {


        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true)
                .title('This is an alert title')
                .textContent('You can specify some description text in here.')
                .ariaLabel('Alert Dialog Demo')
                .ok('Got it!')
                .targetEvent(event)
        );

        var data = {
            "name": $scope.newProduct.name,
            "code": $scope.newProduct.code,
            "category": $scope.newProduct.category
        }

        $http.post("/data/products/add", angular.toJson(data))
            .success(function (response) {
                parent.loadList();
            })
            .error(function (response) {
                alert(response);
            });


        //customersService.insertCustomer(firstName, lastName, city);
        $scope.newProduct.name = "";
        $scope.newProduct.code = "";
        $scope.newProduct.category = "";

    };


    $scope.deleteProductById = function (productId) {
        $http.delete("/data/products/delete/" + productId).then(function (response) {
            parent.loadList();
        });
    }


    $scope.setColumnOrder = function (orderby) {
        if (orderby === $scope.orderby) {
            $scope.reverse = !$scope.reverse;
        }
        $scope.orderby = orderby;
    };


    // $scope.setColumnOrder = function (orderby) {
    //     if (orderby === $scope.orderby) {
    //         $scope.reverse = !$scope.reverse;
    //     }
    //     $scope.orderby = orderby;
    // };


//    init();
    //
    // function init() {
    //
    // }
    //
    // this.drawList   =  function(response,scope) {
    //
    // };
    //
    // productsService.getProducts(function(response) {
    //     alert(111);
    //     alert(reponse.data);
    //     $scope.products = response.data;
    // });

});


app.controller('CustomersController', function ($scope, customersService) {

});

app.controller('CustomerOrdersController', function ($scope, $routeParams, customersService) {


});

app.controller('OrdersController', function ($scope, customersService) {

});


app.controller('OrderChildController', function ($scope) {

});


/*#######################################################################
  
  Dan Wahlin
  http://twitter.com/DanWahlin
  http://weblogs.asp.net/dwahlin
  http://pluralsight.com/training/Authors/Details/dan-wahlin

  Normally like the break AngularJS controllers into separate files.
  Kept them together here since they're small and it's easier to look through them.
  example. 

  #######################################################################*/

//This controller retrieves data from the customersService and associates it with the $scope
//The $scope is ultimately bound to the customers view
// app.controller('ProductsController', function ($scope, $http,myProductList) {
//     $scope.orderby = 'id';
//     $scope.reverse = false;
//
//     parent = this;
//     this.loadList = function () {
//         // $http.get("/data/products/list").then(function (response) {
//         //     $scope.products = response.data;
//         // });
//     }
//     this.loadList();
//     //$scope.products = myProductList;
//
//     $scope.addProduct = function () {
//         var firstName = $scope.newProduct.name;
//
//         var data = {
//                 "name": $scope.newProduct.name,
//                 "code": $scope.newProduct.code,
//                 "category":$scope.newProduct.category
//         }
//
//         $http.post("/data/products/add",angular.toJson(data))
//         .success(function (response) {
//             parent.loadList();
//         })
//         .error(function (response) {
//             alert(response);
//         });
//
//
//         //customersService.insertCustomer(firstName, lastName, city);
//         $scope.newProduct.name = "";
//         $scope.newProduct.code = "";
//         $scope.newProduct.category = "";
//     };
//
//
//     $scope.deleteProductById = function (productId) {
//         $http.delete("/data/products/delete/" + productId).then(function (response) {
//             parent.loadList();
//         });
//     }
//
//
//     $scope.setColumnOrder = function (orderby) {
//         if (orderby === $scope.orderby) {
//             $scope.reverse = !$scope.reverse;
//         }
//         $scope.orderby = orderby;
//     };
//
//
//     // $scope.setColumnOrder = function (orderby) {
//     //     if (orderby === $scope.orderby) {
//     //         $scope.reverse = !$scope.reverse;
//     //     }
//     //     $scope.orderby = orderby;
//     // };
//
//
// //    init();
//     //
//     // function init() {
//     //
//     // }
//     //
//     // this.drawList   =  function(response,scope) {
//     //
//     // };
//     //
//     // productsService.getProducts(function(response) {
//     //     alert(111);
//     //     alert(reponse.data);
//     //     $scope.products = response.data;
//     // });
//
// });


//This controller retrieves data from the customersService and associates it with the $scope
//The $scope is ultimately bound to the customers view
// app.controller('CustomersController', function ($scope, customersService) {
//
//     //I like to have an init() for controllers that need to perform some initialization. Keeps things in
//     //one place...not required though especially in the simple example below
//     init();
//
//     function init() {
//         $scope.customers = customersService.getCustomers();
//     }
//
//     $scope.insertCustomer = function () {
//         var firstName = $scope.newCustomer.firstName;
//         var lastName = $scope.newCustomer.lastName;
//         var city = $scope.newCustomer.city;
//         customersService.insertCustomer(firstName, lastName, city);
//         $scope.newCustomer.firstName = '';
//         $scope.newCustomer.lastName = '';
//         $scope.newCustomer.city = '';
//     };
//
//     $scope.deleteCustomer = function (id) {
//         customersService.deleteCustomer(id);
//     };
// });
//
// //This controller retrieves data from the customersService and associates it with the $scope
// //The $scope is bound to the order view
// app.controller('CustomerOrdersController', function ($scope, $routeParams, customersService) {
//     $scope.customer = {};
//     $scope.ordersTotal = 0.00;
//
//     //I like to have an init() for controllers that need to perform some initialization. Keeps things in
//     //one place...not required though especially in the simple example below
//     init();
//
//     function init() {
//         //Grab customerID off of the route
//         var customerID = ($routeParams.customerID) ? parseInt($routeParams.customerID) : 0;
//         if (customerID > 0) {
//             $scope.customer = customersService.getCustomer(customerID);
//         }
//     }
//
// });
//
// //This controller retrieves data from the customersService and associates it with the $scope
// //The $scope is bound to the orders view
// app.controller('OrdersController', function ($scope, customersService) {
//     $scope.customers = [];
//
//     //I like to have an init() for controllers that need to perform some initialization. Keeps things in
//     //one place...not required though especially in the simple example below
//     init();
//
//     function init() {
//         $scope.customers = customersService.getCustomers();
//     }
// });
//
// app.controller('NavbarController', function ($scope, $location) {
//     $scope.getClass = function (path) {
//         if ($location.path().substr(0, path.length) == path) {
//             return true
//         } else {
//             return false;
//         }
//     }
// });

//This controller is a child controller that will inherit functionality from a parent
//It's used to track the orderby parameter and ordersTotal for a customer. Put it here rather than duplicating 
//setOrder and orderby across multiple controllers.
// app.controller('OrderChildController', function ($scope) {
//     $scope.orderby = 'product';
//     $scope.reverse = false;
//     $scope.ordersTotal = 0.00;
//
//     init();
//
//     function init() {
//         //Calculate grand total
//         //Handled at this level so we don't duplicate it across parent controllers
//         if ($scope.customer && $scope.customer.orders) {
//             var total = 0.00;
//             for (var i = 0; i < $scope.customer.orders.length; i++) {
//                 var order = $scope.customer.orders[i];
//                 total += order.orderTotal;
//             }
//             $scope.ordersTotal = total;
//         }
//     }
//
//     $scope.setOrder = function (orderby) {
//         if (orderby === $scope.orderby) {
//             $scope.reverse = !$scope.reverse;
//         }
//         $scope.orderby = orderby;
//     };
//
// });






















