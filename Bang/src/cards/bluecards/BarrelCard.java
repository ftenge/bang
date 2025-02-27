package cards.bluecards;

import cards.Card;
import cards.CardType;
import players.Player;

public class BarrelCard extends Card {
    public BarrelCard(String suit, int value) {
        super("Barrel", suit, value, CardType.BARREL);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of barrel
    }

}
