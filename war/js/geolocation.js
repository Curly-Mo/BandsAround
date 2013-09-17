if(sessionStorage.getItem('detect_location')!='false' && navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(foundLocation, noLocation, {timeout:1000});
}

function foundLocation(position) {
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	//sessionStorage.setItem('latitude', latitude);
	//sessionStorage.setItem('longitude', longitude);
	//sessionStorage.setItem('zip', position.address.postalCode);

	$.ajax({
	    url : "http://api.geonames.org/findNearbyPostalCodesJSON",
	    dataType : 'json',
	    data: {lat : latitude, lng : longitude, username : "CurlyMo"},
	    error : function() {
	       //alert("Error fetching zip from Lat/Long");
	    },
	    success : function(json) {
	    	zip = json.postalCodes[0].postalCode;
	    	sessionStorage.setItem('zip', zip);
	    	if(!sessionStorage.getItem('tracksRequestedWithLocation')){
	    		requestTracks(0);
	    		clearPlaylist();
	    	}
	    }
	});
}

function noLocation(){
	//alert("no location found");
}