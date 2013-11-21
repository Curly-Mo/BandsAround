document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

var    gallery;

gallery = new SwipeView('#wrapper', {                 
    text: null,
    numberOfPages: 3,
    snapThreshold: null,
    hastyPageFlip: false,
    loop: false 
    });

// Load initial data
settings = document.createElement('span');
settings.setAttribute("id", "settings");
gallery.masterPages[1].appendChild(settings);

mainPage = document.createElement('span');
mainPage.setAttribute("id", "mainPage");
gallery.masterPages[2].appendChild(mainPage);


info = document.createElement('span');
info.setAttribute("id", "info");
gallery.masterPages[0].appendChild(info)
gallery.goToPage(1);

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
        sessionStorage.setItem('mainPageLoaded', true);
        $("#listview-wrapper").addClass("ui-page");
        $("#tracks").listview();
        $("#listview-wrapper").removeClass("ui-page");
        //if(sessionStorage && sessionStorage.tracks){
        //    loadFromSessionStorage();
        //}else{
            loadFromLocalStorage();
            requestTracks(0);
        //}
    }, 0);
    setTimeout(function(){
        onResize();
        $(window).resize(function() {onResize();});
    }, 1000);
});

$('#info').load('info.html', function() {
    setTimeout(function () {
        $("#info").trigger("create");
        $("#artist-detail").attr("data-role","listview");
        $("#artist-collapsible").addClass("ui-page");
        $("#artist-detail").listview();
        $("#artist-collapsible").removeClass("ui-page");
        init_infoScroll();
        loadInfo();
        $("div:jqmData(role='collapsible')").each(function(){
            $(this).bind( "collapse", function(){infoScroll.refresh();}); 
            $(this).bind( "expand", function(){infoScroll.refresh();}); 
        });
        refreshTheme();
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
    $(".data-t, .ui-btn-down-b, .ui-btn-down-c, .ui-btn-down-d, .ui-btn-down-e")
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
    
    if(theme == "a"){
        $("#soundcloud-logo").attr("src", "http://developers.soundcloud.com/assets/powered_by_large_white-de881ecad8561c2131e7676fa9e92738.png");
        $("#echonest-logo").attr("src", "http://the.echonest.com/static/img/logos/EN_P_on_Dark_Transparent.png");
        $("#lastfm-logo").attr("src", "http://cdn.last.fm/flatness/badges/lastfm_black.gif");
    }else if(theme == "c"){
        $("#soundcloud-logo").attr("src", "http://developers.soundcloud.com/assets/powered_by_large_black-e832a12f64d6ce6d2da947494e210e4d.png");
        $("#echonest-logo").attr("src", "http://the.echonest.com/static/img/logos/EN_P_on_Light_Transparent.png");
        $("#lastfm-logo").attr("src", "http://cdn.last.fm/flatness/badges/lastfm_grey.gif");
    }
    gallery.refreshSize();
}

function refreshTheme() {
    if($('input:radio[name=theme]:checked').val()=="dark") {
        changeTheme("a");
    }else{
        changeTheme("c");
    }
}

var widescreen=false;
function onResize(){
	if( $(window).width() > 1000 && !widescreen){
		widescreen = true;
	  	$("#scroller-wrapper").width("40%");
		$(".audiojs").width("40%");
		$("#info-scroller").width("60%");
		$("#info").appendTo(gallery.masterPages[2]);
		$("#info-scroller").css("right",-$("#info-scroller").width());
		$("#info-scroller").animate({"right":"0"});
		gallery.updatePageCount(2);
	    if(gallery.page==2){gallery.goToPage(1);}
	    $("#goToSettingsButton").show();
	}else if( $(window).width() < 1000 && widescreen){
		widescreen = false;
		$("#info-scroller").animate({"right":"0"});
		$("#scroller-wrapper").width("100%");
		$(".audiojs").width("100%");
		$("#info-scroller").width("100%");
		$("#info").appendTo(gallery.masterPages[0]);
	    gallery.updatePageCount(3);
	    $("#goToSettingsButton").hide();
	}
}