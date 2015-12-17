define([
    'backbone'
], function(
    Backbone
){

    var PlayerModel = Backbone.Model.extend({
        registrationURL: '/signup/',
        loginURL: '/signin/',
        defaults: {
            email: '',
            password: '',
            score: 0,
            emailMessage: '',
            passwordMessage: '',
            otherMessage: '',
            isSuccess: false
        },

        registration: function() {
            $.ajax( {
                type: 'POST',
                url: this.registrationURL,
                dataType: 'json',
                data: this.toJSON(),
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
                this.emailMessage = 'Please type a valid e-mail!';
                validationError = 'Validation Error!';
            }
            if ( this.get('password').length < 1 ) {
                this.passwordMessage = 'Please type the password!';
                validationError = 'Validation Error!';
            }
        },

        loginCheck: function() {
            registrationCheck();
        },

        ajaxError: function() {
            this.otherMessage = 'Failed to send the data.';
        },

        ajaxSuccess: function( code ) {
            if ( code === 0 ) {
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        }
    });

    return PlayerModel;
});