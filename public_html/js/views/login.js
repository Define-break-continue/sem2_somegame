define ( [
    'backbone',
    'tmpl/login',
    'models/player'
], function (
    Backbone,
    tmpl,
    userModel
) {
    var View =  Backbone.View.extend({
        template: tmpl,
        model: new userModel(),

        events: {
            'change .js-password' : 'passwordChecker',
            'change .js-login' : 'emailChecker',
            'submit .js-login-form' : 'login'
        },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template() );
            this.$email = this.$('.js-login');
            this.$password = this.$('.js-password');
            return this;
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },

        passwordChecker: function() {
            // при наличии чего-то в поле password2
            if ( !this.$password.val() ) {
                $( '.popup__password-message' ).html( 'Please, enter the password! ' );
                this.$password.focus;
                this.$password.css('color', 'red');
                return false;
            } else {
                $( '.popup__password-message' ).html( '' );
                    this.$password.css('color', 'green');
                    return true;
                }
            return false;

        },

        emailChecker: function() {
            if ( !this.$email[0].validity.valid ) {
                $( '.popup__email-message' ).html( 'Please enter a valid e-mail! ' );
                this.$email.css('color', 'red');
                this.$email.focus;
                return false;
            } else {
                $( '.popup__email-message' ).html( '' );
                this.$email.css('color', 'green');
                return true;
            }
        },

        validate: function () {
            return this.emailChecker() && this.passwordChecker();
        },

        login: function( event ) {
            event.preventDefault();

            if ( !this.validate() ) return;

            this.model.set( { email: this.$email.val(), password: this.$password.val() } )
            this.model.registration();
            if( this.model.get( 'isSuccess' ) ) {
                Backbone.history.navigate( '#main', { trigger: true } );
            } else {
                $('.popup__other-message').html( 'Failed to send data to server. Sth is wrong =)' );
            }
        }
    });
    return new View;
} );