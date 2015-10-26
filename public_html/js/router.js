define ( [
    'backbone',
    'views/game',
    'views/login',
    'views/main',
    'views/scoreboard'
], function (
    Backbone,
    viewGame,
    viewLogin,
    viewMain,
    viewScoreboard
) {
console.log('lv: ', viewLogin);
    var Router = Backbone.Router.extend( {

        routes: {
            'scores' : 'scoreboardAction',
            'game' : 'gameAction',
            'login' : 'loginAction',
            '*default' : 'defaultActions'
        },

        initialize: function() {
            activeView = viewMain;
        },

        screenChange: function(view) {
            console.log('activeview = ', activeView);
            activeView.hide();
            console.log('go!\n');
            view.show();
            this.activeView = view;
        },

        scoreboardAction: function() {
            console.log('entered router\n');
            this.screenChange(viewScoreboard);
        },

        gameAction: function() {
            console.log('entered router\n');
            this.screenChange(viewGame);
        },

        loginAction: function() {
            console.log('entered router\n');
            this.screenChange(viewLogin);
        },
        defaultActions: function() {
            this.screenChange(viewMain);
        }
    } );
    return new Router();
} );