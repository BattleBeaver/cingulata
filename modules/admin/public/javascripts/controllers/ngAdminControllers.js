// (function () {
//   var Ctrl = function($scope) {
//       $scope.age = 24;
//   };
//
//   angular.module('app', [])
//   .controller('Ctrl', ['$scope', Ctrl]);
//
//
// })();

(function(angular) {
  var AdminController = function($scope) {
    console.log("main");
    $scope.tab = 1;
    $scope.selectTab = function(selectTab) {
      $scope.tab = selectTab;
    };

    $scope.isSelected = function(selectedTab) {
      return $scope.tab === selectedTab;
    }
    $scope.button = function(){
      console.log("Fsk");
    }
    $scope.phone = {
      name: "Nokia"
    }
  };

angular.module("app", ["ngRoute"])
  .controller("AdminController", ['$scope', AdminController])

  .config(['$routeProvider','$locationProvider',
    function($routeProvider,$locationProvider) {
      $routeProvider.when('/category',
        {
          templateUrl:'/admin/categories'

        })
      .when('/manage-crawlers',
        {
          templateUrl:'/admin/crawlers'

        })
      .when('/crawler-details',
        {
          templateUrl:'/admin/crawler-details'

        })
}]);
// /home/beaver/dev/workspaces/scala-mine/cingulata/modules/admin/public/javascripts/routes/categories.html
})(window.angular);
