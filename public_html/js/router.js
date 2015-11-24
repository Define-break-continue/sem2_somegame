define ( [
    'backbone',
    'views/game',
    'views/login',
    'views/registration',
    'views/main',
    'views/scoreboard'
], function (
    Backbone,
    viewGame,
    viewLogin,
    viewRegistration,
    viewMain,
    viewScoreboard
) {
console.log('lv: ', viewLogin);
    var Router = Backbone.Router.extend( {

        routes: {
            'scores' : 'scoreboardAction',
            'game' : 'gameAction',
            'login' : 'loginAction',
            'registration': 'registrationAction',
            'admin': 'adminAction',
            '*default' : 'defaultActions'
        },

        initialize: function() {
            activeView = viewMain;
        },

        screenChange: function(view) {
            activeView.hide();
            view.show();
            this.activeView = view;
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

        adminAction: function() {
            this.screenChange(viewAdmin);
        },

        defaultActions: function() {
            this.screenChange(viewMain);
        }
    } );
    return new Router();
} );