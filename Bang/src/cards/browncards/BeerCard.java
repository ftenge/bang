package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class BeerCard extends SingleTargetCard {
    public BeerCard(String suit, int value) {
        super("Beer", suit, value, CardType.BEER);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        return baseModel.beerAction();
    }
}
