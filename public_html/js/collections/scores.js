define([
    'backbone',
    'models/player'
], function(
    Backbone,
    PlayerModel
){

    playerExamples = [];
    for ( var i = 0; i < 10; i++ ) {
    console.log('for works');
        playerExamples.push( new PlayerModel({ login: 'Player' + i.toString(), score: Math.floor( Math.random() * 1000 ) }) );
    };

    var ScoresCollection = Backbone.Collection.extend({
        model: PlayerModel,
        comparator: function ( player1, player2 ) { // returns 1 if players are needed to be placechanged
            if ( player1.get( 'score' ) < player2.get( 'score' ) ) return -1;
            if ( player1.get( 'score' ) > player2.get( 'score' ) ) return 1;
            if ( player1.get( 'login' ) < player2.get( 'login' ) ) return -1;
            return 1;
        }
    });
    return new ScoresCollection(playerExamples);
});