if ( Modernizr.csstransforms ) {
  window.mySwipe = new Swipe(document.getElementById('slider'), {
		startSlide: 1,
		speed: 300,
		callback: function(event, index, elem) {
		  // do something cool

		}
  });
}