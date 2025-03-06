package cards.bluecards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class MustangCard extends SingleTargetCard {
    public MustangCard(String suit, int value) {
        super("Mustang", suit, value, CardType.MUSTANG);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        if(!baseModel.hasMustang()) {
            baseModel.addMustang(this);
            return true;
        }
        return false;
    }
}