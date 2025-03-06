package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

import java.util.List;

public class GatlingCard extends SingleTargetCard {
    public GatlingCard(String suit, int value) {
        super("Gatling", suit, value, CardType.GATLING);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.removeCard(this);
        List<BaseModel> players = baseModel.getGameInstance().getPlayers();
        for(BaseModel player : players){
            if(player != baseModel) {
                player.gatlingAction(baseModel, gameLogic);
            }
        }
        return true;
    }
}
