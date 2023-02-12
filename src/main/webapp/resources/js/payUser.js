'use strict';

/* globals $ */

var treg = {};

treg.User = {};

treg.User.PayUser = new function(){
		
	var internal = null;
	var internalCto = function(){
		
		this.checkedPlayers = null;
		this.userdata = null;
		
		this.jqInvoiceNumber = null;
		
		this.init = function(){
			this.jqOnConfirmPayment = $('#confirmPayment');
			this.jqInvoiceNumber = $('#invoiceNumber');
			this.jqOnConfirmPayment.click(this.onConfirmPayment.bind(this));
			this.userdata = {
					user: sessionStorage.getItem('username'),
					password: sessionStorage.getItem('password').trim()
			};
			this.loadPlayerData();
		};
		
		this.loadPlayerData = function(){
			$.getJSON(
					'player/light?user=' + this.userdata.user + '&password=' + this.userdata.password, this.onPlayerDataLoaded.bind(this));
					
		};
		
		this.onPlayerDataLoaded = function(data) {
			$('#playerName').typeahead(
							{
								source : data,
								display: 'name',
								name:'Nachname',
								highlighter : function(item) {
									return "<span>" + item + "</span>";
								} ,
								itemSelected :this.onDropdownItemSelected.bind(this)
							});
		};
		
		this.onDropdownItemSelected = function(item, id, third){
			this.loadPlayersByUserId(id);
		};
		
		this.onInvoiceClicked = function(){
			var invoiceId = this.jqInvoiceNumber.val().trim();
			this.loadPlayersByUserId(invoiceId);
		};
		
		this.loadPlayersByUserId = function(id){
			$.getJSON('player/searchByInvoice?user=' + this.userdata.user + '&password=' + this.userdata.password + '&invoiceNumber='+id, this.onGridDataLoaded.bind(this));
		};
		
		this.onGridDataLoaded = function(_data){
			$('#playersTable > tbody').empty();
			var tableRow = '';
			var sum = 0;
			$.each(_data, function(index, player) {
				var hasPaid = false;
				tableRow += '<tr><td>' + player.firstName + '</td><td>' +
					player.lastName + '</td><td>' + player.team.name + '</td>';
				$.each(player.competitions, function(index, competition) {
					var classForPaidStatus = '';
					if(competition.isWaitingList) {
						classForPaidStatus = 'waitlist';
					}
					if(competition.paid) {
						hasPaid = true;
						classForPaidStatus = 'success';
					}
					tableRow += '<td class="' + classForPaidStatus + '">' + competition.name + '</td>';
				});
				for (var diff = 0; diff < 4 - player.competitions.length; diff++) {
					tableRow += '<td></td>';
				}
				tableRow += '<td>'+player.payment+' €</td>';
				sum+= player.payment;
				var checked = '';
				console.log(hasPaid);
				if(hasPaid === true){
					checked = 'checked';
				}
				tableRow += '<td><input type="checkbox" id="isPayedCheckbox'+player.id+'" playerIndex="'+player.id+'" '+ checked+' class="treg-user-pay-checkbox"></td>';
				tableRow += '</tr>';
			});
			$('#playersTable > tbody').append(tableRow);
			$('#playersTable > tbody').append('<tr><td/><td/><td/><td/><td/><td/><td/><td>'+sum+' €</td><td/></tr>');
			this.registerCheckboxes();
			this.checkedPlayers = [];
		};
		
		this.registerCheckboxes = function(){
			$('.treg-user-pay-checkbox').off('click');
			$('.treg-user-pay-checkbox').on('click', this.onCheckboxSelected.bind(this));
		};
		
		this.onCheckboxSelected = function(_event){
			var playerId = $(_event.target).attr('playerindex');
			if(this.checkedPlayers[playerId] !== true){
				this.checkedPlayers[playerId] = true;
				return;
				
			}
			delete this.checkedPlayers[playerId];
		};
		
		this.onConfirmPayment = function(){
			var json = [];
			$.each(this.checkedPlayers, function(index, player) {
				if(player === true){
					json.push(index);
				}
			});
			var data = JSON.stringify(json);
			
			$.ajax({
				type : 'POST',
				url : 'player/pay?user=' + this.userdata.user + '&password=' + this.userdata.password.trim(),
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				async : false,
				data : data,
				success : this.onPaymentSuccess.bind(this)
			});
		};
		
		this.onPaymentSuccess = function(){
            let $treg = $('.treg-user-pay-checkbox:checked');
            $treg.attr('disabled', true);
			$treg.closest('tr').addClass('success');
		};
	};
	
	this.Init = function(){
		internal = new internalCto();
		internal.init();
	};
	
	this.OnSearchInvoiceClicked = function(){
		internal.onInvoiceClicked();
	}
};

$(function() {
	treg.User.PayUser.Init();
});
