if(navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(foundLocation, noLocation);
}

function foundLocation(position) {
	sessionStorage.setItem('latitude', lat);
	sessionStorage.setItem('longitude', lng);
}

function noLocation(){
	
}