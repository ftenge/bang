package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class BeerCard extends SingleTargetCard {
    public BeerCard(String suit, int value) {
        super("Beer", suit, value, CardType.BEER);
    }

    //meghívjuk az origin beerAction-jét és annak a visszatérési értékével térünk vissza
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        return origin.beerAction();
    }
}
