var myScroll;
function loaded() {
	myScroll = new iScroll('scroller', {
		hScroll: false,
		vScroll: true,
		x: 0,
		y: 0,
		bounce: false,
		bounceLock: false,
		momentum: true,
		lockDirection: true,
		useTransform: true,
		useTransition: true,
		
		snap: false,
		snapThreshold: 1,
		
		onRefresh: null,
		onBeforeScrollStart: function (e) { e.preventDefault(); },
		onScrollStart: null,
		onBeforeScrollMove: null,
		onScrollMove: null,
		onBeforeScrollEnd: null,
		onScrollEnd: null,
		onTouchEnd: null,
		onDestroy: null
			});
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

/* * * * * * * *
 *
 * Use this for high compatibility (iDevice + Android)
 *
 */
document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
/*
 * * * * * * * */


/* * * * * * * *
 *
 * Use this for iDevice only
 *
 */
//document.addEventListener('DOMContentLoaded', loaded, false);
/*
 * * * * * * * */


/* * * * * * * *
 *
 * Use this if nothing else works
 *
 */
//window.addEventListener('load', setTimeout(function () { loaded(); }, 200), false);
/*
 * * * * * * * */