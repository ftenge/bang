package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class SaloonCard extends Card {
    public SaloonCard(String suit, int value) {
        super("Saloon", suit, value, CardType.SALOON);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of saloon
    }
}
