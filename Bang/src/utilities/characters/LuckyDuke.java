package utilities.characters;

import cards.Card;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class LuckyDuke extends BaseModel {
    public LuckyDuke(Role role, boolean isBot) {
        super(new Character("Lucky Duke", 4), role, isBot);
    }
    //minden húzáskor 2 lap közül választhat

    //done

    @Override
        public Card drawRetCard(GameLogic gameLogic){
        List<Card> cards = new ArrayList<>();
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());

        System.out.println(cards.get(0));
        System.out.println(cards.get(1));

        int index = 0;

        if(isBot){
            if(cards.get(0).getSuit().equals("Hearts")){
                index = 0;
            }else if(cards.get(1).getSuit().equals("Hearts")){
                index = 1;
            }else if(!cards.get(0).getSuit().equals("Spades")){
                index = 0;
            }else if(!cards.get(1).getSuit().equals("Spades")){
                index = 1;
            }else if(cards.get(0).getValue() > 9){
                index = 0;
            }else if(cards.get(1).getValue() > 9){
                index = 1;
            }
        }else {
            index = gameLogic.chooseOption(name, "Select an appropriate card!", cards.get(0).toString(), cards.get(1).toString());
        }

        discardCard(cards.get(0));
        discardCard(cards.get(1));

        return cards.get(index);
    }
}
