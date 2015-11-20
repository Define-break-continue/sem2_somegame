define([
    'backbone'
], function(
    Backbone
){

    var PlayerModel = Backbone.Model.extend({
        login: '',
        score: 0
    });

    return PlayerModel;
});