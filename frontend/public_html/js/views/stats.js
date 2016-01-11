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
//        data: {
//            'E-mail' : model.get( 'email' ),
//            'Score' : model.get( 'score' ),
//            'Games Played' : model.get( 'gamesPlayed' ),
//            'Games Won' : model.get( 'gamesWon' ),
//            'Games Lost': model.get( 'gamesLost' ),
//        },

        getData: function() {
            this.model.fetch();
            return {
                'E-mail' : this.model.get( 'email' ),
                'Score' : this.model.get( 'score' ),
                'Games Played' : this.model.get( 'gamesPlayed' ),
                'Games Won' : this.model.get( 'gamesWon' ),
                'Games Lost': this.model.get( 'gamesLost' ),
            };
        },

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
            this.$el.html( this.template( this.getData() ) );
            this.$('body').append( this.el );
            return this;
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },

    });
    return new View;
} );