define([
    'backbone'
], function(
    Backbone
){



    var PlayerModel = Backbone.Model.extend({
        registrationURL: '/signup/',
        loginURL: '/signin/',
        defaults: {
                tp: 0,
                email: '',
                password: '',
                score: 0,
                gamesPlayed: 0,
                gamesWon: 0,
                gamesLost: 0,
                isAdmin: false,
                isSuccess: 99,
                isLogged: false,
        },

        registration: function( data ) {
//            console.log(data.toString());
            var self = this;
            if ( this.registrationCheck() ) {
                $.ajax( {
                    type: 'POST',
                    url: this.registrationURL,
                    dataType: 'json',
                    data: JSON.stringify( data ),//this.toJSON(),
                    error: function( answer ) { self.set( 'isSuccess', 99 ); },
                    success: function( answer ) { self.set( 'isSuccess', answer.code ); },
                } );
            }
        },

        login: function( data ) {
            var self = this;
            if ( this.loginCheck() ) {
                $.ajax( {
                    type: 'POST',
                    url: this.loginURL,
                    dataType: 'json',
                    data: JSON.stringify( data ),
                    error: function( answer ) { self.set( 'isSuccess', 99 ); },
                    success: function( answer ) { self.set( 'isSuccess', answer.code ); },
                } );
            }
        },

        registrationCheck: function() {
            if ( this.get('email').search('@') < 1 ) {
                return false;
            }
            if ( this.get('password').length < 1 ) {
                return false;
            }
            return true;
        },

        loginCheck: function() {
            return this.registrationCheck();
        },
    });

    return PlayerModel;
});