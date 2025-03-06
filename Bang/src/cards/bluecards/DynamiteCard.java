package cards.bluecards;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class DynamiteCard extends SingleTargetCard {
    public DynamiteCard(String suit, int value) {
        super("Dynamite", suit, value, CardType.DYNAMITE);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.addDynamite(this);
        return true;
    }
}
