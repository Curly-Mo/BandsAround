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
    
    // Load in a track on click
    $('ol li').click(function(e) {
      e.preventDefault();
      $(this).addClass('playing').siblings().removeClass('playing');
      audio.load($('a', this).attr('data-src'));
      audio.play();
    });
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

  
  
  
    var url = 'https://api.soundcloud.com/users.json?client_id=84a2392830bf4d00a8fb7557613a36e6&q=datsik&order=hotness&limit=1';
    $.getJSON(url, function(data) {
        $.each(data, function(index, user) {
            var url = 'https://api.soundcloud.com/users/'+user.id+'/tracks.json?client_id=84a2392830bf4d00a8fb7557613a36e6&order=hotness';
            $.getJSON(url, function(data) {
                $.each(data, function(index, track) {
                    $("<li><a href='#' data-src='http://api.soundcloud.com/tracks/"+track.id+"/stream?client_id=84a2392830bf4d00a8fb7557613a36e6'>"+track.title+" - "+track.user.username+" !!! "+user.username+"</a></li>").appendTo('#tracks');   
                    audio.load("http://api.soundcloud.com/tracks/"+track.id+"/stream?client_id=84a2392830bf4d00a8fb7557613a36e6");
                });
        
        
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
            
                });
                
            });
        });
        
    });
    
    

    $.getJSON("http://api.jambase.com/search?zip=08054&radius=75&apikey=hwxvvh2mtphmygtwce4vtmfm",

            function(data){

            for (i=0; i<data.results.length; i++){

                document.write(data.results[i]);
            }
            });

});