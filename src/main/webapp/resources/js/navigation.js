'use strict';

$(function() {
	hideOrShowDivs("#navbarRightWhenLoggedIn", false);
	var url = window.location.hash;
	var requestedSite = url.substring(url.indexOf('#') + 1);

	if (requestedSite !== '' && requestedSite !== 'myRegistration') {
		navigateToRequestedID(requestedSite);
		return;
	}
	navigateToRequestedID('information');
});

function navigateToRequestedID(elementID) {
  if(elementID != 'information') {
    $('#preregistration-view').css('display', 'none');
    $('#tregInformationComplete').css('display', 'none');
  } else {
    $('#preregistration-view').css('display', 'block');
    $('#tregInformationComplete').css('display', 'block');
  }
  
	var htmlPath = buildHtmlPath(elementID);
	setMainContentAndActiveFlag(elementID, htmlPath);
}

function buildHtmlPath(elementID) {
	return 'resources/html/' + elementID + '.html';
}

function setMainContentAndActiveFlag(elementID, htmlPath) {
	loadNewHtmlContentFromFile(htmlPath);
	setActiveFlag(elementID);
}

function loadNewHtmlContentFromFile(htmlPath) {
	$('#mainContentContainer').load(htmlPath);
}

function setActiveFlag(elementID) {
	$('.navbar li').removeClass('active');
	document.getElementById(elementID).className = 'active';
}