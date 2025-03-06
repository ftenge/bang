package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class PanicCard extends DualTargetCard {
    public PanicCard(String suit, int value) {
        super("Panic", suit, value, CardType.PANIC);
    }

    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        baseModel.removeCard(this);
        baseModel.panicAction(target, gameLogic);
        return true;
    }
}
