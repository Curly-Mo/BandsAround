initialize();
requestTracks();

function initialize(){
	var days = 0;
	var limit = 1;
	$.ajax({
	    url : "/bandwagon",
	    dataType : 'json',
	    data: {days : days, limit : limit},
	    error : function() {
	        alert("Error Occured =(");
	    },
	    success : function(data) {
	    	parseResponse(data);
	    	$("#loading").hide();
        	initPlaylist();
	    }
	});
}

function requestTracks(){
	var days = 7;
	var limit = 5;
	var radius = "20";
	var zip = "00000"
	$.ajax({
	    url : "/bandwagon",
	    dataType : 'json',
	    data: {days : days, limit : limit, radius: radius, zip: zip},
	    error : function() {
	        alert("Error Occured =(");
	    },
	    success : function(data) {
	    	sessionStorage.setItem("tracks", data);
	    	parseResponse(data);
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

function storeAllTracks(jsonData) {
	for (var i in jsonData.tracks) {
	    var track = jsonData.tracks[i];
	    $("<li><a href='#' data-src='"+track.streamURL+"'>"+track.artist+" - "+track.title+"</a></li>").appendTo('#tracks');  
	}
	setTimeout(function () {
		myScroll.refresh();
	}, 1000);
	updatePlaylist();
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

    });
}
