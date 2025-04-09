package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class CatBalouCard extends DualTargetCard {
    public CatBalouCard(String suit, int value, String imagePath) {
        super("Cat Balou", suit, value, CardType.CAT_BALOU, imagePath);
    }

    //eldobjuk a kártyát és az origin eldobat egy kártyát a target kezéből
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        origin.catBalouAction(target, gameLogic);
        origin.removeCard(this);
        return true;
    }
}
