package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class WellsFargoCard extends SingleTargetCard {
    public WellsFargoCard(String suit, int value) {
        super("Wells Fargo", suit, value, CardType.WELLS_FARGO);
    }

    //eldobjuk a kártyát és az origin húz 2 lapot
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.removeCard(this);
        for(int i = 0; i < 3; i++){
            baseModel.drawCard();
        }
        return true;
    }

}
