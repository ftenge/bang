package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class CatBalouCard extends Card {
    public CatBalouCard(String suit, int value) {
        super("Cat Balou", suit, value, CardType.CAT_BALOU);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of catbalou
    }
}
