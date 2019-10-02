'use strict';

var playerNumber = 1;

$(function() {
	$('#organizationSelectpicker').selectpicker('refresh');
	disableBothPlayerTables();
	loadDisciplineSelectPicker();
	registerOnChange();
});

function disableBothPlayerTables() {
	hideOrShowDivs('#doublePlayersTable', false);
	hideOrShowDivs('#playersTable', false);
}

function setPlayersTableVisibility(id, visibility) {
	$('#doublePlayersTable').css('display', visibility);
}

function loadDisciplineSelectPicker() {
	$.getJSON('competition/all', function(data) {
		$.each(data, function(index, value) {
			if(value.kind == 1) {
				$('#organizationSelectpicker').append(
						$('<option></option>').attr('value', value.id).attr('kind',
								value.kind).text(value.name));
			}
		});
		$('#organizationSelectpicker').selectpicker('refresh');
	});
}

function registerOnChange() {
	$('#organizationSelectpicker').change(
			function() {
				disableBothPlayerTables();
				$('#playersTable > tbody > tr').remove();
				var competitionKind = $('#organizationSelectpicker').find(
						':selected').attr('kind');
				buildPlayerTable(this.value, competitionKind);
			});
}

function buildPlayerTable(competitionID, competitionKind) {
	playerNumber = 1;
	$.getJSON('player/comp?competitionId=' + competitionID, function(data) {
		if (competitionKind == 1) {
			appendDataToPlayersTable(data, "");
			$.getJSON('player/waitlist?competitionId=' + competitionID,
					function(data) {
						appendDataToPlayersTable(data, 'waitlist');
					});
			hideOrShowDivs('#playersTable', true);
			return;
		}
		appendDataToDoublePlayersTable(data, "");
		hideOrShowDivs('#doublePlayersTable', true);
	});
}

function appendDataToPlayersTable(data, tableRowClass) {
	$.each(data, function(index, value) {
		if(tableRowClass != 'waitlist'){
			tableRowClass = getRightTableRowClass(index, tableRowClass);
		}

		var tableRow = "<tr class=" + tableRowClass + "><td>" + playerNumber
				+ "</td><td>" + value.firstName + "</td><td>" + value.lastName
				+ "</td><td>" + value.team.name + "</td><td>" + ""
				+ "</td></tr>";
		$('#playersTable > tbody').append(tableRow);
		playerNumber++;
	});
}

function appendDataToDoublePlayersTable(data, tableRowClass) {
	var currentDoubleIndex = 1;
	for (var arrayIndex = 0; arrayIndex < data.length; arrayIndex += 2) {
		tableRowClass = getRightTableRowClass(currentDoubleIndex - 1,
				tableRowClass);

		var firstPlayer = data[arrayIndex];
		var secondPlayer = null;
		if (arrayIndex != data.length - 1) {
			secondPlayer = data[arrayIndex + 1];
		}
		var tableRow = "<tr class=" + tableRowClass + "><td>"
				+ currentDoubleIndex + "</td><td>"
				+ getPlayerNameIfNotNull(firstPlayer) + "</td><td>"
				+ getClubNameIfNotNull(firstPlayer) + "</td><td>"
				+ getPlayerNameIfNotNull(secondPlayer) + "</td><td>"
				+ getClubNameIfNotNull(secondPlayer) + "</td></tr>";
		$('#doublePlayersTable > tbody').append(tableRow);
		currentDoubleIndex++;
	}
}

function getPlayerNameIfNotNull(player) {
	if (player != null) {
		return player.firstName + " " + player.lastName;
	}
	return "";
}
function getClubNameIfNotNull(player) {
	if (player != null) {
		return player.team.name;
	}
	return "";
}