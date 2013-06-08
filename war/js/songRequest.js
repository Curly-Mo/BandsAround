var initialized=false;
var pendingTracks = [];
var allTracks = [];
var alreadyPlayedTracks = [];
var loadedTracks = [];


function requestTracks(attempt){
	if(attempt > 10 || attempt < -10){
		alert("Oh no! Something went wrong. =(\n" +
        		"I'm having some issues with getting your location.\n" +
        		"Try turning off 'Auto Detect Location' and manually entering your Zipcode.");
        gallery.goToPage(2);
        $("#loading").hide();
		return;
	}
	var dayStart = 0;
	var dayEnd=Math.max(2, 7 - attempt);
	var tracksPerArtist = Math.max(1, 5 - attempt);
	var radius = 15 - attempt;
	var zip;
	var retry;
	if(attempt!=0){
		retry = "true";
	}
		
    if (localStorage) {
		if (localStorage.zip && localStorage.detect_location==="false") {
			zip=localStorage.getItem("zip");
		}
		if (localStorage.radius) {
			radius = parseInt(localStorage.getItem("radius")) - attempt;
		}
    	if (localStorage.dates) {
    		dates=getDateRange(localStorage.getItem("dates"));
    		dayStart=dates[0];
    		dayEnd=parseInt(dates[1]);
    		if(dayEnd>2){
    			dayEnd=Math.max(2, parseInt(dates[1]) - attempt);
    		}
		}
    }
	
	$.ajax({
	    url : "/asynctracksrequest",
	    dataType : 'json',
	    data: {dayStart : dayStart, dayEnd : dayEnd, tracksPerArtist : tracksPerArtist, radius: radius, zip: zip, retry: retry},
	    error : function() {
	        /*alert("Sorry =(\n" +
	        		"Server is busy today. I can't handle such large requests.\n" +
	        		"I'm working on fixing this. In the meantime you can reduce the Radius or Date Range on the settings page.");
	        gallery.goToPage(2);
	        $("#loading").hide();*/
	    	$("#loading").innerHtml="Wow, there are a lot of upcoming concerts. Still thinking...";
	    	requestTracks(attempt+1);
	    },
	    success : function(data) {
	    	if(data.tracks==""){
	    		$("#loading").innerHtml="Could not find any concerts. Expanding your search...";
	    		requestTracks(attempt-2);
	    		return;
	    	}
	    	storeTracks(data);
	    	loadTracks(30);
	    	$("#loading").hide();
        	updatePlaylist();
        	refreshListview();
	    }
	});
}

function storeTracks(jsonData) {
	var tracks = JSON.stringify(jsonData.tracks);
	allTracks = JSON.parse(tracks);
	pendingTracks = allTracks;
	setTimeout(function () {
		sessionStorage.setItem("tracks",tracks);
		localStorage.setItem("tracks",tracks);
    }, 0);
}

function loadFromSessionStorage() {
	allTracks = JSON.parse(sessionStorage.getItem("tracks"));
	pendingTracks = allTracks;
	loadTracks(30);
	$("#loading").hide();
	updatePlaylist();
	refreshListview();
}

function loadFromLocalStorage() {
	if(localStorage && localStorage.tracks){
		allTracks = JSON.parse(localStorage.getItem("tracks"));
		var today = new Date();
		var len = allTracks.length
		while (len > 0) {
			len--;
			var trackDate = new Date(allTracks[len].date);
			if(trackDate > today){
				pendingTracks = allTracks;
				loadTracks(1);
				updatePlaylist();
				refreshListview();
				return;
			}
			pendingTracks.pop();
		}
	}
}

function loadTracks(numToLoad) {
	for (var i = 0; i < numToLoad; i++) {
		if(i < pendingTracks.length){
			var track = pendingTracks.pop();
			var artworkURL = track.artwork;
			if(typeof artworkURL === "undefined"){
				artworkURL="/img/wagon80.png";
			}
		    $("<li id='"+track.artist_id+"' name='"+track.artist+"' data-venue='"+track.venue+"' data-date='"+track.date
		    		+"'><a href='#' data-src='"+track.streamURL+"'>"
		    		+"<img src="+artworkURL+">"
		    		+"<h3>"+track.artist+"</h3>"
		    		+"<p>"+track.title+"</p>"
		    		+"</a></li>")
		    		.appendTo('#tracks');  
		    loadedTracks.push(track);
		}
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 100);
	updatePlaylist();
	refreshListview();
}

