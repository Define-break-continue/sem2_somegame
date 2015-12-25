//define( [
// 'collections/packmans',
// 'models/user'
// ], function(
// packmans,
// userModel
// ) {
//    function connection() {
//        var socket = new WebSocket( '/game' );
//
//        socket.onopen = function( event ) {
//            console.log('Connection opened');
//        };
//
//        socket.onclose = function(event) {
//            if (event.wasClean) {
//                console.log('Соединение закрыто чисто');
//            } else {
//                alert('Holy cow! Connection lost!');
//                window.close();
//            }
//            console.log('Код: ' + event.code + ' причина: ' + event.reason);
//        };
//
//        socket.onmessage = function(event) {
//
//        };
//
//        socket.onerror = function(error) {
//            alert( 'A connection error occured!' );
//        };
//
//        return socket;
//
////        send = function( data ) {
////            socket.send( data );
////        };
//    }
//
//    return connection;
// } );