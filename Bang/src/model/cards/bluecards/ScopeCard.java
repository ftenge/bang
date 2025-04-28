package model.cards.bluecards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class ScopeCard extends SingleTargetCard {
    public ScopeCard(String suit, int value, String imagePath) {
        super("Scope", suit, value, CardType.SCOPE, imagePath);
    }


    //ha még nincs előtte távcső lap, akkor a baseModel kezéből,
    //hozzáadja a baseModelhez, igazat ad vissza ha sikerül
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        if(!baseModel.hasScope()) {
            baseModel.removeCardFromHand(this);
            baseModel.addScope(this);
            return true;
        }
        return false;
    }
}
