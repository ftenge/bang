package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class DuelCard extends DualTargetCard {
    public DuelCard(String suit, int value) {
        super("Duel", suit, value, CardType.DUEL);
    }

    //eldobjuk a kártyát és meghívjuk a target duelAction-jét, ahol a baseModel a paraméter
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        origin.removeCard(this);
        target.duelAction(origin, gameLogic);
        return true;
    }
}