package model.cards.browncards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class SaloonCard extends SingleTargetCard {
    public SaloonCard(String suit, int value, String imagePath) {
        super("Saloon", suit, value, CardType.SALOON, imagePath);
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
