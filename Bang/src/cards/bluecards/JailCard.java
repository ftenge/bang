package cards.bluecards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.RoleType;

public class JailCard extends DualTargetCard {
    private int round = 1;

    public JailCard(String suit, int value) {
        super("Jail", suit, value, CardType.JAIL);
    }

    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        if(!target.inJail() && target.getRole().getType() != RoleType.SHERIFF){
            this.round = 1;
            target.addJail(this);
            return true;
        }
        return false;
    }


    public int getRound(){
        return round;
    }
}
