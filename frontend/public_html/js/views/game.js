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
        size: 25,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template(  ) );
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