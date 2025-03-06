package utilities.characters;

import cards.Card;
import cards.HandCard;
import cards.browncards.BeerCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class ElGringo extends BaseModel {
    public ElGringo(Role role) {
        super(new Character("El Gringo", 3), role);
    }
    //húz egy lapot attól aki eltalálja, universal pánik, nem pánik, kézből húz
    //done

    @Override
    public void receiveDamage(int damage, BaseModel source, GameLogic gameLogic){
        this.health = getHealth() - damage;
        drawFromSomeonesHand(source, gameLogic);
        if(health <= 0){
            while(true){
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Beer card or pass!");
                if(card instanceof BeerCard beerCard){
                    playSingleTargetCard(beerCard, gameLogic);
                }
                if(health > 0){
                    System.out.println("Visszahoztad magad az életbe!");
                    return;
                }
                if(card == null){
                    return;
                }
            }
        }
    }

    public void drawFromSomeonesHand(BaseModel target, GameLogic gameLogic){
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < target.getHandCards().size(); i++){
            String cardName = "Hand card " + (i + 1);
            cards.add(new HandCard(cardName, "",i));
        }

        int index = gameLogic.chooseFromHand(cards, name, "Choose a card to steal from " + target.getName());

        handCards.add(target.getHandCards().get(index));
        target.getHandCards().remove(index);
        if(target instanceof SuzyLafayette suzyLafayette){
            if(suzyLafayette.isHandEmpty()){
                suzyLafayette.drawCard();
            }
        }
    }
}
