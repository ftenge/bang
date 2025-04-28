package model.cards.browncards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

import java.util.List;

public class IndiansCard  extends SingleTargetCard {
    public IndiansCard(String suit, int value, String imagePath) {
        super("Indians", suit, value, CardType.INDIANS, imagePath);
    }

    //eldobjuk a kártyát, lekérjük a még élő játékosokat
    //minden játékosnak, aki nem origin, meghívjuk az indiansAction függvényét
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        origin.removeCard(this);
        List<BaseModel> players = origin.getGameInstance().getPlayers();
        for(BaseModel player : players){
            if(player != origin){
                player.indiansAction(origin, gameLogic);
            }
        }
        return true;
    }
}