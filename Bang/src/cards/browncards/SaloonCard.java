package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

public class SaloonCard extends SingleTargetCard {
    public SaloonCard(String suit, int value) {
        super("Saloon", suit, value, CardType.SALOON);
    }

    @Override
     public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        for(BaseModel player : gameLogic.getPlayers()){
            player.saloonAction(gameLogic);
        }
        return true;
    }
}
