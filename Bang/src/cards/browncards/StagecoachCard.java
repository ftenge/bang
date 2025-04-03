package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class StagecoachCard extends SingleTargetCard {
    public StagecoachCard(String suit, int value) {
        super("Stagecoach", suit, value, CardType.STAGECOACH);
    }

    //eldobjuk a kártyát és az origin húz 2 lapot
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.removeCard(this);
        for(int i = 0; i < 2; i++){
            baseModel.drawCard();
        }
        return true;
    }
}
