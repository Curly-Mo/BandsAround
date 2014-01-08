var initialized=false;
var pendingTracks = [];
var allTracks = [];
var alreadyPlayedTracks = [];
var loadedTracks = [];


function requestTracks(attempt){
	sessionStorage.setItem("attempt", attempt);
    if(attempt >= 10 || attempt < -1){
        alert("Oh no! Something went wrong. =(\n");
        gallery.prev();gallery.prev();
        $("#loading").hide();
        return;
    }
    if (localStorage && localStorage.zip && localStorage.detect_location==="false") {
        zip=localStorage.getItem("zip");
        getLatLngFromAddress(zip);
    }else{
        if(sessionStorage.getItem('latitude')){
            sessionStorage.setItem('tracksRequestedWithLocation', true);
        }
        processRequest();
    }
}


function processRequest(){
    //var dayStart = 0;
    //var dayEnd=Math.max(2, 7 - attempt);
	var attempt = parseInt(sessionStorage.getItem("attempt"));
    var tracksPerArtist = 5;
    var maxEvents = 100;
    var page = 1;
    var radius = 32 + attempt*10;
    var retry;
    var artist;
    var latitude = sessionStorage.getItem("latitude");
    var longitude = sessionStorage.getItem("longitude");
    
    if(attempt!=0){
        retry = "true";
    }
    
    if (localStorage) {
        if (localStorage.radius) {
            radius = (parseInt(localStorage.getItem("radius"))*1.6) + attempt*10;
        }
        if(localStorage.detect_location==="false"){
			latitude = sessionStorage.getItem("ziplatitude");
    		longitude = sessionStorage.getItem("ziplongitude");
		}
        /*if (localStorage.dates) {
            dates=getDateRange(localStorage.getItem("dates"));
            dayStart=dates[0];
            dayEnd=parseInt(dates[1]);
            if(dayEnd>2){
                dayEnd=Math.max(2, parseInt(dates[1]) - attempt);
            }
        }*/
    }

    $.ajax({
        url : "/asynctracksrequest",
        dataType : 'json',
        data: {tracksPerArtist : tracksPerArtist, radius: radius, latitude: latitude, longitude: longitude, maxEvents: maxEvents, page: page, retry: retry, artist: artist/*, dayStart : dayStart, dayEnd : dayEnd*/},
        error : function() {
            requestTracks(attempt-1);
        },
        success : function(data) {
            if(data.tracks==""){
                $("#loading").text="Could not find any concerts. Expanding your search...";
                requestTracks(attempt+1);
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
            var lineup = track.lineup;
            if(typeof lineup !== "undefined"){
                lineup = lineup.join("||");
            }
            if(typeof artworkURL === "undefined"){
                artworkURL="/img/wagon80.png";
            }
            $("<li name='"+track.artist+"' data-track='"+track.title+"' data-date='"+track.date+"' data-ticketUrl='"+track.ticketUrl+"' data-venue='"+track.venue.name+"' data-venueLat='"+track.venue.latitude+"' data-venueLng='"+track.venue.longitude+"' data-venuePhone='"+track.venue.phone+"' data-eventTitle='"+track.eventTitle+"' data-lineup='"+lineup+"' data-headliner='"+track.headliner
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

function initAudioJS(){
    var a = audiojs.createAll({
        trackEnded: function() {
          var next = $('#tracks li.playing').next();
          if (next.length) next.click();
        }
      });
      audio = a[0];
}
function updatePlaylist(){
    if(!initialized){
        // Setup the player to autoplay the next track
    	
        // Load in the first track
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
       	if(!isMobile()){
       		if(audio.settings.useFlash) {
       			window.setTimeout('audio.play()', 1000);
       		} else {
       			audio.play();
                try{
                	loadInfo();
                } catch (e) {}
       		}
       	}
    }
        
    // Load in a track on click
    $('#tracks li').unbind().click(function(e) {
        var element = this;
        var index = $("#tracks li").index(this);
        e.preventDefault();
        $(this).addClass('playing').siblings().removeClass('playing');
        $(this).addClass('ui-focus').siblings().removeClass('ui-focus');
        //$(this).addClass('ui-btn-active').siblings().removeClass('ui-btn-active');
        //refreshListview();
        audio.load($('a', this).attr('data-src'));
        audio.play();
        ga('send', 'event', 'play', $(this).attr('name'), $(this).attr('data-track'), 1);
        //$("#title").text($('li.playing').text());    
        manageList(index);
        $(".audiojs").removeClass("error");
        setTimeout(function () {
            myScroll.scrollToElement(element, 1000);
            try{
            	loadInfo();
            } catch (e) {}
            //sessionStorage.setItem("tracks", JSON.stringify(allTracks.slice(0,allTracks.length-index)));
            //localStorage.setItem("tracks", JSON.stringify(allTracks.slice(0,allTracks.length-index+1)));
        }, 100);
    });
    initialized=true;
}

function manageList(index){
    //if( index > 15){
    //    unloadTracksFromBeginning(index - 15);
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
    if(myScroll){
        myScroll.refresh();
    }
}

function getLatLngFromAddress(address){
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var latitude = results[0].geometry.location.lat();
            var longitude = results[0].geometry.location.lng();
            var formattedAddress = results[0].formatted_address;
            sessionStorage.setItem("ziplatitude", latitude);
            sessionStorage.setItem("ziplongitude", longitude);
            processRequest();
            localStorage.setItem("zip", formattedAddress);
            $("#zip").val(formattedAddress);
        } 
    }); 
}

function isMobile(){
	if(navigator.userAgent.match(/ipad|iphone|ipod|android|blackberry|opera mini|iemobile/i)){
		return true;
	}
	return false;
}
