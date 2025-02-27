package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class BeerCard extends Card {
    public BeerCard(String suit, int value) {
        super("Beer", suit, value, CardType.BEER);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of heal
    }
}
