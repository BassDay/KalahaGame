import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;


@WebSocket
public class KalahaGameWebSocket {

    private static Board gameBoard = null;
    private static final Session[] playerSessions = new Session[2];
    private static AtomicInteger nextPlayerId = new AtomicInteger(0);

    private static final String MESSAGE_CODE_KEY = "message_code";
    private static final String BOARD_STATE_KEY = "board_state";
    private static final String CAN_MAKE_TURN_KEY = "can_make_turn";
    private static final String PLAYER_ID_KEY = "player_id";

    private static final String MESSAGE_NEXT_TURN = "next_turn";
    private static final String MESSAGE_GAME_OVER = "game_over";


    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        System.out.println("Message received:" + message);
        int pitIndex = Integer.parseInt(message);
        int playerId = Arrays.asList(playerSessions).indexOf(session);

        nextPlayerId.set(gameBoard.performTurn(playerId, pitIndex));

        boolean gameIsOver = gameBoard.gameIsOver();
        if(!gameIsOver) {
            broadcastMessage(MESSAGE_NEXT_TURN);
            return;
        }
        broadcastMessage(MESSAGE_GAME_OVER);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        if(gameBoard == null || gameBoard.gameIsOver()) {
            gameBoard = new Board(6, 6);
            nextPlayerId.set(0);
        }

        int emptySessionIndex = Arrays.asList(playerSessions).indexOf(null);

        // game is full, no place for another player
        if(emptySessionIndex == -1) {
            session.disconnect();
        }
        playerSessions[emptySessionIndex] = session;
        broadcastMessage(MESSAGE_NEXT_TURN);
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        playerSessions[Arrays.asList(playerSessions).indexOf(session)] = null;
    }

    private void broadcastMessage(String messageCode) throws IOException {
        for(Session s : playerSessions) {
            if(s == null)
                continue;
            if(!s.isOpen()) {
                // if session doesn't exist anymore - clean up
                playerSessions[Arrays.asList(playerSessions).indexOf(s)] = null;
                continue;
            }
            JSONObject broadcastMessage = new JSONObject();
            broadcastMessage.put(MESSAGE_CODE_KEY, messageCode);
            broadcastMessage.put(BOARD_STATE_KEY, gameBoard.getBoardState());
            broadcastMessage.put(CAN_MAKE_TURN_KEY, Arrays.asList(playerSessions).indexOf(s) == nextPlayerId.intValue());
            broadcastMessage.put(PLAYER_ID_KEY, Arrays.asList(playerSessions).indexOf(s));
            s.getRemote().sendString(broadcastMessage.toJSONString());
        }
    }
}