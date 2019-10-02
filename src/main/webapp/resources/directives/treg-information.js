'use strict';

/* globals ttRegistrationApp */

ttRegistrationApp.directive('tregInformation', 
[         'homeModel',
	  function(homeModel) {
		  return {
			    restrict: 'E',
			    replace: true,
			    scope: {
		    },
		    templateUrl: 'resources/directives/treg-information.html',
		    link: function(scope, element, attrs) {
		        homeModel.getInformationData()
		          .success(function(data) {
		            scope.tregInformation = data.text;
		          })
		          .error(function() {
		            console.log('error on get info');
		          }
		        );
	
		      }
		    };
		  }
	]);