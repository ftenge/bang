package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class SaloonCard extends SingleTargetCard {
    public SaloonCard(String suit, int value) {
        super("Saloon", suit, value, CardType.SALOON);
    }

    //eldobjuk a kártyát és minden játékosnak meghívjuk a saloonAction függvényét
    @Override
     public boolean use(BaseModel origin, GameLogic gameLogic) {
        origin.removeCard(this);
        for(BaseModel player : gameLogic.getPlayers()){
            player.saloonAction(gameLogic);
        }
        return true;
    }
}
