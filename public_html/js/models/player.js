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
            isSuccess: false,
            isAdmin: false
        },

        registration: function( data ) {
            $.ajax( {
                type: 'POST',
                url: this.registrationURL,
                dataType: 'json',
                data: data,//this.toJSON(),
                error: this.ajaxError,
                success: this.ajaxSuccess
            });
        },

        login: function() {
            $.ajax( {
                type: 'POST',
                url: this.loginURL,
                dataType: 'json',
                data: this.toJSON(),
                error: this.ajaxError,
                success: this.ajaxSuccess
            });
        },

        registrationCheck: function() {
            if ( this.get('email').search('@') < 0 ) {
                validationError = 'Validation Error!';
            }
            if ( this.get('password').length < 1 ) {
                validationError = 'Validation Error!';
            }
        },

        loginCheck: function() {
            registrationCheck();
        },

        ajaxError: function() {
        },

        ajaxSuccess: function( code ) {
            if ( code == 0 ) {
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        }
    });

    return PlayerModel;
});