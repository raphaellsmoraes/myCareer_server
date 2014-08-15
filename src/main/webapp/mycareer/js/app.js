var myCareerApp = angular.module('myCareerApp', [
    'ngRoute',
    'ui.bootstrap',
    'usersControllers'
]);

myCareerApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/users', {
                templateUrl: 'partials/users/users.html',
                controller: 'usersListControllers'
            }).
            otherwise({
                redirectTo: '/index.html'
            });
}]);