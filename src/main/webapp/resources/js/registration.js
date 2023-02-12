'use strict';

/* globals removeErrorClassFromSelectpicker, $ */

document.getElementById('noDoublePlayersFoundMessage').style.display = 'none';

var isEverythingValid = true;
var currentDisziplinIndex = 1;
var currentClubId = 0;
var currentTableRowIndex = 0;

var currentDoubleRegistrationTableRowIndex = 200;
var players = [];
var selectedIndexOfPlayerA = 100;
var selectedIndexOfPlayerB = 100;
var doublePartnerSearching = false;

var DOUBLE_PLAYER_SEARCHING = 101;

var deleteSelectedPlayerFromSelectpicker = function(selectpickerId, playerValue) {
	$(selectpickerId + ' option[value="' + playerValue + '"]').remove();
	$(selectpickerId).selectpicker('refresh');
};

var registerOnChangeListenerForSelectpickerA = function(firstSelectpickerId,
		secondSelectpickerId) {
	$(firstSelectpickerId).change(
			function() {
				if (selectedIndexOfPlayerA < 100) {
					appendPlayerToSelectpicker(secondSelectpickerId,
							selectedIndexOfPlayerA);
				}
				var selectedPlayerArrayIndex = $(firstSelectpickerId).val();
				selectedIndexOfPlayerA = selectedPlayerArrayIndex;
				if (selectedPlayerArrayIndex == 100) {
					return;
				}
				deleteSelectedPlayerFromSelectpicker(secondSelectpickerId,
						selectedPlayerArrayIndex);
				players[selectedPlayerArrayIndex].selected = true;
			});
};



var registerEventListenerForDoublePartnerSearchingCheckbox = function() {
	$('#doublePartnerSearchingCheckbox').change(function() {
		removeDisabledClassFromSelectpicker('#selectpickerPlayerB');
		doublePartnerSearching = $(this).is(':checked');
		if (doublePartnerSearching) {
			setDisabledClassForSelectpicker('#selectpickerPlayerB');
		}
	});
};

$(function() {
	setClubListAndRegisterSelectedHandler();
	callDatepickerWithYearOnly();

	hideOrShowDivs('#mailDiv', false);
	hideOrShowDivs('#firstPlayer', false);
	hideOrShowDivs('#newClubDataFieldsDiv', false);
	hideOrShowDivs('#double-registration', false);
	buildNewDisciplineSelectBox(currentDisziplinIndex);

	registerOnChangeListenerForSelectpickerA('#selectpickerPlayerA',
			'#selectpickerPlayerB');
	registerOnChangeListenerForSelectpickerB('#selectpickerPlayerB',
			'#selectpickerPlayerA');
	registerEventListenerForDoublePartnerSearchingCheckbox();
	loadDisciplineArray('competition/double/all',
			'.selectpickerDoubleDiscipline');
});

function buildNewDisciplineSelectBox(number) {
	if(number > 1) {
		return;
	}

	var selectPickerId = getDisciplineIdForSelectPickerByDisciplineNumber(number);
	var selectPicker = $(
			'<select class="selectpicker" data-style="btn-default" value="'
					+ number + '" id="' + selectPickerId + '">').appendTo(
			'#discipline' + number);
	selectPicker.append($('<option></option>').attr('value', 0).text(
			'--Disziplin ' + number + '--'));
	selectPicker.change(function() {
		var disciplineNumber = $(this).attr('value');
		if (allDisciplinesReachedOrIsNotTheLastOneSelected(this.value,
				disciplineNumber)) {

		}
	});
	loadDisciplineArray('competition/single/all', "#" + selectPickerId);
	currentDisziplinIndex++;
}

function allDisciplinesReachedOrIsNotTheLastOneSelected(selectedValue,
		disciplineNumber) {
	return (currentDisziplinIndex == 5 || selectedValue == 0 || disciplineNumber < (currentDisziplinIndex - 1));
}

function getDisciplineIdForSelectPickerByDisciplineNumber(number) {
	return 'discipline' + number + 'class';
}

function loadDisciplineArray(url, elementId) {
	$.getJSON(url, function(data) {
		$.each(data, function(index, value) {
			$(elementId).append(
					$('<option></option>').attr('value', value.id).text(
							value.name));
		});
		$(elementId).selectpicker('refresh');
	});
}

