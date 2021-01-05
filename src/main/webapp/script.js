var webSocket;
var output = document.getElementById("info_panel");
var canPerformMove = false;

function connect() {
    // open the connection if one does not exist
    // toto
    if (webSocket !== undefined
            && webSocket.readyState !== WebSocket.CLOSED) {
        return;
    }
    // Create a websocket
    webSocket = new WebSocket("ws://" + window.location.host +"/KalahaGame");

    webSocket.onopen = function(event) {
        console.log("connection opened");
    };

    webSocket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        var messageCode = message["message_code"];
        switch(messageCode) {
            case "next_turn":
                 updateBoardState(message["player_id"], message["board_state"]);
                 var info_message = "Your turn. Click on a cell to make a move.";
                 canPerformMove = message["can_make_turn"];
                 if(!canPerformMove)
                    info_message = "Waiting for your opponent's turn."
                 updateOutput(info_message);
                break;
            case "wait_for_opponent":
                updateOutput("waiting for opponent to connect.");
                break;
            case "game_over":
                updateBoardState(message["player_id"], message["board_state"]);
                updateOutput("Game over. Refresh the page to restart.");
                break;
        }

        console.log(event.data);
    };

    webSocket.onclose = function(event) {
        console.log(event.data);
    };
}

function updateBoardState(myId, boardState) {
    var otherId = (myId + 1) % 2;
    var myBoard = boardState["player_" + myId];
    var opponentBoard = boardState["player_" + otherId];
    for (var i = 0; i < myBoard.length; i++) {
        document.getElementById("my_pit_" + i).innerHTML = myBoard[i];
        document.getElementById("opponent_pit_" + i).innerHTML = opponentBoard[i];
    }
}

function makeMove(id) {
    if(canPerformMove)
        webSocket.send(id.charAt(id.length-1));
}

function updateOutput(text) {
    output.innerHTML = text;
}