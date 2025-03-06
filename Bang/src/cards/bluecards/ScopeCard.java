package cards.bluecards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class ScopeCard extends SingleTargetCard {
    public ScopeCard(String suit, int value) {
        super("Scope", suit, value, CardType.SCOPE);
    }


    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        if(!baseModel.hasScope()) {
            baseModel.addScope(this);
            return true;
        }
        return false;
    }
}
