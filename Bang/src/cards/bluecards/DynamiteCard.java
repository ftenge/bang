package cards.bluecards;

import cards.Card;
import cards.CardType;
import players.Player;

public class DynamiteCard extends Card {
    public DynamiteCard(String suit, int value) {
        super("Dynamite", suit, value, CardType.DYNAMITE);
    }

    @Override
    public void use() {

    }

    public void use(Player player, Player target) {
        // Implementation of dynamite
    }

}
