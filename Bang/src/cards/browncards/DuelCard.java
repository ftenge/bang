package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class DuelCard extends DualTargetCard {
    public DuelCard(String suit, int value) {
        super("Duel", suit, value, CardType.DUEL);
    }

    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        baseModel.removeCard(this);
        target.duelAction(baseModel, gameLogic);
        return true;
    }
}