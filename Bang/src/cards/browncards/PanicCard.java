package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class PanicCard extends Card {
    public PanicCard(String suit, int value) {
        super("Panic", suit, value, CardType.PANIC);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of panic
    }
}
