package model.utilities.characters;

import model.cards.Card;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

import java.util.List;

public class VultureSam extends BaseModel {
    public VultureSam(Role role, boolean isBot) {
        super(new Character("Vulture Sam", 4), role, isBot);
    }
    //megkapja a kiesett játékos lapjait.
    //done

    public void collectCard(List<Card> cards){
        handCards.addAll(cards);
    }
}
