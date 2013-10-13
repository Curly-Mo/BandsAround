$.ajax({
    url : "/initchannel",
    dataType : 'json',
    error : function() {
        alert("Error Occured =(");
    },
    success : function(data) {
        var channelKey = data.channelKey
        var token = data.token;
        channel = new goog.appengine.Channel(token);
        socket = channel.open();
        socket.onopen = onOpened;
        socket.onmessage = onMessage;
        socket.onerror = onError;
        socket.onclose = onClose;
    }
});

function onOpened() {
    //alert("Channel opened!");
}

function onMessage(msg) {
    var data = JSON.parse(msg.data);
    $("<li><a href='#' data-src='"+data.streamURL+"'>"+data.artist+" - "+data.title+"</a></li>").appendTo('#tracks');  
    if (initialized){
        updatePlaylist();
    }else{
        initPlaylist();
    }
    setTimeout(function () {
        myScroll.refresh();
    }, 0);
}

function onClose() {
    //alert("Channel closed!");
}

function onError() {
    //alert("Errored!");
}

sendMessage = function(path, opt_param) {
    path += '?g=' + state.game_key;
    if (opt_param) {
        path += '&' + opt_param;
    }
    var xhr = new XMLHttpRequest();
    xhr.open('POST', path, true);
    xhr.send();
};

var startList=1;
var initialized=false;
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
    initialized=true;
}

function updatePlaylist(){
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
}