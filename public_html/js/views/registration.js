define ( [
    'backbone',
    'tmpl/registration'
], function (
    Backbone,
    tmpl
) {
    var View =  Backbone.View.extend({
        template: tmpl,

        events: {
            'change #password2' : 'passwordChecker',
            'change #password1' : 'passwordChecker',
            'change #email' : 'emailChecker'
        },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template() );
            this.$email = this.$('#email');
            this.$password1 = this.$('#password1');
            this.$password2 = this.$('#password2');
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
                $( '.login-form__password-message' ).html( 'The passwords are different! Please, check it!' );
                this.$password1.focus;
                this.$password1.css('color', 'red');
                this.$password2.css('color', 'red');
            } else {
                $( '.login-form__password-message' ).html( '' );
                if ( this.$password2.val() ) {
                    this.$password1.css('color', 'green');
                    this.$password2.css('color', 'green');
                }
            }
        },

        emailChecker: function(event) {
            if ( !event.target.validity.valid ) {
                $( '.login-form__email-message' ).html( 'Please enter a valid e-mail' );
                this.$email.css('color', 'red');
                this.$email.focus;
            } else {
                $( '.login-form__email-message' ).html( '' );
                this.$email.css('color', 'green');
            }
        },

        register: function() {
            $("#idForm").on("submit", function(event) {
                event.preventDefault();
                $.ajax({
                    type: "POST",
                    url: "path/to/your/endpoint",
                    data: $(this).serialize(),
                    success: postDispatcher
                    });
            });
        }
    });
    return new View;
} );