function showRowForOnePlayer() {
	hideOrShowDivs('#firstPlayer', true);
}

function callDatepickerWithYearOnly() {
	$('#datepicker').datepicker({
		format : 'yyyy',
		viewMode : 'years',
		minViewMode : 'years',
		autoclose : true,
	});
}

function showCorrectFieldsAfterTeamSelection(teamData) {
	teamData.on('typeahead:selected', function(evt, data) {
		showRowForOnePlayer();
	});
}

function validateSinglePlayerData() {
	$('.form-control').removeClass('form-control-error');
	var firstName = $('#firstName').val();
	var surname = $('#surname').val();
	var yearOfBirth = $('#datepicker').val();
	isEverythingValid = true;

	checkFieldIsEmptyAndSetErrorClass('#firstName');
	checkFieldIsEmptyAndSetErrorClass('#surname');
	checkYearIsValidAndSetErrorClass('#datepicker');
	validateAtLeastOneDisziplin();
	if (isEverythingValid) {
		appendPlayerToTable(firstName, surname, yearOfBirth);
		hideOrShowDivs('#mailDiv', true);
		clearPlayerDataFields();

		//prepareDoubleRegistration(firstName, surname, yearOfBirth);
	}
}



var removeAllErrorClassesFromDoubleSelectpickers = function() {
	removeErrorClassFromSelectpicker('#selectpickerPlayerA');
	removeErrorClassFromSelectpicker('#selectpickerPlayerB');
	removeErrorClassFromSelectpicker('.selectpickerDoubleDiscipline');
};

var areSelectedDoubleRegistrationValuesCorrect = function(
		selectedDisciplineValue) {
	var isValidDoubleRegistration = true;
	if (selectedIndexOfPlayerA == 100) {
		setErrorClassForSelectpicker('#selectpickerPlayerA');
		isValidDoubleRegistration = false;
	}
	// if (selectedIndexOfPlayerB == 100 && ) {
	// 	setErrorClassForSelectpicker('#selectpickerPlayerB');
	// 	isValidDoubleRegistration = false;
	// }
	if (selectedDisciplineValue == 100) {
		setErrorClassForSelectpicker('.selectpickerDoubleDiscipline');
		isValidDoubleRegistration = false;
	}
	return isValidDoubleRegistration;
};

var addPlayerToDoubleRegistrationTable = function(selectedDisciplineValue) {
	var playerAName = getPlayerNameByIndex(selectedIndexOfPlayerA);
	var playerBName = '';
	var playerBIndex = selectedIndexOfPlayerB;
	if (!selectedPlayerFromDatabase.isSelected) {
		players[selectedIndexOfPlayerB].registeredForDouble = true;
		playerBName = getPlayerNameByIndex(selectedIndexOfPlayerB);
	} else {
		playerBName = getPlayerNameByIndex();
	}

	var disciplineName = $('#selectedDoubleDiscipline option:selected')
			.text();

	var playerIdStringForTable = '';
	if(selectedPlayerFromDatabase.isSelected)
		playerIdStringForTable = ' player-id="' + selectedPlayerFromDatabase.id + '"';

	$('#doublePlayersTable > tbody')
			.append(
					'<tr id="'
							+ currentDoubleRegistrationTableRowIndex
							+ '"><td player-A-value=\''
							+ selectedIndexOfPlayerA
							+ '\'>'
							+ playerAName
							+ '</td>'
							+ '<td player-b-value=\''
							+ playerBIndex
							+ '\'' + playerIdStringForTable + '>'
							+ playerBName
							+ '</td>'
							+ '<td value="'
							+ selectedDisciplineValue
							+ '">'
							+ disciplineName
							+ '</td>'
							+ '<td><button class="btn btn-default btn-xs pull-right" type="button" id="'
							+ currentDoubleRegistrationTableRowIndex
							+ '" onclick="deleteDoublePlayerRowByRowId(this.id)"><span class="glyphicon glyphicon-minus"></span></button></td></tr>');
	currentDoubleRegistrationTableRowIndex++;
};

