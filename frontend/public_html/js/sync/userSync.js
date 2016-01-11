define( [
], function(
){
    var methodMap = {
        'create': 'POST',
        'update': 'PUT',
        'patch':  'PATCH',
        'delete': 'DELETE',
        'read':   'GET'
    };

    var sync = function( method, model, options ){
        var type = methodMap[ method ];
        var params = {
            type: type,
            dataType: 'json'
        };

        if( method === 'create' ) {
            params.url = '/signup/';
            params.data = options.data;
        }

        if( method === 'read' ) {
            if( options.data ){
                params.url = '/signin/';
                params.data = options.data;
                params.type = 'POST';
            } else {
                params.url = '/info';
                params.type = 'GET';
            }
        }

        return Backbone.ajax( _.extend( params, options ) );

    };

    return sync;

});
