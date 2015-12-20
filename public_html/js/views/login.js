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
            this.$passwordMessage = this.$( '.popup__password-message' );
            this.$emailMessage = this.$( '.popup__email-message' );
            this.$errorMessage = this.$( '.popup__other-message' );
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
                this.$passwordMessage.html( 'Please, enter the password! ' );
                this.$password.focus;
                this.$password.css('color', 'red');
                return false;
            } else {
                this.$passwordMessage.html( '' );
                    this.$password.css('color', 'green');
                    return true;
                }
            return false;

        },

        emailChecker: function() {
            if ( !this.$email[0].validity.valid ) {
                this.$emailMessage.html( 'Please enter a valid e-mail! ' );
                this.$email.css('color', 'red');
                this.$email.focus;
                return false;
            } else {
                this.$emailMessage.html( '' );
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

            this.model.set( { email: this.$email.val(), password: this.$password.val() } );
            this.model.login( {
                tp: 0,
                email: this.$email.val(),
                password: this.$password.val(),
            } );
            console.log('view');
            switch ( this.model.get( 'isSuccess' ) ) {
                case 0:
                    this.$errorMessage.html( '' );
                    this.model.set( { 'isLogged': true } );
                    Backbone.history.navigate( '#main', { trigger: true } );
                    break;
                case 4:
                case 9:
                    this.$errorMessage.html( 'Incorrect login or password!' );
                    break;
                default:
                    this.$errorMessage.html( 'Failed to send data to server. Sth is wrong =)' );
                    break;
            }
            return false;
        },
    });
    return new View;
} );