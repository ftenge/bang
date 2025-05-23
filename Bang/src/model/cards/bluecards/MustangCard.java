package model.cards.bluecards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class MustangCard extends SingleTargetCard {
    public MustangCard(String suit, int value, String imagePath) {
        super("Mustang", suit, value, CardType.MUSTANG, imagePath);
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