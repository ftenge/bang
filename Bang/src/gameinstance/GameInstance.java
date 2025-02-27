package gameinstance;

import gamelogic.GameLogic;

public class GameInstance {
    private int playerCount;
    private String difficulty;
    private GameLogic gameLogic;

    public GameInstance(int playerCount, String difficulty) {
        this.playerCount = playerCount;
        this.difficulty = difficulty;
        initializeGame();
    }

    private void initializeGame() {
        // Logic for setting up players, deck, and game
    }
}
