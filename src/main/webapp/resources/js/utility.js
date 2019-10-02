'use strict';

/* globals $ */

var selectedPlayerFromDatabase = {};

// From registration.js copied

var registerOnChangeListenerForSelectpickerB = function(firstSelectpickerId,
		secondSelectpickerId) {
	$(firstSelectpickerId).change(
			function() {
				var playerId = $('option:selected', this).attr('player-id');
				selectedPlayerFromDatabase.isSelected = false;
				if (playerId > 0) {
					selectedPlayerFromDatabase.isSelected = true;
					selectPlayerInSelectpicker(playerId);
					return;
				}

				if (selectedIndexOfPlayerB < 100) {
					appendPlayerToSelectpicker(secondSelectpickerId,
							selectedIndexOfPlayerB);
				}
				var selectedPlayerArrayIndex = $(firstSelectpickerId).val();
				selectedIndexOfPlayerB = selectedPlayerArrayIndex;
				if (selectedPlayerArrayIndex == 100) {
					return;
				}
				deleteSelectedPlayerFromSelectpicker(secondSelectpickerId,
						selectedPlayerArrayIndex);
				players[selectedPlayerArrayIndex].selected = true;
			});
};

function validateDoublePlayerRegistration() {
	removeAllErrorClassesFromDoubleSelectpickers();

	var selectedDisciplineValue = $('.selectpickerDoubleDiscipline').val();

	if (!areSelectedDoubleRegistrationValuesCorrect(selectedDisciplineValue)) {
		return;
	}

	players[selectedIndexOfPlayerA].registeredForDouble = true;
	// if (!doublePartnerSearching) {
	// players[selectedIndexOfPlayerB].registeredForDouble = true;
	// }
	addPlayerToDoubleRegistrationTable(selectedDisciplineValue);
	refreshAllPlayersForSelectpicker();
}

// end from registration.js copies

var dateExtension = '-01-01';
var spinnerVisible = false;

function hideOrShowDivs(elementId, isVisible) {
	if (isVisible) {
		$(elementId).show();
	} else {
		$(elementId).hide();
	}
}

function getRightTableRowClass(currentIndex, tableRowClass) {
	if (tableRowClass == "" && currentIndex % 2 != 0) {
		return "alternatedRow";
	}
	return "";

}

function checkFieldIsEmptyAndSetErrorClass(fieldId) {
	var value = $(fieldId).val();
	if (value === '') {
		setErrorClassForInputFields(fieldId);
		isEverythingValid = false;
	}
}

function setRegistrationButtonToActive(isButtonActive) {
	if (isButtonActive) {
		$('#registrationButton').removeClass('disabled');
	} else {
		$('#registrationButton').addClass('disabled');
	}
}

function setErrorClassForInputFields(elementId) {
	$(elementId).addClass('form-control-error');
}

function setErrorClassForSelectpicker(elementId) {
	$(elementId).selectpicker('setStyle', 'btn-danger');
}

function setDisabledClassForSelectpicker(elementId) {
	$(elementId).selectpicker('setStyle', 'disabled');
}

function removeErrorClassFromSelectpicker(elementId) {
	$(elementId).selectpicker('setStyle', 'btn-danger', 'remove');
	$(elementId).selectpicker('setStyle', 'btn-default');
}

function removeDisabledClassFromSelectpicker(elementId) {
	$(elementId).selectpicker('setStyle', 'disabled', 'remove');
	$(elementId).selectpicker('setStyle', 'btn-default');
}

function getDateFromYear(year) {
	return year + dateExtension;
}

function getYearFromString(yearAsString) {
	return yearAsString.replace(dateExtension, '');
}

function getFilteredStringValueFromInputField(inputFieldId) {
	var stringValue = $(inputFieldId).val();
	return stringValue.replace(new RegExp('[a-zA-Z\-]+', ''));
}

var setContainerOpacity = function(id, opacity) {
	var element = document.querySelector('#' + id);
	element.style.opacity = opacity;
}

function showProgress(cb) {
	if (!spinnerVisible) {
		document.querySelector('div#loader').style.display = 'block';
		setContainerOpacity('navbarContainer', 0.5);
		setContainerOpacity('mainContentContainer', 0.5);

		spinnerVisible = true;
		setTimeout(function() {
			registerPlayers();
		}, 200);
	}
}

function hideProgress() {
	if (spinnerVisible) {
		var spinner = $('div#loader');
		spinner.stop();
		document.querySelector('div#loader').style.display = 'none';
		setContainerOpacity('navbarContainer', 1.0);
		setContainerOpacity('mainContentContainer', 1.0);
		spinnerVisible = false;
	}
}

function getAvailablePlayerForSelectedDoubleDiscipline() {
	deleteAvailbalePlayersBeforeAddTheNew();
	var selectedDoubleDisciplineID = $('#availableDoubleDisciplinePicker')
			.find(':selected').attr('value');
	if (selectedDoubleDisciplineID == 100)
		return;
	$
			.getJSON(
					'player/search?competitionId=' + selectedDoubleDisciplineID,
					function(availablePlayersForDouble) {
						if (availablePlayersForDouble.length > 0) {
							document
									.getElementById('noDoublePlayersFoundMessage').style.display = 'none';
							appendDataToAvailableDoublePlayersTable(availablePlayersForDouble);
							setDoubleDiscipline();
						} else {
							showNoPlayersFoundMessage();
						}
					});
}

function deleteAvailbalePlayersBeforeAddTheNew() {
	$('#searchForDoublePlayersTable > tbody tr').remove();
}

function appendDataToAvailableDoublePlayersTable(players) {
	$
			.each(
					players,
					function(index, value) {
						var tableRow = '<tr data-player-id='
								+ value.id
								+ ' style="cursor: pointer;" onclick="addPlayerAsDoublePartner(this)"><td>'
								+ value.firstName + '</td>' + '<td>'
								+ value.lastName + '</td>' + '<td>'
								+ value.team.name + '</td>' + '</tr>';
						$('#searchForDoublePlayersTable > tbody').append(
								tableRow);
					});
}

function addPlayerAsDoublePartner(row) {
	var x = row.cells;
	var player = {
		id : $(row).data('player-id'),
		firstName : x[0].innerHTML,
		lastName : x[1].innerHTML
	};

	setPlayerInSecondDoubleSelectpicker(player);
	closePopup();
}

function closePopup() {
	$('#searchPlayerPopup').modal('hide');
}

function setPlayerInSecondDoubleSelectpicker(player) {
	selectedPlayerFromDatabase = player;
	$('#selectpickerPlayerB').append(
			$('<option></option>').attr('player-id', player.id).text(
					player.firstName + ' ' + player.lastName));
	$("#selectpickerPlayerB option[player-id='" + player.id + "']").prop(
			'selected', true);
	selectedPlayerFromDatabase.isSelected = true;
	$('#selectpickerPlayerB').selectpicker('refresh');
}

function setDoubleDiscipline() {
	var selectedDoublePlayerValue = $('#availableDoubleDisciplinePicker').find(
			':selected').attr('value');
	$('.selectpickerDoubleDiscipline').val(selectedDoublePlayerValue);
	$('.selectpickerDoubleDiscipline').selectpicker('refresh');
}

function selectPlayerInSelectpicker(playerId) {
	$("#selectpickerPlayerB option[player-id='" + playerId + "']").prop(
			'selected', true);
	$('#selectpickerPlayerB').selectpicker('refresh');
}

function showNoPlayersFoundMessage() {
	document.getElementById('noDoublePlayersFoundMessage').style.display = 'block';
}