package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

import java.util.List;

public class GatlingCard extends SingleTargetCard {
    public GatlingCard(String suit, int value, String imagePath) {
        super("Gatling", suit, value, CardType.GATLING, imagePath);
    }

    //eldobjuk a kártyát, lekérjük a még élő játékosokat
    //minden játékosnak, aki nem origin, meghívjuk a gatlingAction függvényét
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        origin.removeCard(this);
        List<BaseModel> players = origin.getGameInstance().getPlayers();
        for(BaseModel player : players){
            if(player != origin) {
                player.gatlingAction(origin, gameLogic);
            }
        }
        return true;
    }
}
