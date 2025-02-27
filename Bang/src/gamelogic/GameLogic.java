package gamelogic;

import cards.Deck;
import players.Player;

import java.util.List;

public class GameLogic {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;

    public GameLogic(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
        this.currentPlayerIndex = 0;
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean isOver() {
        return players.stream().filter(Player::isAlive).count() == 1;
    }
}