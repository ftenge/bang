package cards.bluecards;

import cards.Card;
import cards.CardType;
import players.Player;

public class JailCard extends Card {
    private int round;

    public JailCard(String suit, int value) {
        super("Jail", suit, value, CardType.JAIL);
        this.round = 1;
    }

    @Override
    public void use() {

    }

    public void use(Player player, Player target) {
        // Implementation of jail
    }

}