function unloadTracksFromEnd(numToUnload) {
	for (var i = 0; i < numToUnload; i++) {
		var track;
		$('#tracks li:last-child').remove();
		track = loadedTracks.pop();
		pendingTracks.push(track);
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 100);
}

function unloadTracksFromBeginning(numToUnload) {
	for (var i = 0; i < numToUnload; i++) {
		var track;
		console.log("hiding " + $('#tracks li:visible:first').text());
		$('#tracks li:visible:first').hide();
		track = loadedTracks.shift();
		alreadyPlayedTracks.push(track);
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 100);
}

function updatePlaylist(){
	if(!initialized){
	    // Setup the player to autoplay the next track
	    var a = audiojs.createAll({
	      trackEnded: function() {
	        var next = $('#tracks li.playing').next();
	        if (next.length) next.click();
	        loadInfo(next.attr("name"),next.attr("data-venue"),next.attr("data-date"));
	      }
	    });
	
	    // Load in the first track
	    audio = a[0];
	        first = $('#tracks a').attr('data-src');
	    $('#tracks li').first().addClass('playing');
	    audio.load(first);
	    $("#title").text($('li.playing').text());
	
	    // Keyboard shortcuts
	    $(document).keydown(function(e) {
	      var unicode = e.charCode ? e.charCode : e.keyCode;
	         // right arrow
	      if (unicode == 39) {
	        var next = $('li.playing').next();
	        if (next.length) next.click();
	        // back arrow
	      } else if (unicode == 37) {
	        var prev = $('li.playing').prev();
	        if (prev.length) prev.click();
	        // spacebar
	      } else if (unicode == 32) {
	        audio.playPause();
	      }
	    })    
	    audio.playPause();
	}
	    
    // Load in a track on click
    $('#tracks li').unbind().click(function(e) {
    	var element = this;
    	var index = $("#tracks li").index(this);
		e.preventDefault();
		$(this).addClass('playing').siblings().removeClass('playing');
		audio.load($('a', this).attr('data-src'));
		audio.play();
		$("#title").text($('li.playing').text());	
    	manageList(index);
    	setTimeout(function () {
    		myScroll.scrollToElement(element, 1000);
    	    var playing = $('li.playing');
    	    loadInfo(playing.attr("name"),playing.attr("data-venue"),playing.attr("data-date"));
    		//sessionStorage.setItem("tracks", JSON.stringify(allTracks.slice(0,allTracks.length-index)));
    		//localStorage.setItem("tracks", JSON.stringify(allTracks.slice(0,allTracks.length-index+1)));
    	}, 100);
    });
    initialized=true;
}

function manageList(index){
	//if( index > 15){
	//	unloadTracksFromBeginning(index - 15);
	//}
	if( index > $("#tracks li").length - 15){
		loadTracks(index - ($("#tracks li").length - 15));
	}
}

function refreshListview(){
	$("#listview-wrapper").addClass("ui-page");
	$("#tracks").listview("refresh");
	$("#listview-wrapper").removeClass("ui-page");
	refreshTheme();
}

function getDateRange(dates){
	var dateRange;
	if(dates==="tonight"){
		dateRange=[0,0];
	}else if(dates==="weekend"){
		var today = new Date().getDay();
		if(today == 0){
			dateRange=[0,0];
		}else if(today == 6){
			dateRange=[0,1];
		}else if(today == 5){
			dateRange=[0,2];
		}else {
			friday = 5 - today;
			dateRange=[friday,2];
		}
	}else if(dates==="week"){
		dateRange=[0,7];
	}else if(dates==="month"){
		dateRange=[0,30];
	}else if(dates==="3month"){
		dateRange=[0,90];
	}
	
	return dateRange;
}

function clearPlaylist(){
	$('#tracks li:not(.playing)').remove();
	myScroll.refresh();
}

