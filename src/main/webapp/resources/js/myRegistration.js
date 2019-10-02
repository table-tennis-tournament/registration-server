'use strict';

/* globals $ */

$(function() {
	loadPlayersByUsername();
});
function loadPlayersByUsername() {
	var username = sessionStorage.getItem('username');
	var password = sessionStorage.getItem('password');
	$.getJSON('player?user=' + username + '&password=' + password, function(
			data) {
		var tableRow = '';
		$.each(data, function(index, player) {
			tableRow += '<tr><td>' + player.firstName + '</td><td>' +
				player.lastName + '</td><td>' + player.birthDate +
					'</td><td>' + player.team.name + '</td>';
			$.each(player.competitions, function(index, competition) {
				var classForPaidStatus = '';
				if(competition.isWaitingList) {
					classForPaidStatus = 'waitlist';
				}
				if(competition.paid) {
					classForPaidStatus = 'success';
				}
				tableRow += '<td class="' + classForPaidStatus + '">' + competition.name + '</td>';
			});
			for (var diff = 0; diff < 4 - player.competitions.length; diff++) {
				tableRow += '<td></td>';
			}
			tableRow += '</tr>';
		});
		$('#playersTable > tbody').append(tableRow);
	});
}

function registerOnChange() {
	$('#organizationSelectpicker').change(
			function() {
				$.getJSON('player/comp?competitionId=' + this.value, function(
						data) {
					$('#playersTable > tbody > tr').remove();
					$.each(data, function(index, value) {
						var tableRow = '<tr><td>' + value.firstName +
							'</td><td>' + value.lastName + '</td><td>' +
								value.team.name + '</td></tr>';
						$('#playersTable > tbody').append(tableRow);
					});

				});
			});
}