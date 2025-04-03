package cards.bluecards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class MustangCard extends SingleTargetCard {
    public MustangCard(String suit, int value) {
        super("Mustang", suit, value, CardType.MUSTANG);
    }

    //ha még nincs előtte musztáng lap, akkor a baseModel kezéből,
    // hozzáadja a baseModelhez, igazat ad vissza ha sikerül
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        if(!baseModel.hasMustang()) {
            baseModel.removeCardFromHand(this);
            baseModel.addMustang(this);
            return true;
        }
        return false;
    }
}