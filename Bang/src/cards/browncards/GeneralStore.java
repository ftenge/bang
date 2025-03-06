package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import players.Player;
import utilities.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class GeneralStore extends SingleTargetCard {
    public GeneralStore(String suit, int value) {
        super("General Store", suit, value, CardType.GENERAL_STORE);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        List<BaseModel> players = baseModel.getGameInstance().getPlayers();
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            cards.add(baseModel.getGameInstance().getDeck().draw());
        }
        for(int i = 0; i < players.size(); i++){
            cards.remove(players.get((players.indexOf(baseModel) + i) % players.size()).generalStoreAction(cards, gameLogic));
        }
        return true;
    }
}
