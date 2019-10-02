'use strict';

/* globals ttRegistrationApp */

ttRegistrationApp.factory('homeModel', function(serverApi) {
	var homeModel = {
		getPreregisteredOverviewData : function() {
			return serverApi.get('competition/information');
		},
	
		getInformationData: function(){
			return serverApi.get('competition/info');
		}
	};
	return homeModel;
});