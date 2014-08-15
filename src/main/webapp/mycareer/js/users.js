var usersControllers = angular.module('usersControllers', []);

usersControllers.controller('usersListControllers', ['$scope', '$http',
    function ($scope, $http) {
        $http.get('/api/user/users').success(function(data) {
            $scope.users = data;
        });
}]);

