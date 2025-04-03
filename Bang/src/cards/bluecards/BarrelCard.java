package cards.bluecards;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class BarrelCard extends SingleTargetCard {
    public BarrelCard(String suit, int value) {
        super("Barrel", suit, value, CardType.BARREL);
    }

    //ha nincs a kijátszó játékoson kijátszott hordó lap,
    //akkor kiveszi a kezéből és kijátssza és igazat ad vissza,
    //máskülönben hamisat
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
