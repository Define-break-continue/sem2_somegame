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
            'input .js-password2' : 'passwordChecker',
            'change .js-password1' : 'passwordChecker',
            'change .js-email' : 'emailChecker',
            'input .js-email' : 'setLocalStorage',
            'submit .js-registration-form' : 'register'
        },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$storage = window.localStorage;
            this.$el.html( this.template() );
            this.$email = this.$('.js-email');
            this.$password1 = this.$('.js-password1');
            this.$password2 = this.$('.js-password2');
            this.$errorMessage = this.$('.popup__other-message');
            return this;
        },
        show: function () {
            this.render();
            if ( this.$storage.getItem('email') ) {
                this.$email.val( this.$storage.getItem( 'email' ) );
                this.emailChecker();
            }
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

        setLocalStorage: function() {
            this.$storage.setItem( 'email', this.$email.val() );
        },

        validate: function () {
            return this.emailChecker() && this.passwordChecker();
        },

        register: function( event ) {
            event.preventDefault();

            var self = this;

            if ( !this.validate() ) return;

            this.model.set( { email: this.$email.val(), password: this.$password1.val() } );

            this.model.save( null, {
                data: JSON.stringify( {
                    tp: 0,
                    email: this.$email.val(),
                    password1: this.$password1.val(),
                    password2: this.$password2.val(),
                } ),

                success: function( model, data ) {
                    self.model.clear();
                    switch ( data.code ) {
                        case 0:
                            self.$errorMessage.html( '' );
                            self.model.set( { 'isLogged': true } );
                            self.$errorMessage.html( 'Success!' );
                            self.$( '.popup__input' ).css( { 'disabled': 'true' } );
                            window.setTimeout( function() { Backbone.history.navigate( '#main', { trigger: true } ); }, 1000 );
                            self.model.set(_.extend(data.response, { isLogged: true }));
                            break;
                        case 5:
                            self.$errorMessage.html( 'There is already a user with this e-mail. Please choose another one!' );
                            break;
                        case 8:
                            self.$errorMessage.html( 'The input passwords are different!' );
                            break;
                        default:
                            self.$errorMessage.html( 'Failed to send data to server. Sth is wrong =)' );
                            break;
                    }
                },
                error: function( model, data ) {
                    self.model.clear();
                    self.$errorMessage.html( 'Failed to send data to server. Sth is wrong =)' );
                },
            } );
        }
    } );
    return new View;
} );