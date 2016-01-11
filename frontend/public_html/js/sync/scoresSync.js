define( [
 'collections/scores',
 'models/player'
], function(
 scoresCollection,
 userModel
){
    var methodMap = {
        'read':   'GET'
    };

    var urlMap = {
        'read': '/info/10',
    };

    var sync = function( method, model, options ){
        var type = methodMap[ method ];
        var params = {
            type: type,
            dataType: 'json',
            contentType: 'application/json',
            url: urlMap[ method ],
        };

        if( method === 'read' ) {

        }

        return Backbone.ajax( _.extend( params, options ) );

    };

    return sync;

});
