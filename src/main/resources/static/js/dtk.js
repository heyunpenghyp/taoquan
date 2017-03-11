$(function () {
    var data;
//    var scripts = document.getElementsByTagName('script');
    var myScript = document.getElementById('counterJs');

    var queryString = myScript.src.replace(/^[^\?]+\??/, '');

    var params = parseQuery(queryString);
    function parseQuery(query) {
        var Params = new Object();
        if (!query) return Params; // return empty object
        var Pairs = query.split(/[;&]/);
        for (var i = 0; i < Pairs.length; i++) {
            var KeyVal = Pairs[i].split('=');
            if (!KeyVal || KeyVal.length != 2) continue;
            var key = unescape(KeyVal[0]);
            var val = unescape(KeyVal[1]);
            val = val.replace(/\+/g, ' ');
            Params[key] = val;
        }
        return Params;
    }

    data = {
        'r': 'counter/index',
        'ua': navigator.userAgent || '',
        'href': window.location.href || '',
        'sw': window.screen.width || '',
        'sh': window.screen.height || '',
        'referer': document.referrer || '',
	    'uid': params.u || '',
	    'pv': params.pv || '',
    };
    return $.ajax('http://counter.dataoke.com/index.php', {
        data: data,
        dataType: 'jsonp',
        async: false
    });
});
