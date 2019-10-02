'use strict';

function login() {
	var email = $('#loginEmailInput').val();
	var password = $('#loginPasswordInput').val();
	$('.form-control').removeClass('form-control-error');
	$
			.getJSON(
					'user?username=' + email + "&password=" + password,
					function(data) {
						if (jQuery.isEmptyObject(data)) {
							setErrorClassForInputFields('#loginEmailInput');
							setErrorClassForInputFields('#loginPasswordInput');
							return;
						}
						sessionStorage.setItem('username', data.userName);
						sessionStorage.setItem('password', password);
						
						if(data.isAdmin === 1){
							$("#navigationTabsList")
							.append(
									'<li id="payUser">'
											+ '<a onclick="navigateToRequestedID(\'payUser\');">Kasse</a></li>');
							navigateToRequestedID('payUser');
						}
						else{
							$("#navigationTabsList")
							.append(
									'<li id="myRegistration">'
											+ '<a onclick="navigateToRequestedID(\'myRegistration\');">Meine Anmeldung</a></li>');
							navigateToRequestedID('myRegistration');
						}
						
						hideOrShowDivs("#navbarRightWhenLoggedIn", true);
						hideOrShowDivs("#navbarRightToLogin", false);
						$('#loginEmailInput').val('');
						$('#loginPasswordInput').val('');
						$('#username').text(email);
					});
}

function logout() {
	hideOrShowDivs("#navbarRightWhenLoggedIn", false);
	hideOrShowDivs("#navbarRightToLogin", true);
	$('#myRegistration').remove();
	$('#payUser').remove();
	navigateToRequestedID('information');
}
