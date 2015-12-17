define ( [
    'backbone',
    'tmpl/registration',
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
            'change .js-password2' : 'passwordChecker',
            'change .js-password1' : 'passwordChecker',
            'change .js-email' : 'emailChecker',
            'submit .js-registration-form' : 'register'
        },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template() );
            this.$email = this.$('.js-email');
            this.$password1 = this.$('.js-password1');
            this.$password2 = this.$('.js-password2');
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
            if (  this.$password2.val() && this.$password1.val() != this.$password2.val() ) {
                $( '.popup__password-message' ).html( 'The passwords are different! Please, check it! ' );
                this.$password1.focus;
                this.$password1.css('color', 'red');
                this.$password2.css('color', 'red');
                return false;
            } else {
                $( '.popup__password-message' ).html( '' );
                if ( this.$password2.val() ) {
                    this.$password1.css('color', 'green');
                    this.$password2.css('color', 'green');
                    return true;
                }
                return false;
            }
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

        register: function( event ) {
            event.preventDefault();

            if ( !this.validate() ) return;

            this.model.set( { email: this.$email.val(), password: this.$password1.val() } )
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