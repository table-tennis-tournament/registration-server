'use strict';

function registerPlayers() {
	var club = getClubData();

	if ($('#registrationButton').hasClass('disabled') || !club) {
		hideProgress();
		return;
	}

	var playerData = {
		players : [],
		doubles : []
	};
	playerData.players = $('#playersTable tbody tr').map(function() {
		var $row = $(this);
		return {
			firstName : $row.find(':nth-child(1)').text(),
			lastName : $row.find(':nth-child(2)').text(),
			birthDate : getDateFromYear($row.find(':nth-child(3)').text()),
			competitions : [ {
				playerCompetitionPk : {
					competitionId : $row.find(':nth-child(4)').attr('value')
				}
			}, {
				playerCompetitionPk : {
					competitionId : $row.find(':nth-child(5)').attr('value')
				}
			}, {
				playerCompetitionPk : {
					competitionId : $row.find(':nth-child(6)').attr('value')
				}
			}, {
				playerCompetitionPk : {
					competitionId : $row.find(':nth-child(7)').attr('value')
				}
			} ],
			user : {
				userName : $('#mailAddress').val()
			},
			team : club
		};
	}).get();

	playerData.doubles = [];

	var json = JSON.stringify(playerData);
	$.ajax({
		type : 'POST',
		url : 'player/new',
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : false,
		data : json,
		success : function(data) {
			sessionStorage.setItem('playerData', JSON.stringify(data));
			loadRegistrationAckowledgment();
		}
	});
}

var getDoubleRegistrationPlayers = function() {
	var tableRowID = [];
	var table = document.getElementById('doublePlayersTable');
	var trs = table.getElementsByTagName('tr');
	var doubleRegistrations = [];

	for (var i = 1; i < trs.length; i++) {
		tableRowID.push(trs[i].id);
	}

	for (var i = 0; i < tableRowID.length; i++) {
		var row = document.getElementById(tableRowID[i]);
		var playerAID = row.getElementsByTagName('td')[0]
				.getAttribute('player-a-value');
		var playerBID = row.getElementsByTagName('td')[1]
				.getAttribute('player-b-value');
		var databasePlayerID = row.getElementsByTagName('td')[1]
				.getAttribute('player-id');
		var disciplineID = row.getElementsByTagName('td')[2]
				.getAttribute('value');
		var oneDoubleRegistration = {};
		var firstPlayer = getPlayerObjectByPlayersArrayId(playerAID);
		var secondPlayer = getPlayerObjectByPlayersArrayId(playerBID,
				databasePlayerID);
		oneDoubleRegistration = {
			firstPlayer : firstPlayer,
			secondPlayer : secondPlayer,
			doubleId : disciplineID
		};
		doubleRegistrations.push(oneDoubleRegistration);
	}

	return doubleRegistrations;
};

var getPlayerObjectByPlayersArrayId = function(arrayIndex, playerID) {
	// if (arrayIndex == DOUBLE_PLAYER_SEARCHING) {
	// return {
	// id : DOUBLE_PLAYER_SEARCHING,
	// firstName : 'Suchend',
	// lastName : 'Suchend',
	// birthDate : '1997'
	// };
	// }
	if (playerID) {
		return {
			id : playerID,
			firstName : 'first',
			lastName : 'last'
		};
	} else {
		return {
			id : 0,
			firstName : players[arrayIndex].forename,
			lastName : players[arrayIndex].surname,
			birthDate : getDateFromYear(players[arrayIndex].yearOfBirth)
		};
	}
};

function getClubData() {

	if (currentClubId === 0) {
		if (!checkIfClubNameIsNotEmpty()
				|| !checkIfOrganizationSelectpickerIsNotEmpty()) {
			return undefined;
		}
		return {
			id : currentClubId,
			name : $('#clubName').val(),
			organization : $('#organizationSelectpicker option:selected')
					.text()
		};
	}
	return {
		id : currentClubId,
		name : $('#club').val()
	};
}

function loadRegistrationAckowledgment() {
	$('#mainContentContainer').load(
			'resources/html/registrationAcknowledgment.html');
}

function checkIfOrganizationSelectpickerIsNotEmpty() {
	var organizationSelectpickerValue = $(
			'#organizationSelectpicker option:selected').attr('value');

	$('#organizationSelectpicker').selectpicker('setStyle', 'btn-danger',
			'remove');
	if (organizationSelectpickerValue == 0) {
		$('#organizationSelectpicker').selectpicker('setStyle', 'btn-danger');
		return false;
	}
	return true;
}

function checkIfClubNameIsNotEmpty() {
	var clubName = document.getElementById('clubName').value;
	if (clubName === '') {
		setErrorClassForInputFields('#clubName');
		return false;
	}
	return true;
}