var getPlayerNameByIndex = function(playerIndex) {
	if(selectedPlayerFromDatabase.isSelected && !playerIndex) {
		return selectedPlayerFromDatabase.firstName + ' ' + selectedPlayerFromDatabase.lastName;
	}
	return players[playerIndex].forename + ' ' + players[playerIndex].surname;
};

var deleteDoublePlayerRowByRowId = function(tableRowId) {
	var row = document.getElementById(tableRowId);
	var playerAID = row.getElementsByTagName('td')[0]
			.getAttribute('player-a-value');
	var playerBID = row.getElementsByTagName('td')[1]
			.getAttribute('player-b-value');
	players[playerAID].registeredForDouble = false;
	if (playerBID != DOUBLE_PLAYER_SEARCHING && players[playerBID] && players[playerBID].registeredForDouble) {
		players[playerBID].registeredForDouble = false;
	}
	refreshAllPlayersForSelectpicker();
	row.parentNode.removeChild(row);
};

var prepareDoubleRegistration = function(forename, surname, yearOfBirth) {
	pushPlayerToArray(forename, surname, yearOfBirth);
	refreshAllPlayersForSelectpicker();
	//hideOrShowDivs('#double-registration', true);
};

var refreshAllPlayersForSelectpicker = function() {
	selectedIndexOfPlayerA = 100;
	selectedIndexOfPlayerB = 100;
	doublePartnerSearching = false;
	$('#doublePartnerSearchingCheckbox').prop('checked', false);
	removeAllEntriesInDoubleSelectpickers();
	for ( var playerIndex in players) {
		if (players[playerIndex].registeredForDouble) {
			continue;
		}
		players[playerIndex].selected = false;
		appendPlayerToSelectpicker('#selectpickerPlayerA', playerIndex);
		appendPlayerToSelectpicker('#selectpickerPlayerB', playerIndex);
	}
	$('#selectpickerPlayerA').selectpicker('refresh');
	$('#selectpickerPlayerB').selectpicker('refresh');
	$('.selectpickerDoubleDiscipline').val(100);
	$('.selectpickerDoubleDiscipline').selectpicker('refresh');
};

var removeAllEntriesInDoubleSelectpickers = function() {
	$('#selectpickerPlayerA option[value!="100"]').remove();
	$('#selectpickerPlayerB option[value!="100"]').remove();
};

var appendPlayerToSelectpicker = function(selectpickerId, playerIndex) {
	$(selectpickerId).append(
			$('<option></option>').attr('value', playerIndex).attr('player-id', 0).text(
					players[playerIndex].forename + ' '
							+ players[playerIndex].surname));
	$(selectpickerId).selectpicker('refresh');
};

var pushPlayerToArray = function(forename, surname, yearOfBirth) {
	var player = {
		forename : forename,
		surname : surname,
		yearOfBirth : yearOfBirth,
		selected : false,
		registeredForDouble : false
	};
	players.push(player);
};

function validateAtLeastOneDisziplin() {
	var disciplineOneClass = '#'
			+ getDisciplineIdForSelectPickerByDisciplineNumber(1);
	var disciplineId = $(disciplineOneClass + ' option:selected').attr('value');
	$(disciplineOneClass).selectpicker('setStyle', 'btn-danger', 'remove');
	if (disciplineId == 0) {
		$(disciplineOneClass).selectpicker('setStyle', 'btn-danger');
		isEverythingValid = false;
	}
}

function checkMailAddressIsGiven() {
	var mailAddress = document.getElementById('mailAddress').value;
	setRegistrationButtonToActive(false);

	if (isValidEmailAddress(mailAddress)) {
		setRegistrationButtonToActive(true);
		return;
	}
	setErrorClassForInputFields('mailAddress');
}

