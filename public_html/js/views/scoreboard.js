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
        console.log(this.scores.toJSON())
            this.$el.html( this.template( this.scores.toJSON() ) );
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