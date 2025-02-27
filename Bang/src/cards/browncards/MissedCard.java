package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class MissedCard extends Card {
    public MissedCard(String suit, int value) {
        super("Missed", suit, value, CardType.MISSED);
    }

    @Override
    public void use() {

    }

    public void use(Player player, Player target) {
        // Implementation of missed
    }

}