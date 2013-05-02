document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

var	gallery;

gallery = new SwipeView('#wrapper', { numberOfPages: 3 });

// Load initial data
settings = document.createElement('span');
settings.innerHTML = "Settings will go here.";
gallery.masterPages[0].appendChild(settings);

mainPage = document.createElement('span');
mainPage.innerHTML = "<h1 id='title'></h1><audio preload='none'></audio><div id='scroller-wrapper'><div id='scroller'><ol id='tracks'><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li></ol></div></div>";
gallery.masterPages[1].appendChild(mainPage);

info = document.createElement('span');
info.innerHTML = "Information about the band/artist and upcoming event will go here.";
gallery.masterPages[2].appendChild(info)