package cards.browncards;

import cards.Card;
import cards.CardType;
import players.Player;

public class StagecoachCard extends Card {
    public StagecoachCard(String suit, int value) {
        super("Stagecoach", suit, value, CardType.STAGECOACH);
    }

    @Override
    public void use() {

    }

    public void use(Player player) {
        // Implementation of stagecoach
    }
}
