'use strict';

/* globals ttRegistrationApp */

ttRegistrationApp.factory('serverApi', function($http, $sce) {
  var serverApi = {
    get: function(url) {
      var promise = $http.get(url).success(function(res) {
        return res;
      }).error(function(res) {
        return res;
      });
      return promise;
    }
  };
  return serverApi;
});