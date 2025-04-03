package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

import java.util.List;

public class IndiansCard  extends SingleTargetCard {
    public IndiansCard(String suit, int value) {
        super("Indians", suit, value, CardType.INDIANS);
    }

    //eldobjuk a kártyát, lekérjük a még élő játékosokat
    //minden játékosnak, aki nem origin, meghívjuk az indiansAction függvényét
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.removeCard(this);
        List<BaseModel> players = baseModel.getGameInstance().getPlayers();
        for(BaseModel player : players){
            if(player != baseModel){
                player.indiansAction(baseModel, gameLogic);
            }
        }
        return true;
    }
}