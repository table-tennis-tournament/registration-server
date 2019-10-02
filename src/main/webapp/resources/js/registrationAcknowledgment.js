'use strict';

/* globals $, hideProgress */

$(function() {
	var playerData = JSON.parse(sessionStorage.getItem('playerData'));
	buildSoloPlayerTable(playerData.players);
	hideProgress();
});

function buildSoloPlayerTable(playerData) {
	var tableRow = '';

	$.each(playerData, function(i, player) {
		tableRow += '<tr><td>' + player.firstName + '</td><td>'
				+ player.lastName + '</td><td>' + player.birthDate + '</td>';
		$.each(player.competitions, function(index, competition) {
			tableRow += '<td>' + competition.name + '</td>';
		});
		for (var diff = 0; diff < 4 - player.competitions.length; diff++) {
			tableRow += '<td></td>';
		}
		tableRow += '</tr>';
	});
	$('#playersTable > tbody').append(tableRow);
}

function buildDoublePlayerTable(playerData) {
	var tableRow = '';

	$.each(playerData, function(i, player) {
		tableRow += '<tr><td>' + player.firstPlayer.firstName + ' '
				+ player.firstPlayer.lastName + '</td><td>'
				+ player.secondPlayer.firstName + ' '
				+ player.secondPlayer.lastName + '</td><td>'
				+ player.disciplineName + '</td>';
		tableRow += '</tr>';
	});
	$('#playersTableDouble > tbody').append(tableRow);
}