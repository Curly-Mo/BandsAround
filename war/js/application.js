document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

var	gallery;

gallery = new SwipeView('#wrapper', { 				
	text: null,
	numberOfPages: 3,
	snapThreshold: null,
	hastyPageFlip: false,
	loop: true 
	});

// Load initial data
settings = document.createElement('span');
settings.setAttribute("id", "settings");
gallery.masterPages[0].appendChild(settings);

mainPage = document.createElement('span');
mainPage.setAttribute("id", "mainPage");
mainPage.innerHTML = "<!--<h1 id='title'></h1>--><audio data-noswipe preload='none'></audio><div id='scroller-wrapper'><div id='scroller'><ol data-role='listview' class='ui-listview' id='tracks'></ol></div></div>";
gallery.masterPages[1].appendChild(mainPage);

info = document.createElement('span');
info.setAttribute("id", "info");
info.innerHTML = "Coming soon! <br>Information about the band/artist and their upcoming event will go here.";
gallery.masterPages[2].appendChild(info)


$('#settings').load('settings.html', function() {
    setTimeout(function () {
    	$("#settings").trigger("create");
    	$('[data-noswipe]').find("*").attr("data-noswipe", true);
    	init_settingsScroll();
    }, 1000);
});


