define ( [
    'backbone',
    'tmpl/scoreboard',
    'collections/scores'
], function (
    Backbone,
    tmpl,
    ScoresCollection
) {
    var View =  Backbone.View.extend({
        template: tmpl,
        scores: ScoresCollection,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            var self = this;
            var rend = null;
            this.scores.fetch( {
                success: function( model, data ) {
                    rend = self.scores.toJSON();
                },
                error: function( model, data ) {
                    rend = { 'login': 'HOLY', 'score': 'SHOT' };
                }
            } );

            this.$el.html( this.template( rend ) );
            this.$('body').append( this.el );
//                i++;
//            } );
            return this;
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });
    return new View;
} );