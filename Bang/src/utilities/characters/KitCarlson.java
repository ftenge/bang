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

    //done

    @Override
    public void roundStartDraw(GameLogic gameLogic){
        List<Card> cards = new ArrayList<>();
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());

        List<Card> chosenCards = new ArrayList<>();
        chosenCards = gameLogic.selectTwoCards(cards, name, "Choose 2 cards and put back the rest!");
        if(chosenCards == null){
            handCards.add(cards.get(0));
            handCards.add(cards.get(1));
            gameInstance.getDeck().putFirst(cards.getLast());
            return;
        }
        handCards.add(chosenCards.get(0));
        handCards.add(chosenCards.get(1));
        gameInstance.getDeck().putFirst(chosenCards.getLast());


    }
}
