package cards.bluecards;

import cards.Card;
import cards.CardType;
import players.Player;

public class ScopeCard extends Card {
    public ScopeCard(String suit, int value) {
        super("Scope", suit, value, CardType.SCOPE);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of scope
    }

}
