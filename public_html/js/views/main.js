//var balls = require(['balls']);

define ( [
    'backbone',
    'tmpl/main',
    'tmpl/balls-control',
    '../../canvas/balls',
    'models/player',
], function (
    Backbone,
    tmpl,
    ballsCtrl,
    balls,
    userModel
) {
    var View =  Backbone.View.extend({
        template: tmpl,
        ballsControl: ballsCtrl,
        model: new userModel(),

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
            this.ballsFlag = false;
            this.isLoggedIn = this.model.get( 'isLogged' );
        },
        render: function () {
            this.$el.html( this.template( { isLoggedIn: this.isLoggedIn } ) );
            ballColors = ['red', $('.main__header').css('color')];
            if ( !this.ballsFlag ) {
            	$('#balls-control').html( this.ballsControl() );
            	balls();
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