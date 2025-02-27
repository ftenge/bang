package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class WellsFargoCard extends Card {
    public WellsFargoCard(String suit, int value) {
        super("Wells Fargo", suit, value, CardType.WELLS_FARGO);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of wells Fargo
    }
}
