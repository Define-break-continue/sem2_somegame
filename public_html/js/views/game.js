define ( [
    'backbone',
    'tmpl/game',
    '../../canvas/game'
], function (
    Backbone,
    tmpl,
    gameCanvas
) {
    var View =  Backbone.View.extend({
        template: tmpl,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
        alert( gameCanvas );
            this.$el.html( this.template(  ) );
            this.$foreCanvas = this.$( '.game__canvas-fore' )[0];
            this.$backCanvas = this.$( '.game__canvas-back' )[0];
            gameCanvas( this.$foreCanvas, this.$backCanvas );
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