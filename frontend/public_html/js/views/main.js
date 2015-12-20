//var balls = require(['balls']);

define ( [
    'backbone',
    'tmpl/main',
    'tmpl/balls-control',
    'views/balls'
], function (
    Backbone,
    tmpl,
    ballsCtrl,
    balls
) {
    var View =  Backbone.View.extend({
        template: tmpl,
        ballsControl: ballsCtrl,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
            this.ballsFlag = false;
        },
        render: function () {
            this.$el.html( this.template() );
            ballColors = ['red', $('.main__header').css('color')];
//            $('#page').css('z-index', -101);
            if ( !this.ballsFlag ) {
            	$('#balls-control').html( this.ballsControl() );
            	scene();
            	this.ballsFlag = true;
            }
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