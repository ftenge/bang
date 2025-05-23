package model.cards.bluecards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class BarrelCard extends SingleTargetCard {
    public BarrelCard(String suit, int value, String imagePath) {
        super("Barrel", suit, value, CardType.BARREL, imagePath);
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
