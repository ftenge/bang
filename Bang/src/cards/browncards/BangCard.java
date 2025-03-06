package cards.browncards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.characters.SlabTheKiller;

public class BangCard extends DualTargetCard {
    public BangCard(String suit, int value) {
        super("Bang!", suit, value, CardType.BANG);
    }

    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        if(!baseModel.getBangedThisRound() || baseModel.getRapid()) {
            baseModel.setBangedThisRound(true);
            baseModel.removeCard(this);
            if(baseModel instanceof SlabTheKiller){
                target.slabTheKillerBangangAction(baseModel, gameLogic);
                return true;
            }
            target.bangAction(baseModel, gameLogic);
            return true;
        }
        return false;
    }

}
