package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class GeneralStore extends Card {
    public GeneralStore(String suit, int value) {
        super("General Store", suit, value, CardType.GENERAL_STORE);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of general store
    }
}
