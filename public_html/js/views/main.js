//var balls = require(['balls']);

define ( [
    'backbone',
    'tmpl/main',
    'views/balls'
], function (
    Backbone,
    tmpl,
    balls
) {
    var View =  Backbone.View.extend({
        template: tmpl,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template() );
            $('.main').css( 'margin-top', $('.main').height / 2 );
            ballColors = [/*$('.main').css('box-shadow').replace(/^.*(rgba?\([^)]+\)).*$/,'$1')*/'red', $('.main__header').css('color')];
            scene();
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