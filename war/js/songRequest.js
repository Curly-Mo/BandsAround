var initialized=false;
requestTracks(0);
var pendingTracks = [];
var alreadyPlayedTracks = [];
var loadedTracks = [];

function requestTracks(attempt){
	if(attempt > 10 || attempt < -10){
		alert("Sorry =(\n" +
        		"I'm having trouble handling your request right now.");
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
	    		requestTracks(attempt-1);
	    		return;
	    	}
	    	storeTracks(data);
	    	loadTracks(30);
	    	$("#loading").hide();
        	updatePlaylist();
        	$("#listview-wrapper").addClass("ui-page");
        	$("#tracks").listview("refresh");
        	$("#listview-wrapper").removeClass("ui-page");
        	refreshTheme();
	    }
	});
}

function storeTracks(jsonData) {
	pendingTracks = JSON.parse(JSON.stringify(jsonData.tracks));
	try{
		sessionStorage.setItem("tracks",JSON.stringify(jsonData.tracks));
	}catch(e){
		
	}
}

function loadSongsFromStorage() {
	var tracks = JSON.parse(sessionStorage.getItem("tracks"));
	for (var i in tracks) {
	    var track = tracks[i];
	    $("<li><a href='#' data-src='"+track.streamURL+"'>"+track.artist+" - "+track.title+"</a></li>").appendTo('#tracks');  
	    loadedTracks.push(track);
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 100);
	updatePlaylist();
}

function loadSongFromStorage() {
	var tracks = JSON.parse(sessionStorage.getItem("tracks"));
	var track = tracks.shift();
	if(track){
		$("<li><a href='#' data-src='"+track.streamURL+"'>"+track.artist+" - "+track.title+"</a></li>").appendTo('#tracks');  
		//sessionStorage.setItem("tracks",JSON.stringify(tracks));
		loadedTracks.push(track);
	
		setTimeout(function () {
			myScroll.refresh();
		}, 100);
		updatePlaylist();
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
		    $("<li><a href='#' data-src='"+track.streamURL+"'>"
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
	$("#listview-wrapper").addClass("ui-page");
	$("#tracks").listview("refresh");
	$("#listview-wrapper").removeClass("ui-page");
	refreshTheme();
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
		e.preventDefault();
		$(this).addClass('playing').siblings().removeClass('playing');
		audio.load($('a', this).attr('data-src'));
		audio.play();
		$("#title").text($('li.playing').text());
    	manageList(element);
    	setTimeout(function () {
    		myScroll.scrollToElement(element, 1000);
    	}, 100);
    });
    initialized=true;
}

function manageList(elementPlaying){
	var index = $("#tracks li:visible").index(elementPlaying);
	//if( index > 15){
	//	unloadTracksFromBeginning(index - 15);
	//}
	if( index > $("#tracks li").length - 15){
		loadTracks(index - ($("#tracks li").length - 15));
	}
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

