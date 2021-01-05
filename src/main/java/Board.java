import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Board {
	
	private final Player[] players;
	private final int[] pitMapping;
	private final int pitCount;
		
	/**
	 * Initialize the new board
	 * @param pitNumber amount of small pits per player
	 * @param stoneCount amount of stones per pit at the beginning of the game
	 */
	public Board(int pitNumber, int stoneCount) {
		
		pitCount = pitNumber + 1;
		players = new Player[2];
		players[0] = new Player(pitNumber, stoneCount);
		players[1] = new Player(pitNumber, stoneCount);
		
		// map opposing pits for easier capturing
		pitMapping = new int[pitCount];
		for(int i = 0; i < pitCount - 1; i++) {
			pitMapping[i] = pitCount - i - 2;
		}
		pitMapping[pitCount - 1] = pitCount - 1;
	}
	
	/**
	 * performs a game turn for chosen player (seeding + capturing of stones, if possible) 
	 * @param playerId id of the player making a turn
	 * @param pitIdx index of pit for seeding
	 * @return id of player making next move
	 */
	public int performTurn(int playerId, int pitIdx) {
		int anotherPlayerId = (playerId +1) % 2;
		Player currentPlayer = players[playerId];
		Player anotherPlayer = players[anotherPlayerId];
		
		// if chosen pit is empty - try again
		if(currentPlayer.isPitEmpty(pitIdx)) {
			return playerId;
		}
		
		int lastSeedingIdx = (pitIdx + currentPlayer.getStoneAmount(pitIdx)) % pitCount;
		
		boolean lastSeedingEmpty = currentPlayer.isPitEmpty(lastSeedingIdx) || lastSeedingIdx == pitIdx;
		currentPlayer.seed(pitIdx);
		// if last seeded pit was empty before seeding - player captures the stones from opponent
		if(lastSeedingEmpty) {
			int capturedAmount = anotherPlayer.loosePitStones(pitMapping[lastSeedingIdx]);
			currentPlayer.capturePitStones(lastSeedingIdx, capturedAmount);
		}
		
		// if lastSeedingIdx corresponds to the big pit - we grant another turn
		if(lastSeedingIdx == pitCount - 1) {
			return playerId;
		}
		
		return anotherPlayerId;
	}

	/**
	 * returns JSON object of current board state
	 */
	public JSONObject getBoardState() {

		JSONArray jsonPlayer0State = new JSONArray();
		JSONArray jsonPlayer1State = new JSONArray();
		for(int i = 0; i < pitCount; i++) {
			jsonPlayer0State.add(players[0].getStoneAmount(i));
			jsonPlayer1State.add(players[1].getStoneAmount(i));

		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("player_0", jsonPlayer0State);
		jsonObject.put("player_1", jsonPlayer1State);

		return jsonObject;
	}
	
	/**
	 * checks if the game is over. Game is over if at least one of the players
	 * can not perform any more actions
	 * @return true if game is over, false otherwise
	 */
	public boolean gameIsOver() {
		// game is over once at least one of the players can't perform actions anymore
		for (Player p : players) {
			if(!p.canPerformAction()) {
				cleanUp();
				return true;
			}
		}
		return false;
	}

	private void cleanUp() {
		for (Player p : players) {
			for(int i = 0; i < pitCount-1; i++) {
				p.capturePitStones(pitCount-1, p.loosePitStones(i));
			}
		}
	}
}