function isValidEmailAddress(mailAddress) {
	var pattern = new RegExp(
			/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	return pattern.test(mailAddress);
}

function checkIfClubEmpty() {
	var club = $('#club').val();

	if (club === '') {
		hideOrShowDivs('#mailDiv', false);
		hideOrShowDivs('#firstPlayer', false);
		hideOrShowDivs('#newClubDataFieldsDiv', false);
		clearPlayerDataFields();
	}
}

function checkYearIsValidAndSetErrorClass() {
	var yearOfBirth = $('#datepicker').val();
	checkFieldIsEmptyAndSetErrorClass('#datepicker');
	var currentYear = new Date().getFullYear();

	if (yearOfBirth > currentYear || yearOfBirth < 1913) {
		setErrorClassForInputFields('#datepicker');
		isEverythingValid = false;
	}
}

function appendPlayerToTable(firstName, surname, yearOfBirth) {
	var disciplineTDElements = '';
	var emptyTDElements = '';
	for (var disciplineIndex = 1; disciplineIndex < 5; disciplineIndex++) {
		var classId = getDisciplineIdForSelectPickerByDisciplineNumber(disciplineIndex);
		var disciplineName = $('#' + classId + ' option:selected').text();
		var disciplineId = $('#' + classId + ' option:selected').attr('value');

		// if no discipline selected or not visible skip
		if (disciplineId == 0 || typeof disciplineId == 'undefined') {
			emptyTDElements += "<td/>";
			continue;
		}
		disciplineTDElements += '<td value="' + disciplineId + '">'
				+ disciplineName + '</td>';
	}
	disciplineTDElements += emptyTDElements;

	$('#playersTable > tbody')
			.append(
					'<tr id="'
							+ currentTableRowIndex
							+ '"><td>'
							+ firstName
							+ '</td>'
							+ '<td>'
							+ surname
							+ '</td>'
							+ '<td>'
							+ yearOfBirth
							+ '</td>'
							+ disciplineTDElements
							+ '<td><button class="btn btn-default btn-xs pull-right" type="button" id="'
							+ currentTableRowIndex
							+ '" onclick="deletePlayerRowByRowId(this.id)"><span class="glyphicon glyphicon-minus"></span></button></td></tr>');
	currentTableRowIndex++;
	checkMailAddressIsGiven();
}

function deletePlayerRowByRowId(tableRowId) {
	var row = document.getElementById(tableRowId);
	row.parentNode.removeChild(row);
	checkIfTableIsEmpty();
}

function checkIfTableIsEmpty() {
	if ($('#playersTable > tbody').children().length === 0) {
		hideOrShowDivs('#mailDiv', false);
		setRegistrationButtonToActive(false);
	}
}

function clearPlayerDataFields() {
	$('#firstName').val('');
	$('#surname').val('');
	$('#datepicker').val('');
	clearDisciplineSelectBoxes();
}

function clearDisciplineSelectBoxes() {
	for (var disciplineIndex = 1; disciplineIndex < currentDisziplinIndex; disciplineIndex++) {
		$('#discipline' + disciplineIndex).empty();
	}
	currentDisziplinIndex = 1;
	buildNewDisciplineSelectBox(currentDisziplinIndex);
}

function setClubListAndRegisterSelectedHandler() {

	$
			.getJSON(
					'team/all',
					function(data) {
						$('#club')
								.typeahead(
										{
											source : data,
											sorter : function(items) {
												if (items.length == 0) {
													items.push("Undefined");
												}
												return items;
											},
											highlighter : function(item) {
												if (typeof item == 'undefined') {
													toggleTooltipForNewClub();
													return '<span><strong>Neuer Teamname</strong><span class="glyphicon glyphicon-plus" style="vertical-align:middle; margin-bottom: 5px; padding-left: 10px"></span>';
												}
												return "<span>" + item
														+ "</span>";
											},
											itemSelected : function(item) {
												var clubId = item
														.attr('data-value');
												if (typeof clubId == 'undefined') {
													currentClubId = 0;
													loadNewClubCreatingSite();
													return;
												}
												currentClubId = clubId;
												showRowForOnePlayer();
											}
										});
					});
}

function toggleTooltipForNewClub() {
	$('#club').tooltip({
		trigger : "focus"
	});
	$('#club').tooltip('show');
}

function loadNewClubCreatingSite() {
	setUpOrganizationSelectPicker();
	hideOrShowDivs('#newClubDataFieldsDiv', true);
	showRowForOnePlayer();
}

function setUpOrganizationSelectPicker() {
	$.getJSON('team/organizations', function(data) {
		$.each(data, function(index, value) {
			$('#organizationSelectpicker').append(
					$('<option></option>').text(value));
		});
		$('#organizationSelectpicker').selectpicker('refresh');
	});
}



