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
gallery.masterPages[1].appendChild(mainPage);

info = document.createElement('span');
info.setAttribute("id", "info");
gallery.masterPages[2].appendChild(info)


$('#settings').load('settings.html', function() {
    setTimeout(function () {
    	$("#settings").trigger("create");
    	$('[data-noswipe]').find("*").attr("data-noswipe", true);
    	init_settingsScroll();
    }, 100);
});

$('#mainPage').load('mainPage.html', function() {
    setTimeout(function () {
    	init_myScroll();
    }, 100);
});

$('#info').load('info.html', function() {
    setTimeout(function () {
    }, 100);
});


