package cards.browncards;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class MissedCard extends SingleTargetCard {
    public MissedCard(String suit, int value) {
        super("Missed", suit, value, CardType.MISSED);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        // Implementation of missed
        return true;
    }
}