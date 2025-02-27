package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class GatlingCard extends Card {
    public GatlingCard(String suit, int value) {
        super("Gatling", suit, value, CardType.GATLING);
    }

    @Override
    public void use() {

    }

    public void use(Player player, Player target) {
        // Implementation of gatling
    }
}
