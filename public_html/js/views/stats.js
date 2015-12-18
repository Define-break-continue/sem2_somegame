define ( [
    'backbone',
    'tmpl/stats',
    'models/player'
], function (
    Backbone,
    tmpl,
    userModel
) {
    var View =  Backbone.View.extend({
        template: tmpl,
        model: new userModel(),
        isAdmin: true,
        data: {
            'E-mail' : 'romvano@ya.ru',
            'Score' : 1024,
            'Games Played' : 4,
            'Games Won' : 2,
            'Games Lost': 2,
        },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template( this.data, {isAdmin: this.isAdmin} ) );
            this.$('body').append( this.el );
            return this;
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });
    return new View;
} );