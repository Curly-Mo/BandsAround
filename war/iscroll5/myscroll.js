var myScroll;
var settingsScroll;
var infoScroll;
function init_myScroll() {
    myScroll = new IScroll('#scroller', {
    	tap: true,
    	click: true,
    	bounce: false,
    	momentum: true,
    	keyBindings: true,
    	mouseWheel: true, 
    	scrollbars: true,
    	interactiveScrollbars: true
            });
}

function init_settingsScroll(){
    settingsScroll = new IScroll('#settings-scroller', {
    	click: true,
        bounce: false,
        momentum: true,     
    	scrollbars: true, 
    	mouseWheel: true, 
    	interactiveScrollbars: true
            });
}

function init_infoScroll(){
    infoScroll = new IScroll('#info-scroller', {
    	click: true,
        bounce: false,
        momentum: true,     
    	scrollbars: true, 
    	mouseWheel: true, 
    	interactiveScrollbars: true
            });
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

var previousTracks = [];
function scrollMove(){
    //if(this.y < -20*$("#tracks").children(":first").outerHeight() ){
        //previousTracks.push($('#tracks li:first-child').remove());
        //this.refresh();
        //console.log(previousTracks);
    //}
    if( this.dirY == 1 && this.y < (this.maxScrollY + 39)){
        loadTracks(10);
    }
    if( this.dirY == -1 && this.y > (-39) && loadedTracks.length > 50){
        unloadTracks(10);
    }
}

/* * * * * * * *
 *
 * Use this for high compatibility (iDevice + Android)
 *
 */
//document.addEventListener('DOMContentLoaded', function () { setTimeout(init_myScroll, 200); }, false);
/*
 * * * * * * * */


/* * * * * * * *
 *
 * Use this for iDevice only
 *
 */
//document.addEventListener('DOMContentLoaded', loaded, false);
/*
 * * * * * * * */


/* * * * * * * *
 *
 * Use this if nothing else works
 *
 */
//window.addEventListener('load', setTimeout(function () { loaded(); }, 200), false);
/*
 * * * * * * * */