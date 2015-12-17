define ( [
    'backbone',
    'views/main'
], function (
    Backbone,
    viewMain
) {
    var viewManager = Backbone.View.extend( {
        views: [],
        activeView: viewMain,
//        events: {
//            'click .js-back-button': 'back'
//        },

        addViews: function( viewArray ) {
            for ( var view in viewArray ) {
                this.views.push( view );
                this.listenTo( view, 'show', function() { this.activeView.hide; this.activeView = view; } )
            }
        }
    } );

    return new viewManager;

} );