package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class CatBalouCard extends DualTargetCard {
    public CatBalouCard(String suit, int value) {
        super("Cat Balou", suit, value, CardType.CAT_BALOU);
    }

    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        baseModel.removeCard(this);
        // Implementation of catbalou
        baseModel.catBalouAction(target, gameLogic);
        return true;
    }
}
