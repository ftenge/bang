package cards.browncards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class BangCard extends DualTargetCard {
    public BangCard(String suit, int value) {
        super("Bang!", suit, value, CardType.BANG);
    }

    @Override
    public boolean use(BaseModel player, BaseModel target, GameLogic gameLogic) {
        if(!player.getBangedThisRound() || player.getRapid()) {
            player.setBangedThisRound(true);
            target.bangAction(player, gameLogic);
            return true;
        }
        return false;
    }

}
