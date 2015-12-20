//var balls = require(['balls']);

define ( [
    'backbone',
    'tmpl/rooms'
], function (
    Backbone,
    tmpl
) {
    var View =  Backbone.View.extend({
        template: tmpl,
        rooms: { rooms: ['room1', 'room2', 'room3', 'room4' ] },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template( this.rooms ) );
            return this;
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
    });
    return new View;
} );