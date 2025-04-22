package cards.bluecards;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class DynamiteCard extends SingleTargetCard {
    public DynamiteCard(String suit, int value, String imagePath) {
        super("Dynamite", suit, value, CardType.DYNAMITE, imagePath);
    }

    //kiveszi a kezéből a dinamit kártyát és lerakja maga elé
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        if(!baseModel.hasDynamite()){
            baseModel.removeCardFromHand(this);
            baseModel.addDynamite(this);
            return true;
        }
        return false;
    }
}
