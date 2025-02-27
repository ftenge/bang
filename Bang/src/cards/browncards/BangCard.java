package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class BangCard extends Card {
    public BangCard(String suit, int value) {
        super("Bang!", suit, value, CardType.BANG);
    }

    @Override
    public void use() {

    }

    public void use(Player player, Player target) {
        // Implementation of attack
    }

}
