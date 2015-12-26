define ( [
    'backbone',
    'tmpl/game',
    '../../canvas/game',
    '../../canvas/balls'
], function (
    Backbone,
    tmpl,
    gameCanvas,
    balls
) {
    var View =  Backbone.View.extend({
        template: tmpl,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template(  ) );
            $( '#balls-control' ).html( '' );
            clrscr();
            this.$foreCanvas = this.$( '.game__canvas-fore' )[0];
            this.$backCanvas = this.$( '.game__canvas-back' )[0];
            gameCanvas( this.$foreCanvas, this.$backCanvas, 'ws://localhost:8080/game/' );
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