var IE = false;
for (var i = 5; i <= 8; i++) {
	if(navigator.userAgent.indexOf("MSIE " + i)>=0) {
		IE = true;
	}
}

if(IE){

	$(function(){
		console.log(navigator.userAgent);
		
		$("<div>")
			.css({
				'position': 'absolute',
				'top': '0px',
				'left': '0px',
				backgroundColor: 'black',
				'opacity': '0.75',
				'width': '100%',
				'height': $(window).height(),
				zIndex: 5000
			})
			.appendTo("body");
			
		$("<div><p><br /><strong>Leider wird Ihr Browser von der Anwendung nicht unterst&uuml;tzt.</strong><br /><br />Wenn Sie die Anwendung verwenden wollen <a href='https://www.google.com/intl/de/chrome/browser/'>aktualisieren Sie Ihren Browser</a>.</p>")
			.css({
				backgroundColor: 'white',
				'top': '50%',
				'left': '50%',
				marginLeft: -210,
				marginTop: -100,
				width: 410,
				paddingRight: 10,
				height: 200,
				'position': 'absolute',
				zIndex: 6000
			})
			.appendTo("body");
	});		
}