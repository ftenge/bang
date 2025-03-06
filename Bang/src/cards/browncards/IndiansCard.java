package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

import java.util.List;

public class IndiansCard  extends SingleTargetCard {
    public IndiansCard(String suit, int value) {
        super("Indians", suit, value, CardType.INDIANS);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        List<BaseModel> players = baseModel.getGameInstance().getPlayers();
        for(BaseModel player : players){
            if(player != baseModel){
                player.indiansAction(baseModel, gameLogic);
            }
        }
        return true;
    }
}