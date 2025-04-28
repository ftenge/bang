package model.cards.browncards;

import model.cards.CardType;
import model.cards.DualTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class PanicCard extends DualTargetCard {
    public PanicCard(String suit, int value, String imagePath) {
        super("Panic", suit, value, CardType.PANIC, imagePath);
    }

    //eldobjuk a kártyát és az origin húz egy kártyát a target kezéből
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        if(origin.getVision().get(gameLogic.getPlayers().indexOf(target)) <= 1) {
            origin.panicAction(target, gameLogic);
            origin.removeCard(this);
            return true;
        }
        return false;
    }
}
