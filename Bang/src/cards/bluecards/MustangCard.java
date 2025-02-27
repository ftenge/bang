package cards.bluecards;

import cards.Card;
import cards.CardType;
import players.Player;

public class MustangCard extends Card {
    public MustangCard(String suit, int value) {
        super("Mustang", suit, value, CardType.MUSTANG);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of attack
    }

}