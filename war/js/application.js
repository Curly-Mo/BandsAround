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
    	$('[data-noswipe-wrapper]').find("*").attr("data-noswipe", true);
    	init_settingsScroll();
    	loadSettingsValues();
    	refreshTheme();
    }, 100);
});

$('#mainPage').load('mainPage.html', function() {
    setTimeout(function () {
    	init_myScroll();
    	$("#listview-wrapper").addClass("ui-page");
    	$("#tracks").listview();
    	$("#listview-wrapper").removeClass("ui-page");
    }, 100);
});

$('#info').load('info.html', function() {
    setTimeout(function () {
    	init_infoScroll();
    }, 100);
});

function changeTheme(theme){
    $(".ui-body-a, .ui-body-b, .ui-body-c, .ui-body-d, .ui-body-e")
		    .removeClass('ui-body-a ui-body-b ui-body-c ui-body-d ui-body-e')
			.addClass('ui-body-' + theme)
			.attr('data-theme', theme);
    $(".ui-btn-down-a, .ui-btn-down-b, .ui-btn-down-c, .ui-btn-down-d, .ui-btn-down-e")
		    .removeClass('ui-btn-down-a ui-btn-down-b ui-btn-down-c ui-btn-down-d ui-btn-down-e')
			.addClass('ui-btn-down-' + theme);
	//reset all the buttons widgets
    $("#body").find('.ui-btn')
			.removeClass('ui-btn-up-a ui-btn-up-b ui-btn-up-c ui-btn-up-d ui-btn-up-e ui-btn-hover-a ui-btn-hover-b ui-btn-hover-c ui-btn-hover-d ui-btn-hover-e')
			.addClass('ui-btn-up-' + theme)
			.attr('data-theme', theme);
    //reset the page widget
    $("#body").removeClass('ui-body-a ui-body-b ui-body-c ui-body-d ui-body-e')
			.addClass('ui-body-' + theme)
			.attr('data-theme', theme);
    
    gallery.refreshSize();
}

function refreshTheme() {
	if($('input:radio[name=theme]:checked').val()=="dark") {
		changeTheme("a");
	}else{
		changeTheme("c");
	}
}
