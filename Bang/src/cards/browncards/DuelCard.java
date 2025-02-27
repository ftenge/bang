package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class DuelCard extends Card {
    public DuelCard(String suit, int value) {
        super("Duel", suit, value, CardType.DUEL);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of duel
    }
}