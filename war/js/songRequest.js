initialize();
requestTracks();
var pendingTracks = [];
var loadedTracks = [];

function initialize(){
	var dayStart = 0;
	var dayEnd = 0;
	var tracksPerArtist = 1;
	$.ajax({
	    url : "/bandwagon",
	    dataType : 'json',
	    data: {dayStart : dayStart, dayEnd : dayEnd, tracksPerArtist : tracksPerArtist},
	    error : function() {
	        alert("Error Occured =(");
	    },
	    success : function(data) {
	    	parseResponse(data);
	    	$("#loading").hide();
        	initPlaylist();
        	audio.playPause();
	    }
	});
}

function requestTracks(){
	var dayStart = 0;
	var dayEnd = 7;
	var tracksPerArtist = 5;
	var radius = "20";
	var zip = "00000"
	$.ajax({
	    url : "/bandwagon",
	    dataType : 'json',
	    data: {dayStart : dayStart, dayEnd : dayEnd, tracksPerArtist : tracksPerArtist, radius: radius, zip: zip},
	    error : function() {
	        alert("Error Occured =(");
	    },
	    success : function(data) {
	    	storeTracks(data);
	    	loadTracks(10);
	    	//loadSongsFromStorage();
	    }
	});
}

function parseResponse(jsonData) {
	for (var i in jsonData.tracks) {
	    var track = jsonData.tracks[i];
	    $("<li><a href='#' data-src='"+track.streamURL+"'>"+track.artist+" - "+track.title+"</a></li>").appendTo('#tracks');  
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 1000);
	updatePlaylist();
}

function storeTracks(jsonData) {
	pendingTracks = JSON.parse(JSON.stringify(jsonData.tracks));
	sessionStorage.setItem("tracks",JSON.stringify(jsonData.tracks));
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
	}, 1000);
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
		}, 1000);
		updatePlaylist();
	}
}

function loadTracks(numToLoad) {
	for (var i = 0; i < numToLoad; i++) {
		if(i < pendingTracks.length){
			var track = pendingTracks.pop();
		    $("<li><a href='#' data-src='"+track.streamURL+"'>"+track.artist+" - "+track.title+"</a></li>").appendTo('#tracks');  
		    loadedTracks.push(track);
		}
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 100);
	updatePlaylist();
}

function unloadTracks(numToUnload) {
	for (var i = 0; i < numToUnload; i++) {
		var track;
		$('#tracks li:last-child').remove();
		track = loadedTracks.pop(track);
		pendingTracks.push(track);
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 100);
}

function initPlaylist(){
    // Setup the player to autoplay the next track
    var a = audiojs.createAll({
      trackEnded: function() {
        var next = $('ol li.playing').next();
        if (next.length) next.click();
      }
    });

    // Load in the first track
    audio = a[0];
        first = $('ol a').attr('data-src');
    $('ol li').first().addClass('playing');
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
	    
    // Load in a track on click
    $('ol li').click(function(e) {
      e.preventDefault();
      $(this).addClass('playing').siblings().removeClass('playing');
      audio.load($('a', this).attr('data-src'));
      audio.play();
      $("#title").text($('li.playing').text());
      myScroll.scrollToElement(this, 1000)
    });
}

function updatePlaylist(){
    // Load in a track on click
    $('ol li').click(function(e) {
    	e.preventDefault();
    	$(this).addClass('playing').siblings().removeClass('playing');
    	audio.load($('a', this).attr('data-src'));
    	audio.play();
    	$("#title").text($('li.playing').text());
    	myScroll.scrollToElement(this, 1000)
    });
}
