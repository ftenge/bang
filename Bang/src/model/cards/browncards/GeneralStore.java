package model.cards.browncards;

import model.cards.Card;
import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class GeneralStore extends SingleTargetCard {
    public GeneralStore(String suit, int value, String imagePath) {
        super("General Store", suit, value, CardType.GENERAL_STORE, imagePath);
    }

    //eldobjuk a kártyát, lekérjük a még élő játékosokat,
    //húzunk annyi kártyát, ahány játékos van
    //majd az origintől kezdve, minden játékos választ egy lapot,
    //közben a már választott lapokat eltávolítjuk, hogy ne lehessen kétszer választani őket
    //igazzal térünk vissza mindig
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        origin.removeCard(this);
        List<BaseModel> players = origin.getGameInstance().getPlayers();
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            cards.add(origin.getGameInstance().getDeck().draw());
        }
        for(int i = 0; i < players.size(); i++){
            cards.remove(players.get((players.indexOf(origin) + i) % players.size()).generalStoreAction(cards, gameLogic));
        }
        return true;
    }
}
