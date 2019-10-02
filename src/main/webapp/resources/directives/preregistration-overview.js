'use strict';

/* globals ttRegistrationApp */

ttRegistrationApp.directive('preregistrationOverview', 
[         'homeModel',
  function(homeModel) {
  return {
    restrict: 'E',
    replace: true,
    scope: {
    },
    templateUrl: 'resources/directives/preregistration-overview.html',
    link: function(scope, element, attrs) {
        homeModel.getPreregisteredOverviewData()
          .success(function(data) {
            scope.preregistrationOverviewData = data;
          })
          .error(function() {
            console.log("error on get data")
          }
        );

      }
    };
  }
]);