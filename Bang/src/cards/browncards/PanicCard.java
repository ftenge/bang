package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class PanicCard extends DualTargetCard {
    public PanicCard(String suit, int value) {
        super("Panic", suit, value, CardType.PANIC);
    }

    //eldobjuk a kártyát és az origin húz egy kártyát a target kezéből
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        if(origin.getVision().get(gameLogic.getPlayers().indexOf(target)) <= 1) {
            origin.removeCard(this);
            origin.panicAction(target, gameLogic);
            return true;
        }
        return false;
    }
}
