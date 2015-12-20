define ( [
    'backbone',
    'views/game',
    'views/login',
    'views/registration',
    'views/main',
    'views/scoreboard',
    'views/stats',
    'views/ViewManager'
], function (
    Backbone,
    viewGame,
    viewLogin,
    viewRegistration,
    viewMain,
    viewScoreboard,
    viewStats,
    viewManager
) {
    viewManager.addViews( [
        viewGame,
        viewLogin,
        viewRegistration,
        viewMain,
        viewScoreboard,
        viewStats
    ] );

    var Router = Backbone.Router.extend( {

        routes: {
            'scores' : 'scoreboardAction',
            'game' : 'gameAction',
            'login' : 'loginAction',
            'registration': 'registrationAction',
            'stats': 'statsAction',
            '*default' : 'defaultActions'
        },

        initialize: function() {
            activeView = viewMain;
        },

        screenChange: function(view) {
            view.show();
        },

        scoreboardAction: function() {
            this.screenChange(viewScoreboard);
        },

        gameAction: function() {
            this.screenChange(viewGame);
        },

        loginAction: function() {
            this.screenChange(viewLogin);
        },

        registrationAction: function() {
            this.screenChange(viewRegistration);
        },

        statsAction: function() {
            this.screenChange(viewStats);
        },

        defaultActions: function() {
            this.screenChange(viewMain);
        }
    } );
    return new Router();
} );