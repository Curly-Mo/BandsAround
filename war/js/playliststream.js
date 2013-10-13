if (typeof(EventSource)!=="undefined") {
    var source = new EventSource('/eventstream');
} else {
    alert("Your browser is not supported. =(");
}

source.addEventListener('message', function(e) {
    var data = JSON.parse(e.data);
    $("<li><a href='#' data-src='"+data.url+"'>"+data.title+"</a></li>").appendTo('#tracks');   
    
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
    
}, false);

source.addEventListener('open', function(e) {
    // Connection was opened.
}, false);

source.addEventListener('error', function(e) {
    if (e.readyState == EventSource.CLOSED) {
        // Connection was closed.
    }
}, false);