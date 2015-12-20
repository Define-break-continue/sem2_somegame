define ( [
    'backbone',
    'views/game',
    'views/login',
    'views/registration',
    'views/main',
    'views/scoreboard',
    'views/stats',
    'views/rooms',
    'views/ViewManager'
], function (
    Backbone,
    viewGame,
    viewLogin,
    viewRegistration,
    viewMain,
    viewScoreboard,
    viewStats,
    viewRooms,
    viewManager
) {
    viewManager.addViews( [
        viewGame,
        viewLogin,
        viewRegistration,
        viewMain,
        viewScoreboard,
        viewStats,
        viewRooms
    ] );

    var Router = Backbone.Router.extend( {

        routes: {
            'scores' : 'scoreboardAction',
            'game' : 'gameAction',
            'login' : 'loginAction',
            'registration': 'registrationAction',
            'stats': 'statsAction',
            'rooms': 'roomsAction',
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

        roomsAction: function() {
            this.screenChange(viewRooms);
        },

        defaultActions: function() {
            this.screenChange(viewMain);
        }
    } );
    return new Router();
} );