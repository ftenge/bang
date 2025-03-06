package cards.bluecards;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class BarrelCard extends SingleTargetCard {
    public BarrelCard(String suit, int value) {
        super("Barrel", suit, value, CardType.BARREL);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        if(!baseModel.hasBarrel()) {
            baseModel.removeCardFromHand(this);
            baseModel.addBarrel(this);
            return true;
        }
        return false;
    }

}
