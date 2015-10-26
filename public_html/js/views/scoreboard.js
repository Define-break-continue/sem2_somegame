define ( [
    'backbone',
    'tmpl/scoreboard',
    'collections/scores'
], function (
    Backbone,
    tmpl,
    ScoresCollection
) {
    var View =  Backbone.View.extend({
        template: tmpl,

        initialize: function ( options ) {
            this.el = $( '#page' );
            this.setElement('#page');
        },
        render: function () {
        console.log('here we go! ', ScoresCollection);
            this.$el.html( this.template() );
            var i = 1;
            ScoresCollection.forEach( function( data ) {
                this.$('.highscores-table').append(
                    '<tr class="highscores-table__usual-tr">\
                        <td class="highscores-table__left-td">' + i.toString() + '</td>\
                        <td class="highscores-table__center-td">' + data.get( 'login' ) + '</td>\
                        <td class="highscores-table__right-td">' + data.get( 'score' ) + '</td>\
                    </tr>'
                );
                i++;
            } );
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