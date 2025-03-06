package utilities.characters;

import cards.Card;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class KitCarlson extends BaseModel {
    public KitCarlson(Role role) {
        super(new Character("Kit Carlson", 4), role);
    }
    //húzáskor 3 lapból választhat 2-t, a maradékot visszateszi


    @Override
    public void roundStartDraw(GameLogic gameLogic){
        List<Card> cards = new ArrayList<>();
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());

        cards = gameLogic.selectTwoCards(cards);

        handCards.add(cards.get(0));
        handCards.add(cards.get(1));

        gameInstance.getDeck().putFirst(cards.getLast());
    }
}
