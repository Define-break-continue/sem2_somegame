define([
    'backbone'
], function(
    Backbone
){
//
//$.ajaxSetup({
//    beforeSend: function(xhr, settings) {
//        if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
//        xhr.setRequestHeader("X-CSRFToken", csrftoken);
//        }
//    }
//});

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
            isSuccess: 0,
            isAdmin: false
        },

        registration: function( data ) {
//            console.log(data.toString());
            if ( this.registrationCheck() ) {
                $.ajax( {
                    type: 'POST',
                    url: this.registrationURL,
                    dataType: 'Content-Type: application/json',
                    data: JSON.stringify(data),//this.toJSON(),
                    error: this.ajaxError,
                    success: this.ajaxSuccess
                } );
//               $.post({
//
//                  url: this.registrationURL,
//                  dataType: 'application/json',
//                  data: JSON.stringify(data),//this.toJSON(),
//                  error: this.ajaxError,
//                  success: this.ajaxSuccess
//              } );
            }
        },

        login: function() {
            if ( this.loginCheck() ) {
                $.ajax( {
                    type: 'POST',
                    url: this.loginURL,
                    dataType: 'json',
                    data: this.toJSON(),
                    error: this.ajaxError,
                    success: this.ajaxSuccess
                } );
            }
        },

        registrationCheck: function() {
            if ( this.get('email').search('@') < 0 ) {
                validationError = 'Validation Error!';
                return false;
            }
            if ( this.get('password').length < 1 ) {
                validationError = 'Validation Error!';
                return false;
            }
            return true;
        },

        loginCheck: function() {
            return registrationCheck();
        },

        ajaxError: function() {

        },

        ajaxSuccess: function( code ) {
//        var dat = $.parseXML( code );
//            console.log('code ' + dat );
            this.isSuccess = code;
        }
    });

    return PlayerModel;
});