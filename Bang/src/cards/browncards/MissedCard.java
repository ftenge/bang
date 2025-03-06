package cards.browncards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.characters.CalamityJanet;
import utilities.characters.SlabTheKiller;

public class MissedCard extends DualTargetCard {
    public MissedCard(String suit, int value) {
        super("Missed", suit, value, CardType.MISSED);
    }

    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        baseModel.removeCard(this);
        if(baseModel instanceof CalamityJanet){
            if(!baseModel.getBangedThisRound() || baseModel.getRapid()) {
                baseModel.setBangedThisRound(true);
                target.bangAction(baseModel, gameLogic);
            }
        }
        // Implementation of missed
        return true;
    }
}