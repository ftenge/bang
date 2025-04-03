package cards.bluecards;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class DynamiteCard extends SingleTargetCard {
    public DynamiteCard(String suit, int value) {
        super("Dynamite", suit, value, CardType.DYNAMITE);
    }

    //kiveszi a kezéből a dinamit kártyát és lerakja maga elé
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.removeCardFromHand(this);
        baseModel.addDynamite(this);
        return true;
    }
}
