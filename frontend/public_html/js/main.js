require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery",
        underscore: "lib/underscore",
        backbone: "lib/backbone"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        }
    }
});

define([
    'backbone',
    'router'
], function(
    Backbone,
    router
){
    Backbone.history.start();

//    // костыль, который отменяет все идущие ajax-запросы, чтобы не вылезала 405
//        $.xhrPool = []; // array of uncompleted requests
//        $.xhrPool.abortAll = function() { // our abort function
//            $(this).each(function(idx, jqXHR) {
//                jqXHR.abort();
//            });
//            $.xhrPool.length = 0
//        };
//
//        $.ajaxSetup({
//            beforeSend: function(jqXHR) { // before jQuery send the request we will push it to our array
//                $.xhrPool.push(jqXHR);
//            },
//            complete: function(jqXHR) { // when some of the requests completed it will splice from the array
//                var index = $.xhrPool.indexOf(jqXHR);
//                if (index > -1) {
//                    $.xhrPool.splice(index, 1);
//                }
//            }
//        });

});