package utilities.characters;

import cards.Card;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.List;

public class VultureSam extends BaseModel {
    public VultureSam(Role role) {
        super(new Character("Vulture Sam", 4), role);
    }
    //megkapja a kiesett játékos lapjait.
    //done

    public void collectCard(List<Card> cards){
        handCards.addAll(cards);
    }
}
