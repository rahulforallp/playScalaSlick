
     var internApp = angular.module('internApp', []);

              internApp.controller('AddCtrl', function ($scope, $http){
              alert("hello")
                     $scope.save = function(){
                     var data1 = {"id":8,"name":$scope.name,"email":$scope.email,"mobile":$scope.mobile,"address":$scope.address,"emergency":$scope.emergency};

                              $http({
                                  method:'POST',
                                  url:'/addNew',
                                  data: JSON.stringify(data1),
                                  contentType: 'application/json',
                                  dataType:'json'
                              })
                     }})



             internApp.controller('InternCtrl', function ($scope, $http){

               $http.get('/list').success(function(data) {
                 $scope.dataList=data;
               });
             });




