package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class IndiansCard  extends Card {
    public IndiansCard(String suit, int value) {
        super("Indians", suit, value, CardType.INDIANS);
    }

    @Override
    public void use() {

    }

    public void use(Player player, Player target) {
        // Implementation of indians
    }
}