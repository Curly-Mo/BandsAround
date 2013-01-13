var startList=1;
  $(function() { 
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
      
      var count = 0;
      var showElement = $('li.playing').prev()
      while (showElement.length && count < 2) {
    	count = count + 1;
        if (showElement.is(':hidden') ) {
			showElement.show();
			startList = startList - 1;
			$('#tracks').attr('start', startList);
        }
        showElement = showElement.prev()
   	  }	

      count = 0;
      var hideElement = $('li.playing').prev()
      while (hideElement.length && hideElement.is(':visible')){
    	count = count+1;
        if (count > 2){
        	hideElement.hide();
        	startList = startList + 1;
        	$('#tracks').attr('start', startList);
    	}
      	hideElement = hideElement.prev()
      }
      $("#title").text($('li.playing').text());

    });

});