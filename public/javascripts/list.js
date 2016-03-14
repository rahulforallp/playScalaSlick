
     var internApp = angular.module('internApp', []);
             internApp.controller('InternCtrl', function ($scope, $http){
               $http.get('/list').success(function(data) {
                 $scope.dataList=data;
               });
             });

