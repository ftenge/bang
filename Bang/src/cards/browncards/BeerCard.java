package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class BeerCard extends SingleTargetCard {
    public BeerCard(String suit, int value, String imagePath) {
        super("Beer", suit, value, CardType.BEER, imagePath);
    }

    //meghívjuk az origin beerAction-jét és annak a visszatérési értékével térünk vissza
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        if(gameLogic.getPlayers().size() > 2) {
            if (origin.getHealth() < origin.getMaxHP()) {
                origin.removeCard(this);
                return origin.beerAction();
            }
        }
        return false;
    }
}
